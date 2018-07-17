package hr.fer.zemris.java.tecaj_13.model;

/**
 * Class that encapsulates information about poll.
 * 
 * @author Marin Jurjevic
 *
 */
public class Poll {

	/**
	 * poll ID
	 */
	private long id;

	/**
	 * poll title
	 */
	private String title;

	/**
	 * poll message
	 */
	private String message;

	/**
	 * Default constructor.
	 */
	public Poll() {
	}

	/**
	 * Creates new poll with title and message.
	 * 
	 * @param title
	 *            poll title
	 * @param message
	 *            poll message
	 */
	public Poll(String title, String message) {
		super();
		this.title = title;
		this.message = message;
	}

	/**
	 * Returns poll ID.
	 * 
	 * @return poll ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets poll ID.
	 * 
	 * @param id
	 *            poll ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns poll title.
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets poll title.
	 * 
	 * @param title
	 *            poll title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns poll message.
	 * 
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets new poll message.
	 * 
	 * @param message
	 *            poll message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
