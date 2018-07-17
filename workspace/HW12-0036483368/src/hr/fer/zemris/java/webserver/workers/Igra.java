package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Random;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Runs a simple guess a number game.
 * @author Marin Jurjevic
 *
 */
public class Igra implements IWebWorker {

	/**
	 * maximum number of attempts
	 */
	private static final short MAX_ATTEMPTS = 7;
	
	/**
	 * random number generator
	 */
	private Random rnd = new Random();
	
	@Override
	public void processRequest(RequestContext context) {
		// start of all message
		String message = ("<html>\r\n" +
				"  <head>\r\n" + 
				"    <title>Igra pogađanja</title>\r\n"+
				"  </head>\r\n" + 
				"  <body  align=\"center\">\r\n" + 
				"    <h1>Pogodi broj.</h1>\r\n");
		
		if(context.getPersistentParameter("rndNumber") == null){
			context.setPersistentParameter("rndNumber", Integer.toString(rnd.nextInt(100)+1));
			context.setPersistentParameter("attempts", Integer.toString(MAX_ATTEMPTS));
			message += generateStartMessage();
		}else{
			int attempts = Integer.parseInt(context.getPersistentParameter("attempts"));
			attempts--;
			int userNumber = Integer.parseInt(context.getParameter("cand"));
			int rndNumber = Integer.parseInt(context.getPersistentParameter("rndNumber"));
			
			if(userNumber == rndNumber){
				message += generateWinMessage(rndNumber, attempts);
				resetGame(context);
			}else if(attempts == 0){
				message += generateLoseMessage(rndNumber);
				resetGame(context);
			}else{ 
				message += generateAttemptMessage(userNumber>rndNumber, attempts);
				context.setPersistentParameter("attempts", Integer.toString(attempts));
			}
			
		}
		
		try {
			context.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Generates new message that appears upon starting this application.
	 * @return message as String
	 */
	private String generateStartMessage(){
		return ("<p>Na raspologanju imate "+MAX_ATTEMPTS+" pokušaja"+ "</p>\r\n" + 
				"    <form action=\"/ext/Igra\"> Što mislite koji je broj? <input type=\"text\" name=\"cand\"><br>" +
						"<input type=\"submit\"></form> "+
				"  </body>"+
				"</html>");		
	}
	
	/**
	 * Generates new message when user has more attempts and current attempt resulted in wrong number.
	 * @param bigger true if user number was bigger than correct, false if smaller
	 * @param attempts number of left attempts to use
	 * @return message as String
	 */
	private String generateAttemptMessage(boolean bigger, int attempts){
		return ("<p>Na raspologanju imate "+attempts+" pokušaja"+ "</p>\r\n" + 
				"    <form action=\"/ext/Igra\"> Što mislite koji je broj? <input type=\"text\" name=\"cand\"><br>" +
						"<input type=\"submit\"></form> "+
						"    <p>Broj koji ste unijeli je " + (bigger?"veći":"manji") + " od točnog broja."+ "</p>\r\n" + 
				"  </body>"+
				"</html>");	
	}
	
	/**
	 * Generates new message that appears after all attempts are used and game is over.
	 * @param correctNumber correct number that was generated on start
	 * @return message as String
	 */
	private String generateLoseMessage(int correctNumber){
		return ("<p>Igra je gotova, nažalost nemate sreće."+ "</p>\r\n" + 
				"    <p>Točan odgovor je bio : "+ correctNumber + "</p>\r\n" +
				"    <p>Ako želite pokušati ponovo, osježite stranicu."+ "</p>\r\n" + 
				"  </body>"+
				"</html>");	
	}

	/**
	 * Generates new message when user has guessed the number
	 * @param correctNumber correct number that was generated on start
	 * @param attempts number of left attempts to use
	 * @return message as String
	 */
	private String generateWinMessage(int correctNumber, int attempts){
		return ("<p>Bravo, točan odgovor jest : "+ correctNumber + "</p>\r\n" +
				"    <p>Trebalo vam je : "+ (MAX_ATTEMPTS - attempts) +" pokušaja"+ "</p>\r\n" +
				"    <p>Ako želite pokušati ponovo, osježite stranicu."+ "</p>\r\n" + 
				"  </body>"+
				"</html>");	
	}

	/**
	 * Resets game, deletes current application info from persistent parameters map.
	 * @param context current context 
	 */
	private void resetGame(RequestContext context){
		context.removePersistentParameter("attempts");
		context.removePersistentParameter("rndNumber");
	}
}
