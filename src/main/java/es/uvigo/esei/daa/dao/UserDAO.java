package es.uvigo.esei.daa.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.entities.User;

/**
 * DAO class for managing the users of the system.
 * 
 * @author Nicolas
 * @author Iria
 */

public class UserDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(UserDAO.class.getName());

	/**
	 * Returns a user stored persisted in the system.
	 * 
	 * @param login the login of the user to be retrieved.
	 * @return a user with the provided login.
	 * @throws DAOException             if an error happens while retrieving the
	 *                                  user.
	 * @throws IllegalArgumentException if the provided login does not corresponds
	 *                                  with any persisted user.
	 */
	public User get(String login) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM users WHERE login=?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, login);

				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error checking login", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Checks if the provided credentials (login and password) correspond with a
	 * valid user registered in the system.
	 * 
	 * <p>The password is stored in the system "salted" and encoded with the
	 * SHA-256 algorithm.</p>
	 * 
	 * @param login the login of the user.
	 * @param password the password of the user.
	 * @return {@code true} if the credentials are valid. {@code false}
	 * otherwise.
	 * @throws DAOException if an error happens while checking the credentials.
	 */
	public boolean checkLogin(String login, String password) throws DAOException {
		try {
			final User user = this.get(login);
			
			final String dbPassword = user.getPassword();
			final String shaPassword = encodeSha256(password);
			
			return shaPassword.equals(dbPassword);
		} catch (IllegalArgumentException iae) {
			return false;
		}
	}
	
	/**
	 * Encrypted the password to SHA-256
	 * @param password the password of the user 
	 * @return String with the password encrypted
	 * otherwise.
	 * @throws NoSuchAlgorithmException if an error happens while using the algorithm SHA-256.
	 */
	private final static String encodeSha256(String password) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] digested = digest.digest(password.getBytes());
			
			return hexToString(digested);
		} catch (NoSuchAlgorithmException e) {
			LOG.log(Level.SEVERE, "SHA-256 not supported", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Transform hexadecimal bytes to String
	 * @param bytes hexadecimal  
	 * @return String of the bytes
	 */
	private final static String hexToString(byte[] hex) {
		final StringBuilder sb = new StringBuilder();
		
		for (byte b : hex) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		return sb.toString();
	}

	/**
	 * Returns an User created with the attributes of the row
	 * @param  ResultSet containing the row 
	 * @return an user created with the attributes of the row
	 * @throws SQLException if an error happens in the SQL.
	 */
	private User rowToEntity(ResultSet result) throws SQLException {
		return new User(result.getInt("id"),result.getString("login"), result.getString("password"), result.getString("role"),result.getString("mail"));
	}

}