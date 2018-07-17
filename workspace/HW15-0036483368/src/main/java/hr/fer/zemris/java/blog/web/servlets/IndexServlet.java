package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for redirecting to servlet main.
 * @author Marin Jurjevic
 * 
 */
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet {

	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 4821725724364276112L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
}