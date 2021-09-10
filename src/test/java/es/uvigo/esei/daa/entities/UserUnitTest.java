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

public class UserUnitTest {
	
	@Test
	public void testUserGetters() throws IOException {
		final int id = 1;
		final String login = "user1";
		final String password = "12345678";
		final String role = "admin";
		final String mail = "user@mail.com";
		

		final User user = new User(id,login,password,role,mail);

		assertThat(user.getId(), is(equalTo(id)));
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo(password)));
		assertThat(user.getRole(), is(equalTo(role)));
		assertThat(user.getMail(), is(equalTo(mail)));
		

	}
	
	@Test(expected = NullPointerException.class)
	public void testUserNullLogin() {
		final int id = 1;
		final String login = null;
		final String password = "12345678";
		final String role = "admin";
		final String mail = "user@mail.com";
		

		new User(id,login,password,role,mail);
	}
	
	@Test(expected = NullPointerException.class)
	public void testUserNullPassword() {
		final int id = 1;
		final String login = "user1";
		final String password = null;
		final String role = "admin";
		final String mail = "user@mail.com";
		

		new User(id,login,password,role,mail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserTooShortPassword() {
		final int id = 1;
		final String login = "user1";
		final String password ="1234567";
		final String role = "admin";
		final String mail = "user@mail.com";
		

		new User(id,login,password,role,mail);
	}
	
	@Test(expected = NullPointerException.class)
	public void testUserNullRole() {
		final int id = 1;
		final String login = "user1";
		final String password ="12345678";
		final String role = null;
		final String mail = "user@mail.com";
		

		new User(id,login,password,role,mail);
	}
	
	@Test(expected = NullPointerException.class)
	public void testUserNullMail() {
		final int id = 1;
		final String login = "user1";
		final String password ="12345678";
		final String role = "admin";
		final String mail = null;
		

		new User(id,login,password,role,mail);
	}
	
	
	@Test
	public void testSetLogin() throws IOException {
		final int id = 1;
		final String login = "user1";
		final String password = "12345678";
		final String role = "admin";
		final String mail = "user@mail.com";
		

		final User user = new User(id,login,password,role,mail);
		
		user.setLogin("user2");

		assertThat(user.getId(), is(equalTo(id)));
		assertThat(user.getLogin(), is(equalTo("user2")));
		assertThat(user.getPassword(), is(equalTo(password)));
		assertThat(user.getRole(), is(equalTo(role)));
		assertThat(user.getMail(), is(equalTo(mail)));
		

	}
	
	@Test
	public void testSetPassword() throws IOException {
		final int id = 1;
		final String login = "user1";
		final String password = "12345678";
		final String role = "admin";
		final String mail = "user@mail.com";
		

		final User user = new User(id,login,password,role,mail);
		
		user.setPassword("87654321");

		assertThat(user.getId(), is(equalTo(id)));
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo("87654321")));
		assertThat(user.getRole(), is(equalTo(role)));
		assertThat(user.getMail(), is(equalTo(mail)));
		

	}
	
	
	@Test
	public void testSetRole() throws IOException {
		final int id = 1;
		final String login = "user1";
		final String password = "12345678";
		final String role = "admin";
		final String mail = "user@mail.com";
		

		final User user = new User(id,login,password,role,mail);
		
		user.setRole("user");

		assertThat(user.getId(), is(equalTo(id)));
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo(password)));
		assertThat(user.getRole(), is(equalTo("user")));
		assertThat(user.getMail(), is(equalTo(mail)));
		
	}
	
	
	@Test
	public void testSetMail() throws IOException {
		final int id = 1;
		final String login = "user1";
		final String password = "12345678";
		final String role = "admin";
		final String mail = "user@mail.com";
		

		final User user = new User(id,login,password,role,mail);
		
		user.setMail("user2@mail.com");

		assertThat(user.getId(), is(equalTo(id)));
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo(password)));
		assertThat(user.getRole(), is(equalTo(role)));
		assertThat(user.getMail(), is(equalTo("user2@mail.com")));
		
	}
	
	@Test
	public void testEqualsObject() {
		final User userA = new User(1, "user","12345678","user","user@mail.com");
		final User userB = new User(1, "user1","123456789","admin","user23@mail.com");

		assertTrue(userA.equals(userB));
	}
	
	
	@Test
	public void testEqualsHashcode() {
		EqualsVerifier
				.forClass(User.class).withIgnoredFields("login", "password", "role", "mail")
				.suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS).verify();
	}

	

}
