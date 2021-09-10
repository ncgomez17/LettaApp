package es.uvigo.esei.daa.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import es.uvigo.esei.daa.entities.User;
/**
 * Matcher for user entity .
 * 
 * @author Aaron Iglesias Mosteiro
 */
public class IsEqualToUser extends IsEqualToEntity<User>{
	public IsEqualToUser(User entity) {
		super(entity);
	}
	
	@Override
	protected boolean matchesSafely(User actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("id", User::getId, actual)
				&& checkAttribute("login", User::getLogin, actual)
				&& checkAttribute("password", User::getPassword, actual)
				&& checkAttribute("role", User::getRole, actual)
				&& checkAttribute("mail", User::getRole, actual);
		}
	}

	
	
	/**
	 * Factory method that creates a new {@link IsEqualToEntity} matcher with
	 * the provided {@link User} as the expected value.
	 * 
	 * @param event the expected event.
	 * {@link User} as the expected value.
	 */
	@Factory
	public static IsEqualToUser equalsToUser(User user) {
		return new IsEqualToUser(user);
	}
	
	/**
	 * Factory method that returns a new {@link Matcher} that includes several
	 * {@link IsEqualToUser} matchers, each one using an {@link USer} of the
	 * provided ones as the expected value.
	 * 
	 * @param events the events to be used as the expected values.
	 * @return a new {@link Matcher} that includes several
	 * {@link IsEqualToUser} matchers, each one using an {@link User} of the
	 * provided ones as the expected value.
	 * @see IsEqualToEntity#containsEntityInAnyOrder(java.util.function.Function, Object...)
	 */
	@Factory
	public static Matcher<Iterable<? extends User>> containsUserInAnyOrder(User ... users) {
		return containsEntityInAnyOrder(IsEqualToUser::equalsToUser, users);
	}
	
}
