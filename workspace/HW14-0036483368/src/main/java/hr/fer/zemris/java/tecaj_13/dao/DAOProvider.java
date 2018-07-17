package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.sql.SQLDAO;

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
	private static DAO dao = new SQLDAO();

	/**
	 * Fetch instance of DAO object.
	 * 
	 * @return object for accessing persistence layer (data access object)
	 */
	public static DAO getDao() {
		return dao;
	}

}