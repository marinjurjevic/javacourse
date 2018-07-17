package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Marin Jurjevic
 *
 */
public class RequestContext {
	/**
	 * instance of OutputStream for sending data
	 */
	private OutputStream outputStream;
	
	/**
	 * charset used for encoding data
	 */
	private Charset charset;
	
	/**
	 * encoding used, default UTF-8
	 */
	private String encoding = "UTF-8";
	
	/**
	 * header status code
	 */
	private int statusCode = 200;
	
	/**
	 * header text
	 */
	private String statusText = "OK";
	
	/**
	 * mime type
	 */
	private String mimeType = "text/html";
	
	/**
	 * map of parameters
	 */
	private final Map<String,String> parameters;
	
	/**
	 * map of temporary parameters
	 */
	private Map<String,String> temporaryParameters;
	
	/**
	 * map of persistent parameters
	 */
	private Map<String,String> persistentParameters;
	
	/**
	 * list of output cookies
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * flag for keeping track has header already been generated
	 */
	private boolean headerGenerated;
	
	/**
	 * Creates new instance of RequestContext.
	 * @param outputStream stream where data will be written
	 * @param parameters request context parameters
	 * @param persistentParameters request context persistent parameters
	 * @param outputCookies cookies in this context
	 */
	public RequestContext(
			OutputStream outputStream, 
			Map<String,String> parameters,
			Map<String,String> persistentParameters,
			List<RCCookie> outputCookies){
		
		if(outputStream == null){
			throw new IllegalArgumentException("OutputStream must not be null");
		}
		this.outputStream = outputStream;
		this.parameters = parameters == null? new HashMap<>():parameters;
		this.persistentParameters = persistentParameters == null? new HashMap<>():persistentParameters;
		this.outputCookies = outputCookies == null ? new LinkedList<>():outputCookies;
		this.temporaryParameters = new HashMap<>();
	}
	
	/**
	 * Retrieves parameter value from this RequestContext. If there is no such
	 * parameter, <tt>null</tt> is returned. 
	 * @param name parameter name
	 * @return parameter value
	 */
	public String getParameter(String name){
		return parameters.get(name);
	}
	
	/**
	 * Retrieves names of all parameters in this RequestContext. Resulting names 
	 * are stored in unmodifiable set.
	 * @return unmodifiable set consisting all parameter names
	 */
	public Set<String> getParameterNames(){
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Retrieves persistent parameter value from this RequestContext. If there is
	 *  no such persistent parameter, <tt>null</tt> is returned. 
	 * @param name parameter name
	 * @return parameter value
	 */
	public String getPersistentParameter(String name){
		return persistentParameters.get(name);
	}
	
	/**
	 * Retrieves names of all persistent parameters in this RequestContext. Resulting 
	 * names are stored in unmodifiable set.
	 * @return unmodifiable set consisting all persistent parameter names
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Stores new value of persistent parameter in this context.
	 * @param name persistent parameter name
	 * @param value new value of persistent parameter
	 */
	public void setPersistentParameter(String name, String value){
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes persistent parameter from this context.
	 * @param name name of persistent paramter to be removed
	 */
	public void removePersistentParameter(String name){
		persistentParameters.remove(name);
	}
	
	
	/**
	 * Retrieves temporary parameter value from this RequestContext. If there is
	 *  no such temporary parameter, <tt>null</tt> is returned. 
	 * @param name temporary parameter name
	 * @return temporary parameter value
	 */
	public String getTemporaryParameter(String name){
		return temporaryParameters.get(name);
	}
	
	/**
	 * Retrieves names of all persistent parameters in this RequestContext. Resulting 
	 * names are stored in unmodifiable set.
	 * @return unmodifiable set consisting all persistent parameter names
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Stores new value of temporary parameter in this context.
	 * @param name temporary parameter name
	 * @param value new value of temporary parameter
	 */
	public void setTemporaryParameter(String name, String value){
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes temporary parameter from this context.
	 * @param name name of temporary paramter to be removed
	 */
	public void removeTemporaryParameter(String name){
		temporaryParameters.remove(name);
	}
	
	/**
	 * Adds new instance of <tt>RCCookie</tt> to this Request Context.
	 * @param cookie new cookie to be added to present context
	 */
	public void addRCCookie(RCCookie cookie){
		outputCookies.add(cookie);
	}
	
	/**
	 * Writes this string into output stream initialized in this object's constructor.
	 * String is encoded to charset using default encoding (UTF-8) if none were specified.
	 *  The general contract for write(String) is that it should have exactly the same effect as the call
	 *  write(String.getBytes(charset)), where charset is the one encoded at header generation. 
	 * @param text text to be written in output stream
	 * @return this context object
	 * @throws IOException if an error occurs while writing
	 */
	public RequestContext write(String text) throws IOException{
		if(text == null){
			throw new IllegalArgumentException("Text can not be null");
		}
		
		if(!headerGenerated){
			charset = Charset.forName(encoding);
		}
		
		byte[] octets = text.getBytes(charset);
		return write(octets);
	}
	
	/**
	 * Writes sequence of bytes into output stream initialized in this object's constructor.
	 * @param data array of bytes to be written in output stream
	 * @return this context object
	 * @throws IOException if an error occurs while writing
	 */
	public RequestContext write(byte[] data) throws IOException{
		if(data == null){
			throw new IllegalArgumentException("Byte array can not be null");
		}
		
		if(!headerGenerated){
			generateHeader();
		}
		
		outputStream.write(data);
		
		return this;
	}
	
	/**
	 * Generates header of this request context. Header is generated only once using
	 * standard charset {@link StandardCharsets#ISO_8859_1}.
	 * @throws IOException
	 */
	private void generateHeader() throws IOException{
		headerGenerated = true;
		
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" )
		  .append("Content-Type: " + mimeType);
		if(mimeType.startsWith("text/")){
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		
		if(!outputCookies.isEmpty()){
			for(RCCookie c : outputCookies){
				sb.append("Set-Cookie: "+c.name+"=" + c.value);
				if(c.domain != null){
					sb.append("; Domain=" + c.domain);
				}
				if(c.path != null){
					sb.append("; Path=" + c.path);
				}
				if(c.maxAge != null){
					sb.append("; Max-Age=" + c.maxAge);
				}
				sb.append("; Http-Only\r\n");
			}
		}
		
		//end line
		sb.append("\r\n");
		byte[] header = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
		outputStream.write(header);
	}
	
	
	/**
	 * Sets new encoding used in RequestContext.
	 * @param encoding new encoding
	 */
	public void setEncoding(String encoding) {
		checkHeader();
		this.encoding = encoding;
	}

	/**
	 * Sets new status code used in this RequestContext.
	 * @param statusCode new status code
	 */
	public void setStatusCode(int statusCode) {
		checkHeader();
		this.statusCode = statusCode;
	}

	/**
	 * Sets new status text used by this RequestContext.
	 * @param statusText new status text
	 */
	public void setStatusText(String statusText) {
		checkHeader();
		this.statusText = statusText;
	}

	/**
	 * Sets new mime type that this RequestContext generates.
	 * @param mimeType new mime type
	 */
	public void setMimeType(String mimeType) {
		checkHeader();
		this.mimeType = mimeType;
	}
	
	/**
	 * Checks if header has been generated. If any of header property setters has been
	 * called after header had been generated, this method will throw <tt>RuntimeException</tt>.
	 */
	private void checkHeader(){
		if(headerGenerated){
			throw new RuntimeException("Header has been generated."
					+ " You're not allowed to change it's properties!");
		}
	}


	/**
	 * Model of cookie. Cookie is represented by it's name and value. Optional
	 * parameters are domain, path and max age. If optional parameter is not 
	 * specified it's value will be set to <tt>null</tt>.
	 * @author Marin Jurjevic
	 *
	 */
	public static class RCCookie{
		/**
		 * cookie name identifier
		 */
		private final String name;
		
		/**
		 * cookie value
		 */
		private final String value;
		
		/**
		 * cookie domain
		 */
		private final String domain;
		
		/**
		 * path
		 */
		private final String path;
		
		/**
		 * value of max age
		 */
		private final Integer maxAge;
		
		/**
		 * Creates new instance of RCCookie class.
		 * @param name cookie identificator
		 * @param value value
		 * @param domain cookie domain
		 * @param path cookie path
		 * @param maxAge cookie max age value
		 */
		public RCCookie(String name, String value, String domain,String path, Integer maxAge) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns name identifier for this cookie.
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns value of this cookie.
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns string representation of domain.
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Returns path of this cookie.
		 * @return path 
		 */
		public String getPath(){
			return path;
		}

		/**
		 * Returns max age of this cookie.
		 * @return max age value
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
	}
}
