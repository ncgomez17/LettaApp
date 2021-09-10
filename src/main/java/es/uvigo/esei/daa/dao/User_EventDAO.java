package es.uvigo.esei.daa.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Blob;
import java.util.Base64;

import es.uvigo.esei.daa.entities.Event;
import es.uvigo.esei.daa.entities.Users_Event;
import es.uvigo.esei.daa.util.ImageAdapter;

/**
 * DAO class for the {@link Event} entities.
 * 
 * @author Aarón Iglesias Mosteiro.
 * @author Nicolás Cid Gómez
 *
 */
public class User_EventDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(User_EventDAO.class.getName());

	/**
	 * Returns a Users_Event entity with the relation of the users signed in an event
	 * 
	 * @return a new Users_Event entity with the id of the relation
	 * @param userId the id of the user to be signed to an event
	 * @param eventId the id of the event the user is going to get signed in
	 * @throws SQLException if the query didn't execute properly
	 * @throws IllegalArgumentException if an error happens while retrieving the events.
	 */
	public Users_Event addUserToEvent(int userId, int eventId) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "INSERT INTO users_event VALUES (null, ?, ? )";

			try (final PreparedStatement statement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
				statement.setInt(1, userId);
				statement.setInt(2, eventId);
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Users_Event(resultKeys.getInt(1), userId, eventId);
						} else {
							LOG.log(Level.SEVERE, "Error retrieving inserted id");
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding a User-event relation", e);
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Returns the number of users registered in a particular event
	 * 
	 * @return the number of users registered in a particular event
	 * @param eventId the id of the event the capacity is checked
	 * @throws DAOException if an error happens while retrieving the users.
	 */
	public int checkCapacity(int eventId) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT COUNT(*) as capacity FROM users_event WHERE event_id = ?";
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, eventId);
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return result.getInt("capacity");
					} else {
						throw new DAOException("Error counting registed users");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error counting registed users", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns a list with the dates of the event a particular user is registered in
	 * 
	 * @return a list with the dates of the event a particular user is registered in
	 * @param userId the id of the user that is being listed
	 * @throws DAOException if an error happens while retrieving the dates.
	 */
	public List<LocalDateTime> listSignedEvents(int userId) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT eventdate FROM users_event join event ON users_event.event_id=event.id WHERE user_id = ?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, userId);
				try (final ResultSet result = statement.executeQuery()) {
					final List<LocalDateTime> dates = new LinkedList<>();
					
					while (result.next()) {
						dates.add(result.getTimestamp("eventdate").toLocalDateTime());
					}

					return dates;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Checks if a user is already registered on an event
	 * 
	 * @return true if the user is registered to the event, false otherwise
	 * @param userId the id of the user that is being checked
	 * @param event the id of the event that is being checked
	 * @throws DAOException if an error happens while retrieving the registrations.
	 */
	public boolean isRegistered(int userId, int eventId) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM users_event WHERE user_id = ? AND event_id = ?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, userId);
				statement.setInt(2, eventId);
				try (final ResultSet result = statement.executeQuery()) {

					return result.isFirst();
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error checking event registrations", e);
			throw new DAOException(e);
		}
	}
	
}
