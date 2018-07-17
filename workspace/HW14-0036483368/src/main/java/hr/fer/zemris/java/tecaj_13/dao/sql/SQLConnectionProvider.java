package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.sql.Connection;

/**
 * SQLConnectionProvider contains connections in ThreadLocal object. ThreadLocal
 * is actually a map whose keys are thread ID's which is requesting map value
 * which is in this case instance of Connection.
 * 
 * @author Marin Jurjevic
 *
 */
public class SQLConnectionProvider {

	/**
	 * private map of connection whose key is current thread.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Set connection for current thread( or delete connection from map if con
	 * == <tt>null</tt>)
	 * 
	 * @param con
	 *            connection to database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Fetch connection for this thread (caller).
	 * 
	 * @return vezu connection to database
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}