package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.blog.form.BlogEntryForm;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.dao.DAOProvider;

/**
 * 
 * Author servlet represents servlet handling all options for registered user..
 * @author Marin Jurjevic
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * serial UID
	 */
	private static final long serialVersionUID = -3376397475643476847L;

	/**
	 * flag for logged in status
	 */
	private boolean logged = false;

	/**
	 * URL path
	 */
	private String path;

	/**
	 * User unique nickname.
	 */
	private String nick;

	/**
	 * blog id which is edited
	 */
	private Long blogId;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		path = req.getPathInfo();

		logged = req.getSession().getAttribute("current.user.id") != null;
		path = path.substring(1);

		if (path.indexOf('/') == -1) {
			if (logged) {
				logged = path.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			nick = path;
			renderBlogList(req, resp);
			return;
		} else if (path.indexOf("/new") != -1) {	// add new blog
			nick = path.substring(0, path.indexOf('/'));
			if (logged) {
				logged = nick.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			renderNewBlog(req, resp);
			return;
		} else if (path.indexOf("/edit") != -1) {	// edit blog
			nick = path.substring(0, path.indexOf('/'));
			if (logged) {
				logged = nick.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			renderEditBlog(req, resp);
			return;
		} else if (path.matches(".+/\\d+")) {
			int index = path.indexOf('/');
			nick = path.substring(0, index);
			if (logged) {
				logged = nick.equals(req.getSession().getAttribute(
						"current.user.nick"));
			}
			blogId = Long.valueOf(path.substring(index + 1));
			renderBlogEntry(req, resp);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

	/**
	 * 
	 * Method that renders all blog entries this user has created.
	 * @param req
	 *            server request.
	 * @param resp
	 *            server response.
	 * @throws ServletException 
	 * @throws IOException 
	 */
	private void renderBlogList(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntriesForNick(
				nick);
		req.setAttribute("entries", entries);
		req.setAttribute("autor", nick);
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/BlogsList.jsp").forward(req,
				resp);
	}

	/**
	 * Renders new form for adding new blog entry.
	 * @param req servlet request
	 * @param resp servlet response
	 * @throws ServletException 
	 * @throws IOException 
	 */
	private void renderNewBlog(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (logged == false || checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}

		BlogEntryForm fm = new BlogEntryForm();
		BlogEntry e = new BlogEntry();
		fm.fillFromBlogEntry(e);

		req.setAttribute("autor", nick);
		req.setAttribute("form", fm);
		req.setAttribute("mode", "new");
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp").forward(req,
				resp);
	}

	/**
	 * Renders form for editing blog entry.
	 * 
	 * @param req servlet request
	 * @param resp servlet response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void renderEditBlog(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (logged == false || checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}

		blogId = (Long) req.getSession().getAttribute("blogID");
	
		BlogEntryForm fm = new BlogEntryForm();
		BlogEntry e = DAOProvider.getDAO().getBlogEntry(blogId);
		
		if (e == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}
		
		fm.fillFromBlogEntry(e);

		req.setAttribute("autor", nick);
		req.setAttribute("form", fm);
		req.setAttribute("mode", "edit");
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp").forward(req,
				resp);
	}

	/**
	 * 
	 * Method for fetching blog entry.
	 * @param req servlet request
	 * @param resp servlet response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void renderBlogEntry(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		
		BlogEntry e = DAOProvider.getDAO().getBlogEntry(blogId);
		if (e == null || checkIfNickExists(nick) == false) {
			resp.sendRedirect(req.getContextPath() + "/servleti/error");
			return;
		}

		req.setAttribute("form", e);
		req.setAttribute("autor", nick);
		req.setAttribute("loged", logged);
		req.getRequestDispatcher("/WEB-INF/pages/ShowBlog.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String metodaBlog = req.getParameter("metoda-Blog");
		if (metodaBlog != null) {
			String path = req.getPathInfo().substring(1);
			String autor = path.substring(0, path.indexOf('/'));
			if (!metodaBlog.equals("Save")) {
				resp.sendRedirect(req.getContextPath() + "/servleti/author/"
						+ autor);
				return;
			}

			BlogEntry novi = new BlogEntry();
			BlogEntryForm fm = new BlogEntryForm();
			fm.fillFromHttpRequest(req);
			fm.validate();
			
			if (fm.hasErrors()) {
				req.setAttribute("form", fm);
				req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp").forward(req,
						resp);
				return;
			}
			
			fm.fillBlogEntry(novi);
			novi.setCreatedAt(new Date());
			novi.setLastModifiedAt(new Date());

			BlogUser usr = DAOProvider.getDAO().getBlogUser(autor);
			if (usr != null) {
				novi.setCreator(usr);
			} 

			if (req.getParameter("id") != null) {
				Long id = Long.valueOf(req.getParameter("id"));
				BlogEntry stari = DAOProvider.getDAO().getBlogEntry(id);
				stari.setLastModifiedAt(novi.getLastModifiedAt());
				stari.setTitle(novi.getTitle());
				stari.setText(novi.getText());
				DAOProvider.getDAO().addUpdateBlogEntry(stari, true);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/"
						+ autor);
				return;
			}

			DAOProvider.getDAO().addUpdateBlogEntry(novi, false);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/"
					+ autor);
			return;
		}

		String metodaKomentar = req.getParameter("metoda-komentar");
		if (metodaKomentar != null) {

			String komentar = req.getParameter("comment");

			Long blogID = Long.valueOf(req.getParameter("blogID"));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(blogID);

			BlogComment comment = new BlogComment();
			comment.setBlogEntry(entry);
			comment.setMessage(komentar);
			comment.setPostedOn(new Date());

			if (req.getSession().getAttribute("current.user.nick") != null) {
				String nick = (String) req.getSession().getAttribute(
						"current.user.nick");
				BlogUser usr = DAOProvider.getDAO().getBlogUser(nick);
				if (usr != null) {
					comment.setUsersEMail(usr.getEmail());
				}
			} else {
				comment.setUsersEMail("anonymous@blog.com");
			}
			DAOProvider.getDAO().addComment(comment);

			resp.sendRedirect(req.getContextPath() + req.getServletPath()
					+ req.getPathInfo());
			return;
		}
		
	}
	
	/**
	 * Checks if given nickname is already stored in databased (it's already in use)
	 * @param nickname nickname to be tested
	 * @return <tt>true</tt> if nickname is used, <tt>false</tt> otherwise
	 */
	private boolean checkIfNickExists(String nickname) {
		return DAOProvider.getDAO().getBlogUser(nickname) != null;
	}
}