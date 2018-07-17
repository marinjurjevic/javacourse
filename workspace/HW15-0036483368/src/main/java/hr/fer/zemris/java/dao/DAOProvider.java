package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.dao.jpa.JPADAOImpl;

/**
 * Singleton class for providing services of persistence layer for data
 * management.
 * 
 * @author Marin Jurjevic
 *
 */
public class DAOProvider {

	/**
	 * private instance of dao.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Fetch instance of DAO object.
	 * 
	 * @return object for accessing persistence layer (data access object)
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}