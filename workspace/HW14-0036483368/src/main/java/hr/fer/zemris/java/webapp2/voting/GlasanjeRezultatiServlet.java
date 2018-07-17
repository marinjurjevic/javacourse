package hr.fer.zemris.java.webapp2.voting;

import java.io.IOException;
import java.util.LinkedList;
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
 * Servlet for preparing information about current poll results. Only bands who
 * have recieved at least one vote will be processed.
 * 
 * @author Marin Jurjevic
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PollOption> results = DAOProvider.getDao()
				.getPollOptions(((Poll) req.getSession().getAttribute("currentPoll")).getId());
		long maxVotes = results.get(0).getVotesCount();

		List<PollOption> winners = new LinkedList<>();
		results.forEach(o -> {
			if (o.getVotesCount() == maxVotes) {
				winners.add(o);
			}
		});

		req.getSession().setAttribute("results", results);
		req.getSession().setAttribute("winners", winners);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
