package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Test class for testing SmartScriptEngine class on examples from pdf.
 * 
 * @author Marin Jurjevic
 *
 */
public class SmartScriptEngineTester {

	/**
	 * Entry point
	 * 
	 * @param args
	 *            none
	 * @throws IOException if an error during writing occurs
	 */
	public static void main(String[] args) throws IOException {
		//executeScript1();
		//executeScript2();
		//executeScript3();
		//executeScript4();
	}

	/**
	 * Example osnovni.smscr
	 * @throws IOException if an error during writing occurs
	 */
	private static void executeScript1() throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("scripts/osnovni.smscr")),
				StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Example zbrajanje.smscr
	 * @throws IOException if an error during writing occurs
	 */
	private static void executeScript2() throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("scripts/zbrajanje.smscr")),
				StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Example brojPoziva.smscr
	 * @throws IOException if an error during writing occurs
	 */
	private static void executeScript3() throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("scripts/brojPoziva.smscr")),
				StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}
	
	/**
	 * Example fibonacci.smscr
	 * @throws IOException if an error during writing occurs
	 */
	private static void executeScript4() throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("scripts/fibonacci.smscr")),
				StandardCharsets.UTF_8);
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
}
