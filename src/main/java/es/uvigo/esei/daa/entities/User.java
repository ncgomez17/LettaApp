package es.uvigo.esei.daa.entities;

/**
 * An entity that represents a user of the applition .
 * 
 * @author Aaron Iglesias Mosteiro
 */

public class User {
	private int id;
	private String login;
	private String password;
	private String role;
	private String mail;
	
	/**
	 * Constructs a new instance of {@link User}.
	 *
	 * @param id identifier of the user.
	 * @param login login of the user.
	 * @param password password of the user.
	 * @param role role of the user.
	 * @param mail mail of the user.
	 */
	public User (int id,String login,String password ,String role,String mail) {
		this.id=id;
		this.setLogin(login);
		this.setPassword(password);
		this.setRole(role);
		this.setMail(mail);
	}
	
	/**
	 * Returns the identifier of the user.
	 * 
	 * @return the identifier of the user.
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * Returns the login of the user.
	 * 
	 * @return the login of the user.
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Set the login of this user.
	 * 
	 * @param login the new login of the user.
	 * @throws NullPointerException if the {@code login} is {@code null}.
	 */
	public void setLogin(String login) throws NullPointerException {

		if (login == null) {
			throw new NullPointerException("El login no puede ser vacío");
		} else {
			this.login = login;
		}
	}
	/**
	 * Returns the password of the user.
	 * 
	 * @return the password of the user.
	 */
	public String getPassword() {

		return this.password;

	}
	/**
	 * Set the password of this user.
	 * 
	 * @param password the new password of the user.
	 * @throws NullPointerException if the {@code password} is {@code null}.
	 * @throws IllegalArgumentException if the password has less than 8 characters
	 */
	public void setPassword(String password) throws NullPointerException,IllegalArgumentException {
		if(password==null) {
			throw new NullPointerException("La password no puede ser vacía");
		}else if(password.length()<8) {
			throw new IllegalArgumentException("La password debe tener al menos ocho caracteres");
		}else {
			this.password = password;
		}
	}
	/**
	 * Returns the role of the user.
	 * 
	 * @return the role of the user.
	 */
	public String getRole() {
		return this.role;
	}
	/**
	 * Set the role of this user.
	 * 
	 * @param role the new role of the user.
	 * @throws NullPointerException if the {@code role} is {@code null}.
	 */
	public void setRole(String role)throws NullPointerException {
		if(role==null) {
			throw new NullPointerException("El usuario debe de tener un rol asignado");
		}else {
			this.role = role;
		}
		
	}
	/**
	 * Returns the mail of the user.
	 * 
	 * @return the mail of the user.
	 */
	public String getMail() {
		return this.mail;
	}
	/**
	 * Set the mail of this user.
	 * 
	 * @param mail the new mail of the user.
	 * @throws NullPointerException if the {@code mail} is {@code null}.
	 */
	public void setMail(String mail) throws NullPointerException{
		if(mail==null) {
			throw new NullPointerException("El usuario debe tener una cuenta de correo asignada");
		}else {
			this.mail = mail;
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
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
