package hr.fer.zemris.java.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider for EntityManagerFactory object.
 * @author Marin Jurjevic
 *
 */
public class JPAEMFProvider {

	/**
	 * factory instance
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Returns instance of <tt>EntityManagerFactory</tt>
	 * @return <tt>EntityManagerFactory</tt> object
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets new instance of <tt>EntityManagerFactory</tt>
	 * @param emf instance of <tt>EntityManagerFactory</tt> to be used by this provider
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}