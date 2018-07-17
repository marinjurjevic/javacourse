package hr.fer.zemris.java.dao;

import java.util.List;

import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

/**
 * DAO (Data Access Object) interface represents interface towards data
 * persistence layer. This interface communicates with data and application
 * layer providing bridge for data transfer between these two layers.
 */
public interface DAO {

	/**
	 * Fetches blog entry under given id.
	 * @param id blog entry id
	 * @return user blog entry
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;


	/**
	 * Fetches all blog entries on the blog for given user.
	 * @return all user available blog entries
	 * @param nick user nickname
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntries(String nick) throws DAOException;

	/**
	 * Fetches blog user with given nick.
	 * @param nick user nickname
	 * @return blog user
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Fetches all registered users on the blog.
	 * @return all available blog users
	 * @throws DAOException
	 */
	public List<BlogUser> getAllUsers() throws DAOException;

	/**
	 * Adds new blog user.
	 * @param bu blog user
	 * @throws DAOException
	 */
	public void addBlogUser(BlogUser bu) throws DAOException;

	/**
	 * Fetches all blog entries on the blog posted by specific user (identified by unique nickname).
	 * @param nick unique user nickname
	 * @return all available blog entries posted by user
	 * @throws DAOException
	 */
	public List<BlogEntry> getBlogEntriesForNick(String nick)
			throws DAOException;

	/**
	 * Updates given blog entry.
	 * @param e
	 * @param update
	 */
	public void addUpdateBlogEntry(BlogEntry e, boolean update);

	/**
	 * Adds new blog comment.
	 * @param comment blog comment
	 */
	public void addComment(BlogComment comment);

}