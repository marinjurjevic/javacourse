package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * DAO (Data Access Object) interface represents interface towards data
 * persistence layer. This interface communicates with data and application
 * layer providing bridge for data transfer between these two layers.
 */
public interface DAO {

	/**
	 * Creates Poll table in database.
	 * 
	 * @return true if table has been created or existing table is empty, false
	 *         if data already exists
	 */
	public boolean createPollTable();

	/**
	 * Creates PollOptions table in database.
	 * 
	 * @return true if table has been created or existing table is empty, false
	 *         if data already exists
	 */
	public boolean createPollOptionsTable();

	/**
	 * Returns all polls stored in Polls table.
	 * 
	 * @return list of all available polls
	 */
	public List<Poll> getPolls();

	/**
	 * Returns all poll options for specific Poll ordered by number of votes in
	 * descending order. If parameter 0 has been provided, all options from all
	 * polls will be returned in no specific order.
	 * 
	 * @param id
	 *            unique ID for searching poll option inside PollOptions table
	 * @return list of all available poll options for a specific poll, or all
	 *         polls if 0 has been sent as id
	 */
	public List<PollOption> getPollOptions(long id);

	/**
	 * Returns specified poll searched by provided id.
	 * 
	 * @param id
	 *            poll id in database
	 * @return poll object
	 */
	public Poll getPoll(long id);

	/**
	 * Inserts given Poll into database.
	 * 
	 * @param poll
	 *            given poll object
	 * @return generated poll ID from table
	 */
	public long insertPoll(Poll poll);

	/**
	 * Inserts given PollOption into database.
	 * 
	 * @param poll
	 *            given PollOption object
	 */
	public void insertPollOption(PollOption poll);

	/**
	 * Removes Poll from database (optional operation)
	 * 
	 * @param id
	 *            poll ID
	 */
	public void removePoll(long id);

	/**
	 * Removes PollOption from database (optional operation)
	 * 
	 * @param id
	 *            pollOption ID
	 */
	public void removePollOption(long id);

	/**
	 * Updates number of votes for specific pollOption. For poll functionallity,
	 * we will consider update means increase number of votes by 1.
	 * 
	 * @param optionID
	 *            unique ID of poll option
	 */
	public void updateVotes(long optionID);
}
