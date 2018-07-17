package hr.fer.zemris.java.webserver;

/**
 * Implementation of IWebWorker. IWebWorker is interface for providing
 * request context processing.
 * @author Marin Jurjevic
 *
 */
public interface IWebWorker {
	/**
	 * Process some data from context and sends it through context stream.
	 * @param context
	 */
	public void processRequest(RequestContext context);
}