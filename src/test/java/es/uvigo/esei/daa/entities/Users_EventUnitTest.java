package es.uvigo.esei.daa.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.activation.MimetypesFileTypeMap;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class Users_EventUnitTest {
	
	@Test
	public void testUser_EventGetters() throws IOException {
		final int id = 1;
		final int idUser = 2;
		final int idEvent = 3;
		

		final Users_Event userEvent = new Users_Event(id,idUser,idEvent);

		assertThat(userEvent.getId(), is(equalTo(id)));
		assertThat(userEvent.getEvent_id(), is(equalTo(idEvent)));
		assertThat(userEvent.getUser_id(), is(equalTo(idUser)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testNegativeUser() {
		final int id = 1;
		final int idUser = -2;
		final int idEvent = 3;
		

		final Users_Event userEvent = new Users_Event(id,idUser,idEvent);
	}
	
	public void testSetUser() {
		final int id = 1;
		final int idUser = 2;
		final int idEvent = 3;
		

		final Users_Event userEvent = new Users_Event(id,8,idEvent);
		userEvent.setUser_id(idUser);
		
		assertThat(userEvent.getId(), is(equalTo(id)));
		assertThat(userEvent.getEvent_id(), is(equalTo(idEvent)));
		assertThat(userEvent.getUser_id(), is(equalTo(idUser)));
	}
	

	public void testSetEvent() {
		final int id = 1;
		final int idUser = 2;
		final int idEvent = 3;
		

		final Users_Event userEvent = new Users_Event(id,idUser,1);
		userEvent.setEvent_id(idEvent);
		
		assertThat(userEvent.getId(), is(equalTo(id)));
		assertThat(userEvent.getEvent_id(), is(equalTo(idEvent)));
		assertThat(userEvent.getUser_id(), is(equalTo(idUser)));
	}
	
	
	@Test
	public void testEqualsObject() {
		final Users_Event userEventA = new Users_Event(1, 2,2);
		final Users_Event userEventB = new Users_Event(1, 3,3);

		assertTrue(userEventA.equals(userEventB));
	}
	
	
	@Test
	public void testEqualsHashcode() {
		EqualsVerifier
				.forClass(Users_Event.class).withIgnoredFields("user_id", "event_id")
				.suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS).verify();
	}

	

}
