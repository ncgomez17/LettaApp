package es.uvigo.esei.daa.dataset;

import static java.util.Arrays.stream;
import java.io.IOException;
import java.sql.SQLException;

import es.uvigo.esei.daa.entities.Users_Event;

public final class UsersEventsDataset {
	private UsersEventsDataset() {}
	
	public static Users_Event[] users_event() throws SQLException {
		try {
			return new Users_Event[] {
					
				new Users_Event(1,1,1),	
				new Users_Event(2,2,1),
				new Users_Event(3,1,2),
				new Users_Event(4,3,9),

			};
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static Users_Event users_event(int id) throws IllegalArgumentException, SQLException {
		return stream(users_event())
			.filter(user_event -> user_event.getId() == id)
			.findAny()
		.orElseThrow(IllegalArgumentException::new);
	}
	
	public static int existentId() {
		return 1;
	}
	
	public static int nonExistentId() {
		return 1234;
	}
	public static Users_Event existentEvent() throws IllegalArgumentException, SQLException {
		return users_event(existentId());
	}
	

	public static Users_Event nonExistentEvent() throws IOException, SQLException {

		return new Users_Event (nonExistentId(), 10,11);
	}

	
	public static int existentIdUser() {
		return 4;
	}
	
	public static int existentIdEvent() {
		return 4;
	}
	
	public static int registeredIdUser() {
		return 1;
	}
	
	public static int registeredIdEvent() {
		return 1;
	}
	
	public static int nonExistentIdUser() {
		return 10;
	}
	
	public static int nonExistentIdEvent() {
		return 20;
	}

	public static Users_Event newUsersEvent() throws SQLException {
		return new Users_Event(users_event().length , existentIdUser(), existentIdEvent());
	}
 
}
