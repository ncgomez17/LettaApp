package es.uvigo.esei.daa.rest;

import static es.uvigo.esei.daa.dataset.EventsDataset.*;
import static es.uvigo.esei.daa.dataset.UsersEventsDataset.existentIdEvent;
import static es.uvigo.esei.daa.dataset.UsersEventsDataset.existentIdUser;
import static es.uvigo.esei.daa.dataset.UsersEventsDataset.newUsersEvent;
import static es.uvigo.esei.daa.dataset.UsersEventsDataset.nonExistentIdEvent;
import static es.uvigo.esei.daa.dataset.UsersEventsDataset.nonExistentIdUser;
import static es.uvigo.esei.daa.dataset.UsersEventsDataset.registeredIdEvent;
import static es.uvigo.esei.daa.dataset.UsersEventsDataset.registeredIdUser;
import static es.uvigo.esei.daa.matchers.IsEqualToEvent.equalsToEvent;
import static es.uvigo.esei.daa.matchers.IsEqualToUsersEvent.equalsToUsersEvent;
import static es.uvigo.esei.daa.matchers.IsEqualToEvent.containsEventInOrder;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasOkStatus;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import es.uvigo.esei.daa.LETTATestApplication;
import es.uvigo.esei.daa.entities.Event;
import es.uvigo.esei.daa.entities.Users_Event;
import es.uvigo.esei.daa.listeners.ApplicationContextBinding;
import es.uvigo.esei.daa.listeners.ApplicationContextJndiBindingTestExecutionListener;
import es.uvigo.esei.daa.listeners.DbManagement;
import es.uvigo.esei.daa.listeners.DbManagementTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/mem-context.xml")
@TestExecutionListeners({
	DbUnitTestExecutionListener.class,
	DbManagementTestExecutionListener.class,
	ApplicationContextJndiBindingTestExecutionListener.class
})
@ApplicationContextBinding(
	jndiUrl = "java:/comp/env/jdbc/letta",
	type = DataSource.class
)
@DbManagement(
	create = "classpath:db/hsqldb.sql",
	drop = "classpath:db/hsqldb-drop.sql"
)

@DatabaseSetup("/datasets/dataset.xml")
@ExpectedDatabase("/datasets/dataset.xml")
public class EventResourceTest extends JerseyTest {
	@Override
	protected Application configure() {
		return new LETTATestApplication();
	}

	@Override
	protected void configureClient(ClientConfig config) {
		super.configureClient(config);
		
		// Enables JSON transformation in client
		config.register(JacksonJsonProvider.class);
		config.property("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
	}
		
	@Test
	public void testList() throws IOException, SQLException {

		final Response response = target("event").request().get();
		
		assertThat(response, hasOkStatus());
		List<Event> events = null;
		try {
		events = response.readEntity(new GenericType<List<Event>>(){});
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		assertThat(response.getHeaderString("Event-Total-Pages"),is(equalTo("2")));
		assertThat(events, containsEventInOrder(event()));
	}
	
	@Test
	public void testListPage() throws IOException, SQLException {

		final Response response = target("event").queryParam("page", 1).request().get();
		
		assertThat(response, hasOkStatus());
		List<Event> events = null;
		try {
		events = response.readEntity(new GenericType<List<Event>>(){});
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		assertThat(response.getHeaderString("Event-Total-Pages"),is(equalTo("2")));
		assertThat(events, containsEventInOrder(eventPaged()));
	}

	@Test
	public void testGet() throws IOException, IllegalArgumentException, SQLException {
		final Response response = target("event/" + existentId()).request().get();
		assertThat(response, hasOkStatus());
		
		final Event event = response.readEntity(Event.class);
		assertThat(event, is(equalsToEvent(existentEvent())));
	}
	

	@Test
	public void testGetInvalidId() throws Exception {
		final Response response = target("event/" + nonExistentId()).request().get();
		
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void testListFind() throws IOException, SQLException {
		final Response response = target("event").queryParam("title", "Paseo por el parque").request().get();
		
		assertThat(response, hasOkStatus());
		List<Event> events = null;
		try {
		events = response.readEntity(new GenericType<List<Event>>(){});
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		assertThat(events.get(0), is(equalsToEvent(event()[2])));
	}

	
	@Test
	public void testListNotFound() throws IOException, SQLException {
		final Response response = target("event").queryParam("title", "Acabar DAA").request().get();
		
		assertThat(response, hasOkStatus());
		List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
		
		assertThat(events.size(),  is(equalTo(0)));

	}
	
	@Test
	@ExpectedDatabase("/datasets/dataset-addUsersEvents.xml")
	public void testAdd() throws IOException {
		final Form form = new Form();
		
		final Response response = target("event/" + existentIdEvent() + "/users/" + existentIdUser()).request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		// APPLICATION_FORM_URLENCODED_TYPE
		assertThat(response, hasOkStatus());
		
		final Users_Event userevent = response.readEntity(Users_Event.class);
		
		try {
			assertThat(userevent, is(equalsToUsersEvent(newUsersEvent())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPastAdd() throws IOException {
		final Form form = new Form();
		
		final Response response = target("event/" + pastExistentId() + "/users/" + existentIdUser()).request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		// APPLICATION_FORM_URLENCODED_TYPE
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void testCapacityAdd() throws IOException {
		final Form form = new Form();
		
		final Response response = target("event/" + filledExistentId() + "/users/" + existentIdUser()).request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		// APPLICATION_FORM_URLENCODED_TYPE
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void testAddNonExistentUser() throws IOException {
		final Form form = new Form();
		
		final Response response = target("event/" + existentIdEvent() + "/users/" + nonExistentIdUser()).request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		// APPLICATION_FORM_URLENCODED_TYPE
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void testAddNonExistentEvent() throws IOException {
		final Form form = new Form();
		
		final Response response = target("event/" + nonExistentIdEvent() + "/users/" + existentIdUser()).request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		// APPLICATION_FORM_URLENCODED_TYPE
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void duplicateRegistrationEvent() throws IOException {
		final Form form = new Form();
		
		final Response response = target("event/" + registeredIdEvent() + "/users/" + registeredIdUser()).request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		// APPLICATION_FORM_URLENCODED_TYPE
		assertThat(response, hasBadRequestStatus());
	}
	
}