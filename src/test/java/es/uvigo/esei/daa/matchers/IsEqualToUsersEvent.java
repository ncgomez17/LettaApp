package es.uvigo.esei.daa.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import es.uvigo.esei.daa.entities.Users_Event;

public class IsEqualToUsersEvent extends IsEqualToEntity<Users_Event> {
	public IsEqualToUsersEvent(Users_Event entity) {
		super(entity);
	}

	@Override
	protected boolean matchesSafely(Users_Event actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("id", Users_Event::getId, actual)
				&& checkAttribute("user_id", Users_Event::getUser_id, actual)
				&& checkAttribute("event_id", Users_Event::getEvent_id, actual);
		}
	}

	
	
	/**
	 * Factory method that creates a new {@link IsEqualToEntity} matcher with
	 * the provided {@link Users-Event} as the expected value.
	 * 
	 * @param users-event the expected event.
	 * @return a new {@link IsEqualToEntity} matcher with the provided
	 * {@link Users-Event} as the expected value.
	 */
	@Factory
	public static IsEqualToUsersEvent equalsToUsersEvent(Users_Event usersevent) {
		return new IsEqualToUsersEvent(usersevent);
	}
	
	/**
	 * Factory method that returns a new {@link Matcher} that includes several
	 * {@link IsEqualToUsersEvent} matchers, each one using an {@link Users_Event} of the
	 * provided ones as the expected value.
	 * 
	 * @param users-events the users-events to be used as the expected values.
	 * @return a new {@link Matcher} that includes several
	 * {@link IsEqualToUsersEvent} matchers, each one using an {@link Users_Event} of the
	 * provided ones as the expected value.
	 * @see IsEqualToEntity#containsEntityInAnyOrder(java.util.function.Function, Object...)
	 */
	@Factory
	public static Matcher<Iterable<? extends Users_Event>> containsUserEventInOrder(Users_Event ... usersevents) {
		return containsEntityInAnyOrder(IsEqualToUsersEvent::equalsToUsersEvent, usersevents);
	}
	

}