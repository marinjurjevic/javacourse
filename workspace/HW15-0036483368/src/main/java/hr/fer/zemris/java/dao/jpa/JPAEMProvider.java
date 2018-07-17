package hr.fer.zemris.java.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.dao.DAOException;

/**
 * JPAEMProvider presents connection
 * @author Marin Jurjevic
 *
 */
public class JPAEMProvider {

	/**
	 * private map of connection whose key is entity manager
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Fetches new EntityManager object for each call.
	 * @return instance of EntityManager
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Closes entity manager.
	 * @throws DAOException
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
	
}