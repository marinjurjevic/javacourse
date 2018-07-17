package hr.fer.zemris.java.tecaj10.tcp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTTPDownload {

	public static void main(String[] args) throws IOException {
		if(args.length != 4){
			System.out.println("Ocekivao sam host port urlpath localfilepath");
			return;
		}
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		String urlpath = args[2];
		String filepath = args[3];
		
		OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(filepath)));
		
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(hostname), port));
		
		byte[] reqData = (
				"GET " + urlpath + " HTTP/1.1\r\n" +
				"Host: " + hostname + "\r\n" +
				"User-agent: Simple Java Client\r\n" + 
				"Accept: text/html,*/*;q=0.9\r\n" + 
				"Accept-Language: en-US,en;q=0.5\r\n" +
				"Connection: close\r\n" +
				"Cache-Control: max-age=0\r\n"+
				"\r\n").getBytes(StandardCharsets.US_ASCII);
		
		socket.getOutputStream().write(reqData);
		socket.getOutputStream().flush();
		
		byte[] buf = new byte[1024];
		while(true){
			int r = socket.getInputStream().read(buf);
			if(r<0) break;
			os.write(buf, 0, r);
		}
		
		os.flush();
		os.close();
		socket.close();
	}
}
