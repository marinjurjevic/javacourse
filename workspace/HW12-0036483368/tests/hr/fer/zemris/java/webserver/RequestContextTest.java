package hr.fer.zemris.java.webserver;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * JUnit test for RequestContext class.
 * @author Marin Jurjevic
 *
 */
public class RequestContextTest {

	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgument() {
		RequestContext rc = new RequestContext(null, null,null,null);
		
	}
	
	@Test
	public void testHeaderGeneration(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RequestContext rc = new RequestContext(bos, null,null,null);
		rc.setMimeType("text/plain");
		try{
			rc.write("Some text.");
		}catch(IOException ignorable){
			
		}
		
		String expectedText = "HTTP/1.1 200 OK\r\n"+
					"Content-Type: text/plain; charset=UTF-8\r\n\r\n"+
					"Some text.";
		
		assertEquals("Expecting some text.", expectedText, bos.toString());
	}
	
	@Test
	public void testHtmlResponse(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RequestContext rc = new RequestContext(bos, null,null,null);
		
		String text = "<!DOCTYPE html>"+
				"<html>"+
				"<body>" +
					"<p>Browsers usually insert quotation marks around the q element.</p>"+
					"<p>WWF's goal is to: <q>Build a future where people live in harmony with nature.</q></p>"+
					"</body>"+
					"</html>";
		
		try{
			rc.write(text);
		}catch(IOException ignorable){
			
		}
		
		String expectedText = "HTTP/1.1 200 OK\r\n"+
					"Content-Type: text/html; charset=UTF-8\r\n\r\n"+ text;
		
		assertEquals("Expecting some text.", expectedText, bos.toString());
	}
	
	

	@Test
	public void testParameters(){
		Map<String,String> params = new HashMap<>();
		Map<String,String> permParams = new HashMap<>();
		
		params.put("name", "James");
		params.put("surname", "Bond");
		params.put("code", "007");
		
		permParams.put("agency", "MI5");
		permParams.put("country", "GB");
		
		RequestContext rc = new RequestContext(System.out, params,permParams,null);
		
		assertEquals("James", rc.getParameter("name"));
		assertEquals("Bond", rc.getParameter("surname"));
		assertEquals("007", rc.getParameter("code"));
		
		assertEquals("MI5", rc.getPersistentParameter("agency"));
		assertEquals("GB", rc.getPersistentParameter("country"));
		
	}
	
	@Test
	public void testCookies(){
		List<RCCookie> cookies = new LinkedList<RCCookie>();
		cookies.add(new RCCookie("country", "hr", "100.100.100.100", "/", null));
		cookies.add(new RCCookie("id", "5000", "localhost", "/ext/pr", 5000));
		cookies.add(new RCCookie("food", "pizza, spaghetti", "127.0.0.1", "/", null));
		cookies.add(new RCCookie("beverage", "fanta", null, null, null));
		
		testCookie(cookies.get(0), "country", "hr", "100.100.100.100", "/", null);
		testCookie(cookies.get(1), "id", "5000", "localhost", "/ext/pr", 5000);
		testCookie(cookies.get(2), "food", "pizza, spaghetti", "127.0.0.1", "/", null);
		testCookie(cookies.get(3), "beverage", "fanta", null, null, null);
	}
	
	private void testCookie(RCCookie cookie, String name, String value, String domain, String path, Integer maxAge){
		assertEquals(cookie.getName(), name);
		assertEquals(cookie.getValue(), value);
		assertEquals(cookie.getDomain(), domain);
		assertEquals(cookie.getPath(), path);
		assertEquals(cookie.getMaxAge(), maxAge);
	}
	
	
}
