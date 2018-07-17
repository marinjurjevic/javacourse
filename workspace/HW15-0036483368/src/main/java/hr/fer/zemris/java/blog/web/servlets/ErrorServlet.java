package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for forwarding error to JSP page.
 * @author Marin Jurjevic
 *
 */
@WebServlet("/servleti/error")
public class ErrorServlet extends HttpServlet {

	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 5076420141081804727L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req,
				resp);
	}
}