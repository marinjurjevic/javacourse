package hr.fer.zemris.java.webapp2.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Servlet used for voting for chosen band.
 * 
 * @author Marin Jurjevic
 *
 */

@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));

		DAOProvider.getDao().updateVotes(id);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
