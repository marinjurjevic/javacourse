package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * 
 * This is implementation of DAO(Data Access Object) subsystem using SQL
 * technology. This implementation expects that database connection is always
 * ready to use and that will be provided by {@link SQLConnectionProvider}.
 * Before using this object, connection MUST be established or exception will
 * occur. See how to use webfilters for easy connection providing upon
 * application boot.
 * 
 * @author Marin Jurjevic
 */
public class SQLDAO implements DAO {

	@Override
	public boolean createPollTable() {
		Connection con = SQLConnectionProvider.getConnection();
		DatabaseMetaData dbm;
		try {
			dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "POLLS", null);
			if (!tables.next()) {
				Statement sta = con.createStatement();
				sta.executeUpdate("CREATE TABLE Polls" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ "title VARCHAR(150) NOT NULL," + "message CLOB(2048) NOT NULL)");

				System.out.println("Table Polls created.");
				sta.close();
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean createPollOptionsTable() {
		Connection con = SQLConnectionProvider.getConnection();
		DatabaseMetaData dbm;
		try {
			dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "POLLOPTIONS", null);
			if (!tables.next()) {
				Statement sta = con.createStatement();
				sta.executeUpdate("CREATE TABLE PollOptions" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ "optionTitle VARCHAR(100) NOT NULL," + "optionLink VARCHAR(150) NOT NULL," + "pollID BIGINT,"
						+ "votesCount BIGINT," + "FOREIGN KEY (pollID) REFERENCES Polls(id) )");

				System.out.println("Table OptionPolls created.");

				sta.close();
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Poll> getPolls() {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong("id"));
						poll.setTitle(rs.getString("title"));
						poll.setMessage(rs.getString("message"));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DAOException("Error while fetching list of available polls.", ex);
		}
		return polls;
	}

	@Override
	public Poll getPoll(long id) {
		Poll poll = new Poll();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls where id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				rs.next();
				poll.setId(rs.getLong("id"));
				poll.setTitle(rs.getString("title"));
				poll.setMessage(rs.getString("message"));

				rs.close();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DAOException("Error while fetching poll.", ex);
		}
		return poll;
	}

	@Override
	public List<PollOption> getPollOptions(long id) {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			if (id == 0) {
				pst = con.prepareStatement("select * from PollOptions");
			} else {
				pst = con.prepareStatement("select * from PollOptions where pollID=? order by votesCount DESC");
				pst.setLong(1, id);
			}
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption option = new PollOption();
						option.setId(rs.getLong("id"));
						option.setOptionTitle(rs.getString("optionTitle"));
						option.setOptionLink(rs.getString("optionLink"));
						option.setPollID(rs.getLong("pollID"));
						option.setVotesCount(rs.getLong("votesCount"));
						pollOptions.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while fetching list of available poll options.", ex);
		}
		return pollOptions;
	}

	@Override
	public long insertPoll(Poll poll) {
		Connection con = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, poll.getTitle());
			pst.setString(2, poll.getMessage());

			pst.executeUpdate();

			ResultSet rset = pst.getGeneratedKeys();
			try {
				if (rset != null && rset.next()) {
					long noviID = rset.getLong(1);
					System.out.println("Unos je obavljen i anketa je pohranjena pod ključem id=" + noviID);
					return noviID;
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return -1;
	}

	@Override
	public void insertPollOption(PollOption option) {
		Connection con = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, option.getOptionTitle());
			pst.setString(2, option.getOptionLink());
			pst.setLong(3, option.getPollID());
			pst.setLong(4, option.getVotesCount());

			pst.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void updateVotes(long optionID) {
		Connection con = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement("UPDATE PollOptions set votesCount = votesCount + 1 WHERE id=?");
			pst.setLong(1, optionID); // Napravi promjenu u retku s ID=2

			pst.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void removePoll(long id) {
	}

	@Override
	public void removePollOption(long id) {
	}

}