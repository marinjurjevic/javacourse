package hr.fer.zemris.java.webapp2.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Servlet for creating XLS representation of results in a table. Only bands
 * with at least one vote will be processd.
 * 
 * @author Marin Jurjevic
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeRezultatiXLS extends HttpServlet {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PollOption> options = DAOProvider.getDao()
				.getPollOptions(((Poll) req.getSession().getAttribute("currentPoll")).getId());

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati ankete");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Natjecatelj");
		rowhead.createCell(1).setCellValue("Broj glasova");

		int i = 1;

		for (PollOption o : options) {
			HSSFRow row = sheet.createRow(i++);
			row.createCell(0).setCellValue(o.getOptionTitle());
			row.createCell(1).setCellValue(o.getVotesCount());
		}

		resp.setContentType("application/vnd.ms-excel");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

}
