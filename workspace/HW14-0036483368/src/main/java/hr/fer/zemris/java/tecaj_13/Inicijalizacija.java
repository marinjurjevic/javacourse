package hr.fer.zemris.java.tecaj_13;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Simple web listener for checking database configuration and preparing all
 * neccessary booting. In case database is empty, polls or poll options have
 * been deleted, this listener will uppon application booting insert some
 * default polls for voting.
 * 
 * @author Marin Jurjevic
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Path to database configuration file.
	 */
	private Path configPath;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		configPath = Paths.get(sce.getServletContext().getRealPath("/")+"/WEB-INF/dbsettings.properties");
		String connectionURL = createConnectionURL();

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionProvider.setConnection(con);

		DAO dao = DAOProvider.getDao();

		if (dao.createPollTable() || dao.getPolls().isEmpty()) {
			dao.createPollOptionsTable();
			fillPollsTable(dao);
		} else if (dao.createPollOptionsTable() || dao.getPollOptions(0).isEmpty()) {
			fillPollsTable(DAOProvider.getDao());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates connection URL by reading from database settings file.
	 * 
	 * @return string containing URL form for connecting database.
	 */
	private String createConnectionURL() {
		Properties config = new Properties();
		try {
			config.load(Files.newInputStream(configPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String dbName = config.getProperty("name");
		String host = config.getProperty("host");
		String port = config.getProperty("port");
		String user = config.getProperty("user");
		String pass = config.getProperty("password");
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
				+ pass;

		return connectionURL;
	}

	/**
	 * Fills database with default polls (bands and upcoming games)
	 * 
	 * @param dao
	 *            data access object
	 */
	private void fillPollsTable(DAO dao) {
		long pollID;

		pollID = dao.insertPoll(new Poll("Glasanje za bendove", "Glasajte za svoj omiljeni bend"));
		if (pollID != -1) {
			fillPollOptionsBands(dao, pollID);
		}

		pollID = dao
				.insertPoll(new Poll("Glasanje najbolju nadolazecu igru", "Glasajte za igru koju najvise iscekujete"));
		if (pollID != -1) {
			fillPollOptionsGames(dao, pollID);
		}
	}

	/**
	 * Fills table pollOptions with some options for bands poll.
	 * 
	 * @param dao
	 *            data acces object
	 * @param pollID
	 *            bands poll ID
	 */
	private void fillPollOptionsBands(DAO dao, long pollID) {
		dao.insertPollOption(new PollOption("The Beatles",
				"https://www.youtube.com/watch?v=NCtzkaL2t_Y&ab_channel=TheBeatlesVEVO", pollID));
		dao.insertPollOption(new PollOption("The Platters",
				"https://www.youtube.com/watch?v=H2di83WAOhU&ab_channel=OldManCrankyCane", pollID));
		dao.insertPollOption(new PollOption("The Beach Boys",
				"https://www.youtube.com/watch?v=Eab_beh07HU&ab_channel=StephenMcElvain", pollID));
		dao.insertPollOption(new PollOption("The Four Seasons",
				"https://www.youtube.com/watch?v=40bTOCv3_ak&ab_channel=onepapa2", pollID));
		dao.insertPollOption(new PollOption("The Marcels",
				"https://www.youtube.com/watch?v=v0fy1HeJv80&ab_channel=fabriziolencioni", pollID));
		dao.insertPollOption(new PollOption("The Everly Brothers",
				"https://www.youtube.com/watch?v=lvA-STM7oJk&ab_channel=felixbautista", pollID));
		dao.insertPollOption(new PollOption("The Mamas And The Papas",
				"https://www.youtube.com/watch?v=N-aK6JnyFmk&ab_channel=GoldHunting", pollID));
	}

	/**
	 * Fills table pollOptions with some options for upcoming games poll.
	 * 
	 * @param dao
	 *            data acces object
	 * @param pollID
	 *            games poll ID
	 */
	private void fillPollOptionsGames(DAO dao, long pollID) {
		dao.insertPollOption(new PollOption("FIFA 17",
				"https://www.youtube.com/watch?v=lPDOh5ZifyM&ab_channel=EASPORTSFIFA", pollID));
		dao.insertPollOption(new PollOption("Mafia III",
				"https://www.youtube.com/watch?v=j6dgC5RMXRs&ab_channel=PlayStation", pollID));
		dao.insertPollOption(new PollOption("God of War",
				"https://www.youtube.com/watch?v=CJ_GCPaKywg&ab_channel=PlayStation", pollID));
		dao.insertPollOption(new PollOption("Cossacks 3",
				"https://www.youtube.com/watch?v=9C7muvfliMs&ab_channel=moviesectorsk", pollID));
		dao.insertPollOption(new PollOption("Call of Duty: Infinite Warfare",
				"https://www.youtube.com/watch?v=EeF3UTkCoxY&ab_channel=CallofDuty", pollID));
		dao.insertPollOption(new PollOption("Battlefield 1",
				"https://www.youtube.com/watch?v=c7nRTF2SowQ&ab_channel=Battlefield", pollID));

	}

}