package hr.fer.zemris.java.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;

/**
 * Implementation of DAO interface using JPA techonology.
 * @author Marin Jurjevic
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(
				BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogEntry getBlogEntries(String nick) throws DAOException {
		return null;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = em
				.createQuery("select b from BlogUser as b where b.nick=:nick")
				.setParameter("nick", nick).getResultList();
		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public void addBlogUser(BlogUser u) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(u);
	}

	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = em.createQuery("from BlogUser")
				.getResultList();
		return users;
	}

	@Override
	public List<BlogEntry> getBlogEntriesForNick(String nick)
			throws DAOException {

		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = getBlogUser(nick);
		if (user == null) {
			return new ArrayList<BlogEntry>();
		}

		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = em
				.createQuery(
						"select b from BlogEntry as b where b.creator=:user")
				.setParameter("user", user).getResultList();
		return entries;
	}

	@Override
	public void addUpdateBlogEntry(BlogEntry e, boolean update) {
		EntityManager em = JPAEMProvider.getEntityManager();
		if (update) {
			em.merge(e);
		} else {
			em.persist(e);
		}
	}

	@Override
	public void addComment(BlogComment comment) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
	}
}