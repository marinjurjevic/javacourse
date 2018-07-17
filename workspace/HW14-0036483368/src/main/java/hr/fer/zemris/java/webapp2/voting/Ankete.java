package hr.fer.zemris.java.webapp2.voting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;

/**
 * Main servlet for preparing all available polls in this application.
 * 
 * @author Marin Jurjevic
 *
 */

@WebServlet("/index.html")
public class Ankete extends HttpServlet {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DataSource ds = (DataSource) req.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}

		SQLConnectionProvider.setConnection(con);
		List<Poll> polls = DAOProvider.getDao().getPolls();

		req.getSession().setAttribute("polls", polls);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
