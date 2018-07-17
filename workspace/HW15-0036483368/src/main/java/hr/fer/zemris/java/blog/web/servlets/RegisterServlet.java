package hr.fer.zemris.java.blog.web.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.blog.form.BlogUserForm;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.dao.DAOProvider;

/**
 * Servlet for registering new users in blog application.
 * @author Marin Jurjevic
 * 
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
	/**
	 * serial UID
	 */
	private static final long serialVersionUID = -6111149461093850042L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		processRequest(req, resp);
	}

	/**
	 * Method that process user registration form.
	 * @param req servlet request
	 * @param resp servlet response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String metoda = req.getParameter("metoda");
		if ("Cancel".equals(metoda)) {
			req.getSession().setAttribute("register", null);
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		}

		if (req.getSession().getAttribute("register") == null) {

			BlogUserForm fm = new BlogUserForm();
			BlogUser u = new BlogUser();
			fm.fillFromBlogUser(u);

			req.getSession().setAttribute("register", true);
			req.setAttribute("form", fm);
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(
					req, resp);
			return;

		} else {
			String nick = req.getParameter("nick");

			BlogUser usr = DAOProvider.getDAO().getBlogUser(nick);
			boolean nickUsed = false;
			if (usr != null) {
				nickUsed = true;
			}
			BlogUserForm fm = new BlogUserForm();
			fm.fillFromHttpRequesta(req);
			fm.validate(nickUsed);
			
			if (fm.hasErrors()) {
				req.setAttribute("form", fm);
				req.getRequestDispatcher("/WEB-INF/pages/Register.jsp")
						.forward(req, resp);
				return;
			}

			req.getSession().setAttribute("register", null);
			BlogUser u = new BlogUser();
			fm.fillBlogUser(u);

			String hash = SHACrypto.hashValue(u.getPasswordHash());
			u.setPasswordHash(hash);

			DAOProvider.getDAO().addBlogUser(u);
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
		}
	}
}