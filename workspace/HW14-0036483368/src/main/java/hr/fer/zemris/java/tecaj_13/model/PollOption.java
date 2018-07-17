package hr.fer.zemris.java.tecaj_13.model;

/**
 * Class that encapsulates information about single poll option.
 * 
 * @author Marin Jurjevic
 *
 */
public class PollOption {

	/**
	 * poll option ID
	 */
	private long id;

	/**
	 * poll option title
	 */
	private String optionTitle;

	/**
	 * poll option link
	 */
	private String optionLink;

	/**
	 * pollID this pollOption belongs to
	 */
	private long pollID;

	/**
	 * number of times user voted for this option
	 */
	private long votesCount;

	/**
	 * Default constructor.
	 */
	public PollOption() {
	}

	/**
	 * Creates new PollOption with specified parameters.
	 * 
	 * @param optionTitle
	 *            option title
	 * @param optionLink
	 *            option link
	 * @param pollID
	 *            pollID this poll belongs to
	 * @param votesCount
	 *            starting number of votes
	 */
	public PollOption(String optionTitle, String optionLink, long pollID, long votesCount) {
		super();
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Creates new PollOption with specified parameters and default number of
	 * votes (0).
	 * 
	 * @param optionTitle
	 *            option title
	 * @param optionLink
	 *            option link
	 * @param pollID
	 *            pollID this poll belongs to
	 */
	public PollOption(String optionTitle, String optionLink, long pollID) {
		super();
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
	}

	/**
	 * Returns poll option ID.
	 * 
	 * @return poll option ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets poll option ID.
	 * 
	 * @param id
	 *            new poll option ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets poll option title.
	 * 
	 * @return poll option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets poll option title.
	 * 
	 * @param optionTitle
	 *            new option title.
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Returns option link attribute.
	 * 
	 * @return option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets option link attribute.
	 * 
	 * @param optionLink
	 *            new option link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Returns pollID this poll option belongs to.
	 * 
	 * @return pollID that is contains this poll option
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets new pollID for this poll option.
	 * 
	 * @param pollID
	 *            new poll ID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Gets number of votes for this option.
	 * 
	 * @return number of votes
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets number of votes for this option.
	 * 
	 * @param votesCount
	 *            new number of votes
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
