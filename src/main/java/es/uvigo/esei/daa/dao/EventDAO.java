package es.uvigo.esei.daa.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Blob;
import java.util.Base64;

import es.uvigo.esei.daa.entities.Event;
import es.uvigo.esei.daa.util.ImageAdapter;

/**
 * DAO class for the {@link Event} entities.
 * 
 * @author Aarón Iglesias Mosteiro.
 * @author Nicolás Cid Gómez
 *
 */
public class EventDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(EventDAO.class.getName());

	/**
	 * Returns a list with all the events persisted in the system.
	 * 
	 * @return a list with all the events persisted in the system restricted by a page and ordered by date.
	 * @param page segmentation of the listed events
	 * @throws DAOException if an error happens while retrieving the events.
	 */
	public List<Event> list(int page) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM event WHERE eventdate>=? ORDER BY eventdate LIMIT 6 OFFSET ?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setTimestamp(1, Timestamp.valueOf(java.time.LocalDateTime.now()));
				statement.setInt(2, page * 6);
				try (final ResultSet result = statement.executeQuery()) {
					final List<Event> events = new LinkedList<>();
					
					while (result.next()) {
						events.add(rowToEntity(result));
					}

					return events;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Return the number of pages (events/6 rounded up)
	 * 
	 * @return a list with all the events persisted in the system restricted by a page and ordered by date.
	 * @throws DAOException if an error happens while retrieving the events.
	 */
	public int numberOfPages() throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT COUNT(*) as totalPages FROM event ";
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return (result.getInt("totalPages") + 5) / 6;
					} else {
						throw new DAOException("Error counting events");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns an event
	 * 
	 * @return event that has a certain id
	 * @param  the id of an event
	 * @throws DAOException if an error happens while retrieving the events.
	 * @throws IllegalArgumentException if an error happens because the id is invalid
	 */
	public Event get(int id) throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM event WHERE id=?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);

				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting a event", e);
			throw new DAOException(e);
		}
	}
	/**
	 * Returns a list with events in the system that has a certain title or description.
	 * @param  a string called matcher that is used in order to find the list of events
	 * @return a list with events in the system that has a certain title or description.
	 * @throws DAOException if an error happens while retrieving the events.
	 */
	public List<Event> find(String matcher, int page) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM ("
					+ "(SELECT * , 1 as roworder FROM event WHERE title LIKE ?)"
					+ "UNION (SELECT *, 2 as roworder FROM event WHERE description LIKE ? AND id NOT IN (SELECT id FROM event WHERE title LIKE ?))) foo ORDER BY roworder,eventdate LIMIT 6 OFFSET ? ";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				matcher = "%" + matcher + "%";
				statement.setString(1, matcher);
				statement.setString(2, matcher);
				statement.setString(3, matcher);
				statement.setInt(4, page * 6);

				try (final ResultSet result = statement.executeQuery()) {
					final List<Event> events = new LinkedList<>();
					while (result.next()) {
						events.add(rowToEntity(result));
					}

					return events;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Return the number of pages (events/6 rounded up)
	 * 
	 * @return a list with all the events persisted in the system restricted by a page and ordered by date.
	 * @throws DAOException if an error happens while retrieving the events.
	 */
	public int searchNumberOfPages(String matcher) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT COUNT(*) as totalPages FROM ("
					+ "(SELECT * FROM event WHERE title LIKE ? ORDER BY eventdate)"
					+ "UNION (SELECT * FROM event WHERE description LIKE ? ORDER BY eventdate)) foo";
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				matcher = "%" + matcher + "%";
				statement.setString(1, matcher);
				statement.setString(2, matcher);
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return (result.getInt("totalPages") + 5) / 6;
					} else {
						throw new DAOException("Error counting events");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns an event
	 * 
	 * @return event that has a certain id
	 * @param  the id of an event
	 * @throws DAOException if an error happens while retrieving the events.
	 * @throws IllegalArgumentException if an error happens becouse the id is invalid
	 */
	public String getNameCreator(int managerId) throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT login FROM users WHERE id=?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, managerId);

				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return result.getString("login");
					} else {
						throw new IllegalArgumentException("Invalid managerId");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting name of the creator", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns an Event created with the attributes of the row
	 * @param  ResultSet containing the row 
	 * @return an event created with the attributes of the row
	 * @throws SQLException if an error happens in the SQL.
	 * @throws IOException if an error happens.
	 */
	private Event rowToEntity(ResultSet row) throws SQLException {
		String image = null;
		String extension = null;
		try {
			if (row.getBlob("image") != null && row.getBlob("image").length() != 0) {
				extension = row.getString("extension");
			}
			else {
				extension = "jpeg";
			}
			image = blobToImage(row.getBlob("image"), row.getString("category"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new Event(row.getInt("id"), row.getString("title"), row.getString("description"),
					row.getString("category"), row.getTimestamp("eventdate").toLocalDateTime(),
					row.getString("place"), row.getInt("capacity"), image, extension, row.getInt("managerId"));


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Returns a string of the image, or the default image to the category if there is none
	 * @param  a blob image containing the image
	 * @param  a string with the category of the event
	 * @return a string of the image in base64
	 * @throws SQLException if an error happens in the SQL.
	 * @throws IOException if an error happens.
	 */
	public static String blobToImage(Blob image, String category) throws SQLException, IOException {
		byte[] imageData;
		String toret;
		if (image != null && image.length() != 0) {
			imageData = image.getBytes(1, (int) image.length());
			toret = Base64.getEncoder().encodeToString(imageData);
		} else {
			toret = ImageAdapter.pathToImage("/images/" + category + ".jpg");
		}
		return toret;
	}
}
