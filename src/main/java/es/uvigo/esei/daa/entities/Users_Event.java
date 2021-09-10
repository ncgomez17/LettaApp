package es.uvigo.esei.daa.entities;

import es.uvigo.esei.daa.util.*;
import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * An entity that represents a user in a event.
 * 
 * @author Iria Martínez Álvarez
 */
public class Users_Event {
	
	private int id;
	private int user_id;
	private int event_id;

	// Constructor needed for the JSON conversion
	public Users_Event() {}
	
	/**
	 * Constructs a new instance of {@link Users_Event}.
	 *
	 * @param id identifier of the user in the event.
	 * @param user_id identifier of the user.
	 * @param event_id identifier of the event.
	 */
	public Users_Event(int id, int user_id, int event_id) {
		this.id = id;
		this.setUser_id(user_id);
		this.setEvent_id(event_id);
	}
	/**
	 * Returns the identifier of the user in the event.
	 * 
	 * @return the identifier of the user in the event.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the identifier of the user.
	 * 
	 * @return the title of the event.
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * Set the user that is participating on the event.
	 * 
	 * @param user_id the new user_id of the event.
	 * @throws NullPointerException if the {@code user_id} is {@code -1}.
	 */
	public void setUser_id(int user_id) {
		if (user_id <0 ) {
			throw new NullPointerException("El usuario no puede ser negativo");

		} else {

			this.user_id = user_id;
		}
	}
	/**
	 * Returns the event_id of the event.
	 * 
	 * @return the event_id of the event.
	 */
	public int getEvent_id() {

		return event_id;
	}
	/**
	 * Set the event_id of this event.
	 * 
	 * @param event_id the new event of the user.
	 * @throws NullPointerException if the {@code event_id} is {@code -1}.
	 */
	public void setEvent_id(int event_id) {
		if (event_id < 0) {
			throw new NullPointerException("El evento no puede ser negativo");

		} else {
			this.event_id = event_id;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Users_Event))
			return false;
		Users_Event other = (Users_Event) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
