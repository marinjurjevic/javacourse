package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * SmartHttpServer is implementation of a simple web server. SmartHttpServer
 * supports multithreading and it can process more than one client at same time.
 * 
 * @author Marin Jurjevic
 *
 */
public class SmartHttpServer {
	/**
	 * server adress
	 */
	private String address;

	/**
	 * server port
	 */
	private int port;

	/**
	 * number of threads used for serving clients
	 */
	private int workerThreads;

	/**
	 * time until client's session will timeout
	 */
	private int sessionTimeout;

	/**
	 * supported mimeTypes , loaded from mime.properties file
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * main server thread
	 */
	private ServerThread serverThread;

	/**
	 * thread pool for supporting multi-threading
	 */
	private ExecutorService threadPool;

	/**
	 * path to root folder for providing files
	 */
	private Path documentRoot;

	/**
	 * flag for stopping server
	 */
	private boolean serverStop;

	/**
	 * map of supported workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	/**
	 * cached sessions from clients
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();

	/**
	 * random number generator for generating session id's
	 */
	private Random sessionRandom = new Random();

	/**
	 * number of miliseconds cleaning thread will sleep before attempting to
	 * clean timeouted sessions
	 */
	private static final long CLEANER_SLEEP = 300_000;

	/**
	 * Creates new SmartHttpServer and configures it using file which path must
	 * be given.
	 * 
	 * @param configFileName
	 *            path to configuration file
	 */
	public SmartHttpServer(String configFileName) {
		Properties serverConfig = new Properties();
		Properties mimeConfig = new Properties();
		Properties workersConfig = new Properties();
		try {
			serverConfig.load(Files.newInputStream(Paths.get(configFileName)));
			this.address = serverConfig.getProperty("server.address");
			this.port = Integer.parseInt(serverConfig.getProperty("server.port"));
			this.workerThreads = Integer.parseInt(serverConfig.getProperty("server.workerThreads"));
			this.sessionTimeout = Integer.parseInt(serverConfig.getProperty("session.timeout"));
			this.documentRoot = Paths.get(serverConfig.getProperty("server.documentRoot"));

			Path mimeTypePath = Paths.get(serverConfig.getProperty("server.mimeConfig"));
			mimeConfig.load(Files.newInputStream(mimeTypePath));
			for (Entry<Object, Object> e : mimeConfig.entrySet()) {
				mimeTypes.put((String) e.getKey(), (String) e.getValue());
			}

			Path workersPath = Paths.get(serverConfig.getProperty("server.workers"));
			workersConfig.load(Files.newInputStream(workersPath));
			for (Entry<Object, Object> e : workersConfig.entrySet()) {
				String path = (String) e.getKey();
				String fqcn = (String) e.getValue();

				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				IWebWorker result = workersMap.put(path, iww);
				if (result != null) {
					throw new IllegalArgumentException("Cannot have duplicate paths in workers property file!");
				}
			}
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			System.err.println("An error occured while configuring server!!");
			e.printStackTrace();
		}
	}

	/**
	 * Starts server.
	 */
	protected synchronized void start() {
		if (serverThread == null || !serverThread.isAlive()) {
			serverThread = new ServerThread();
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();

			Thread cleaner = new Thread(() -> {
				while (true) {
					try {
						Thread.sleep(CLEANER_SLEEP);
					} catch (Exception e) {
						continue;
					}

					synchronized (SmartHttpServer.this) {
						Iterator<Map.Entry<String, SessionMapEntry>> iter = sessions.entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry<String, SessionMapEntry> entry = iter.next();
							if (System.currentTimeMillis() / 1000 > entry.getValue().validUntil) {
								iter.remove();
							}
						}
					}

				}
			});

			cleaner.setDaemon(true);
			cleaner.start();

			System.out.println("Server started!");
		}

	}

	/**
	 * Stops server from running.
	 */
	protected synchronized void stop() {
		serverStop = true;
		threadPool.shutdown();
		System.out.println("Server stopped.");
	}

	/**
	 * Main server thread for running services.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));

				while (true) {
					if (serverStop) {
						break;
					}
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);

				}

				serverSocket.close();
			} catch (IOException ignorable) {
				ignorable.printStackTrace();
			}
		}
	}

	/**
	 * Implementation of ClientWorker threads. ClientWorker will be assigned to
	 * each new client that sends requestes to SmartHttpServer.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	private class ClientWorker implements Runnable {

		/**
		 * client socket
		 */
		private Socket csocket;

		/**
		 * client input stream
		 */
		private PushbackInputStream istream;

		/**
		 * client output stream
		 */
		private OutputStream ostream;

		/**
		 * client request version
		 */
		private String version;

		/**
		 * client method
		 */
		private String method;

		/**
		 * map of parameters which will be sent to RequestContext object
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * map of persistent parameters which will be sent to RequestContext
		 * object
		 */
		private Map<String, String> permParams = null;

		/**
		 * List of RCCookie objects that will be sent to RequestContext object
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/**
		 * Session ID, randomly generated for each new client
		 */
		private String SID;

		/**
		 * Creates new ClientWorker with appropriate socket.
		 * 
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				System.out.println(sessions);
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> request = readRequest();
				if (request == null) {
					sendError(400, "Bad request");
					return;
				}
				String[] firstLine = request.isEmpty() ? null : request.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}

				String addr = "/";
				for (String head : request) {
					if (head.startsWith("Host")) {
						addr = head.substring(5, head.lastIndexOf(":")).trim();
					}
				}
				method = firstLine[0];
				String requestedPath = firstLine[1];
				version = firstLine[2];

				if (!method.equalsIgnoreCase("GET") || (!version.equals("HTTP/1.0") && !version.endsWith("HTTP/1.1"))) {
					sendError(400, "Bad request");
					return;
				}

				synchronized (SmartHttpServer.this) {
					checkSession(request, requestedPath, addr);
				}

				String path, paramString;

				if (requestedPath.contains("?")) {
					int index = requestedPath.indexOf('?');
					path = requestedPath.substring(0, index);
					paramString = requestedPath.substring(index + 1, requestedPath.length());
					parseParameters(paramString);
				} else {
					path = requestedPath;
				}

				//
				RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
				rc.setStatusCode(200);
				if (path.startsWith("/ext/")) {
					Class<?> referenceToClass;
					String classPath;
					try {
						classPath = path.substring(5);
						referenceToClass = this.getClass().getClassLoader()
								.loadClass("hr.fer.zemris.java.webserver.workers." + classPath);
						Object newObject = referenceToClass.newInstance();
						IWebWorker iww = (IWebWorker) newObject;
						iww.processRequest(rc);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}

				} else {
					IWebWorker worker = workersMap.get(path);
					if (worker != null) {
						worker.processRequest(rc);
					} else {
						Path resolvedPath = documentRoot.resolve(path.substring(1));
						if (!resolvedPath.startsWith(documentRoot)) {
							sendError(403, "Forbidden");
							return;
						}
						if (!(Files.exists(resolvedPath) && Files.isRegularFile(resolvedPath)
								&& Files.isReadable(resolvedPath))) {
							sendError(404, "File not found");
							return;
						}

						String ext = path.substring(path.lastIndexOf('.') + 1, path.length());
						String mimeType = mimeTypes.get(ext);
						if (mimeType == null) {
							mimeType = "application/octet-stream";
						}

						rc.setMimeType(mimeType);

						byte[] fileBytes = Files.readAllBytes(resolvedPath);

						if (ext.equalsIgnoreCase("smscr")) {
							String documentBody = new String(fileBytes, StandardCharsets.UTF_8);
							new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
						} else {
							rc.write(fileBytes);
						}

					}
				}

			} catch (IOException e) {
				System.err.println("An error occured while processing client request.");
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					System.err.println("An error occured while closing socket.");
				}
			}
		}

		/**
		 * Checks session informations.
		 * 
		 * @param path
		 *            path of this cookie
		 * @param address
		 *            cookie domain address
		 * @param header
		 *            extracted header from client's request
		 */
		private void checkSession(List<String> header, String path, String address) {
			String sidCandidate = null;
			Map<String, String> cookies = new ConcurrentHashMap<>();

			for (String line : header) {
				if (line.startsWith("Cookie:")) {
					line = line.substring(line.indexOf(':') + 1, line.length()).trim();
					for (String c : line.split(";")) {
						String name = c.split("=")[0];
						String value = c.split("=")[1];
						if (name.equals("sid")) {
							sidCandidate = value.replace("\"", "");
						} else {
							cookies.put(name, value);
							outputCookies.add(new RCCookie(name, value, address, path, null));
						}
					}
					break;
				}
			}

			if (sidCandidate == null) {
				SID = newCookie(cookies, path, address);
			} else {

				SID = sidCandidate;
				SessionMapEntry entry = sessions.get(sidCandidate);
				if (System.currentTimeMillis() / 1000 - entry.validUntil > sessionTimeout) {
					sessions.remove(sidCandidate);
					SID = newCookie(cookies, path, address);

				} else {
					entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
				}
			}
			permParams = sessions.get(SID).map;
			

		}

		/**
		 * Creates new cookie and puts new SID to sessions map of
		 * SmartHttpServer
		 * 
		 * @param cookies
		 *            cookies to be mapped into SessionMapEntry object
		 * @param path
		 *            path of this cookie
		 * @param address
		 *            cookie domain address
		 * @return newly generated session ID
		 */
		private String newCookie(Map<String, String> cookies, String path, String address) {
			StringBuilder sb = new StringBuilder(20);
			for (int i = 0; i < 20; i++) {
				sb.append((char) (65 + sessionRandom.nextInt(26)));
			}
			String sid = sb.toString();

			sessions.put(sid, new SessionMapEntry(sid, System.currentTimeMillis() / 1000, cookies));
			outputCookies.add(new RCCookie("sid", sid, address, path, null));
			return sid;
		}

		/**
		 * Parses parameter from given parameter String.
		 * 
		 * @param paramString
		 *            parameter String object containing parameter information
		 */
		private void parseParameters(String paramString) {

			String[] mappings = paramString.split("&");
			for (String p : mappings) {
				params.put(p.split("=")[0], p.split("=")[1]);
			}

		}

		/**
		 * Implementation of state machine for reading request header.
		 * 
		 * @return List of string containing header lines
		 * @throws IOException
		 *             if an error occurs while reading
		 */
		private List<String> readRequest() throws IOException {
			byte[] request;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			request = bos.toByteArray();

			if (request == null) {
				sendError(400, "Bad request");
				return null;
			}
			String requestStr = new String(request, StandardCharsets.US_ASCII);

			return extractHeaders(requestStr);
		}

		/**
		 * Extracts header from requestHeader and parses it into lines.
		 * 
		 * @param requestHeader
		 *            string containing request header
		 * @return list of strings
		 */
		private List<String> extractHeaders(String requestHeader) {

			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Sends appropriate error if it occurs during client's request
		 * processing.
		 * 
		 * @param statusCode
		 *            error status code
		 * @param statusText
		 *            error status text
		 * @throws IOException
		 *             if an error while writing on output stream occurs
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			//System.out.println("error " + statusCode + " " + statusText);
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();
		}

	}

	/**
	 * Inner class defining one entry for each new session.
	 * 
	 * @author Marin Jurjevic
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * session ID
		 */
		String sid;

		/**
		 * time until this session is valid
		 */
		long validUntil;

		/**
		 * map of client's conte
		 */
		Map<String, String> map;

		/**
		 * Creates new SessionMapEntry that contains information about one
		 * session.
		 * 
		 * @param sid
		 *            session generated ID
		 * @param validUntil
		 *            time until this session is valid
		 * @param map
		 *            map containing client's data
		 */
		public SessionMapEntry(String sid, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = map;
		}

		@Override
		public String toString() {
			return sid + ", validUntil=" + validUntil + ", map=" + map;
		}

		
	}

	/**
	 * Method for starting server.
	 * 
	 * @param args
	 *            server configuration file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("I expected path to server config file!");
			return;
		}

		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}
}