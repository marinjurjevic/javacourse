package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Prints out parameters passed in URL. 
 * @author Marin Jurjevic
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		StringBuilder sb = new StringBuilder(
				"<html>\r\n" +
				"  <head>\r\n" + 
				"    <title>Ispis zaglavlja</title>\r\n"+
				"  </head>\r\n" + 
				"  <body  align=\"center\">\r\n" + 
				"    <h1>Request information</h1>\r\n" + 
				"    <p>Defined parameters:</p>\r\n" + 
				"    <table border='1' align=\"center\" style=\"width:400\">\r\n" +
				"    <tr><td>Parameter name</td><td>Parameter value</td>");
		
		for(String p : context.getParameterNames()){
			sb.append("      <tr><td>")
			.append(p)
			.append("</td><td>")
			.append(context.getParameter(p))
			.append("</td></tr>\r\n");
		}
		sb.append(
				"    </table>\r\n" + 
				"  </body>\r\n" + 
				"</html>\r\n");
		
		try {
			context.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
