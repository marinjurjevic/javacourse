package hr.fer.zemris.java.webapp2.voting;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Creates new Pie chart using JFreeChart library. Only bands who have at least
 * one vote will be rendered.
 * 
 * @author Marin Jurjevic
 *
 */

@WebServlet("/glasanje-grafika")
public class RezultatiGrafikon extends HttpServlet {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = resp.getOutputStream();
		resp.setContentType("image/png");
		PieDataset dataset = createDataset(req);

		JFreeChart chart = createChart(dataset, "Pita grafikon");
		ChartUtilities.writeChartAsPNG(out, chart, 400, 400);
	}

	/**
	 * Creates new dataset used in pie chart.
	 * 
	 * @param req
	 *            servlet request for retrieving results
	 * @return new PieDataset
	 */
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();
		List<PollOption> options = DAOProvider.getDao()
				.getPollOptions(((Poll) req.getSession().getAttribute("currentPoll")).getId());

		options.forEach(o -> {
			if (o.getVotesCount() > 0) {
				result.setValue(o.getOptionTitle(), o.getVotesCount());
			}
		});

		return result;
	}

	/**
	 * Creates new JFreeChart based on filled dataset.
	 * 
	 * @param dataset
	 *            chart dataset
	 * @param title
	 *            chart title
	 * @return new JFreeChart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, false, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setLabelGenerator(null);
		plot.setStartAngle(290);
		return chart;

	}

}
