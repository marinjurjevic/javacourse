package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for user logout.
 * 
 * @author Marin Jurjevic
 * 
 */
@WebServlet("/servleti/logout")
public class LogOutServlet extends HttpServlet {

	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 7173669682171469759L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().invalidate();
		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/main");
	}
}