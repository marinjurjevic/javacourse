package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.dao.DAOProvider;

/**
 * Main application servlet, app entry point.
 * 
 * @author Marin Jurjevic
 * 
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * serial UID
	 */
	private static final long serialVersionUID = -8547478610134552065L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		process(req, resp);
	}

	/**
	 * Main method for that prepares already registered users.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().setAttribute("register", null);
		if (req.getParameter("metoda") != null) {
			checkLogin(req);
		} else {
			req.getSession().setAttribute("greska",null);
		}
		
		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
	}

	/**
	 * Checks for login action.
	 * @param req servlet request
	 */
	private void checkLogin(HttpServletRequest req) {

		if (req.getParameter("metoda").equals("Login")) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");

			password = SHACrypto.hashValue(password);

			req.getSession().setAttribute("current.user.nick", username);
			BlogUser usr = DAOProvider.getDAO().getBlogUser(username);
			if (usr != null) {

				if (password.equals(usr.getPasswordHash())) {
					req.getSession().setAttribute("current.user.fn",
							usr.getFirstName());
					req.getSession().setAttribute("current.user.ln",
							usr.getLastName());
					req.getSession().setAttribute("current.user.id",
							usr.getId());
				} else {
					req.getSession().setAttribute("error", true);
				}
			} else {
				req.getSession().setAttribute("error", true);
			}
		}
	}
}