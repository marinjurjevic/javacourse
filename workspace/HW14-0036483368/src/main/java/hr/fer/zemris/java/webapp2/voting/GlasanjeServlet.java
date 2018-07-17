package hr.fer.zemris.java.webapp2.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Main servlet for preparing all available poll options that are competing in
 * an online poll.
 * 
 * @author Marin Jurjevic
 *
 */

@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		Poll currentPoll = DAOProvider.getDao().getPoll(pollID);

		req.getSession().setAttribute("options", options);
		req.getSession().setAttribute("currentPoll", currentPoll);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
