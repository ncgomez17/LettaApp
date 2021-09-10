package es.uvigo.esei.daa.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.daa.dao.DAOException;
import es.uvigo.esei.daa.dao.EventDAO;
import es.uvigo.esei.daa.dao.User_EventDAO;
import es.uvigo.esei.daa.entities.Event;
import es.uvigo.esei.daa.entities.Users_Event;

/**
 * REST resource for managing event.
 * 
 * @author Aarón Iglesias Mosteiro.
 * @author Nicolás Cid Gómez
 */
@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
	private final static Logger LOG = Logger.getLogger(EventResource.class.getName());

	private final EventDAO dao;
	private final User_EventDAO relationshipDao;

	/**
	 * Constructs a new instance of {@link EventResource}.
	 */
	public EventResource() {
		this(new EventDAO(), new User_EventDAO());
	}

	// Needed for testing purposes
	EventResource(EventDAO dao, User_EventDAO relationshipDAO) {
		this.dao = dao;
		this.relationshipDao = relationshipDAO;
	}

	/**
	 * Returns the page segmented list of events stored in the system, or the events
	 * matched by the title parameter
	 * 
	 * @return a 200 OK response with the page segmented list of events stored in
	 *         the system, segmented by the page parameter. Or with the events
	 *         matched by the title if {@code title} is not null. If an error
	 *         happens while retrieving the list, a 500 Internal Server Error
	 *         response with an error message will be returned.
	 * @param title search param in search events query by title
	 * @param page  segmentation of the listed events
	 */
	@GET
	public Response list(@QueryParam("title") String title, @QueryParam("page") int page) {

		try {
			if (title == null) {
				return Response.ok(this.dao.list(page)).header("Event-Total-Pages", this.dao.numberOfPages()).build();

			}else {
				final List<Event> events = this.dao.find(title, page);


				return Response.ok(events).header("Event-Total-Pages", this.dao.searchNumberOfPages(title)).build();
			}
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * Returns a event with the provided identifier.
	 * 
	 * @param id the identifier of the event to retrieve.
	 * @return a 200 OK response with a event that has the provided identifier. If
	 *         the identifier does not corresponds with any event, a 400 Bad Request
	 *         response with an error message will be returned. If an error happens
	 *         while retrieving the list, a 500 Internal Server Error response with
	 *         an error message will be returned.
	 */
	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") int id) {
		try {
			final Event event = this.dao.get(id);
			return Response.ok(event).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid event id in get method", iae);

			return Response.status(Response.Status.BAD_REQUEST).entity(iae.getMessage()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a event", e);

			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * Returns the name of the creator of Event
	 * 
	 * @return a 200 OK response with the page segmented list of events stored in
	 *         the system, segmented by the page parameter. Or with the events
	 *         matched by the title if {@code managerId} is not null. If an error
	 *         happens gets Exception, a 500 Internal Server Error response with an
	 *         error message will be returned.
	 * @param managerId segmentation of the listed events
	 */
	@GET
	@Path("/creator/{managerId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response nameCreator(@PathParam("managerId") int managerId) {

		try {
			final String name = this.dao.getNameCreator(managerId);
			return Response.ok(name).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid managerId in get method", iae);

			return Response.status(Response.Status.BAD_REQUEST).entity(iae.getMessage()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a name of creator", e);

			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * Registers a user to an event
	 * 
	 * @param eventId the id of the event the user is going to get signed in
	 * @param userId  the id of the user that is going to get signed to an event
	 * @return a 200 OK response with a Users_Event that has been created. If the
	 *         either of the ids are not provided, a 400 Bad Request response with
	 *         an error message will be returned. 
	 *         Also if the date is in the past, the user is already registered, 
	 *         the capacity is filled, or the user is registered in an event with 
	 *         the same date returns an 400 Bad Request
	 *         If an error happens a 500 Internal
	 *         Server Error response with an error message will be returned.
	 */
	@POST
	@Path("/{eventId}/users/{userId}")
	public Response register(@PathParam("eventId") int eventId, @PathParam("userId") int userId) {
		try {
			Event tmp = this.dao.get(eventId);
			if (tmp.getDate().isBefore(java.time.LocalDateTime.now())
					|| this.relationshipDao.checkCapacity(eventId) >= tmp.getCapacity()
					|| this.relationshipDao.isRegistered(userId, eventId)
					) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			} else {
				boolean f = false;
				for(LocalDateTime i : this.relationshipDao.listSignedEvents(userId)) {
					if(i.equals(tmp.getDate()))
						f = true;
				}
				if(f) {
					return Response.status(Response.Status.BAD_REQUEST).build();
				}else {
					
				final Users_Event newPerson = this.relationshipDao.addUserToEvent(userId, eventId);

				return Response.ok(newPerson).build();
				}
			}
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid person or event id in register method", iae);

			return Response.status(Response.Status.BAD_REQUEST).entity(iae.getMessage()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error adding a person to an event", e);

			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}
