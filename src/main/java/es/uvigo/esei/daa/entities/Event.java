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
 * An entity that represents a event.
 * 
 * @author Iria Martínez Álvarez
 */
public class Event {
	
	private int id;
	private String title;
	private String description;
	private String category;
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = ParseDeserializer.class)
	private LocalDateTime date;
	private String place;
	private int capacity;
	private String image;
	private String extension;
	private int managerId;

	// Constructor needed for the JSON conversion
	public Event() {}
	
	/**
	 * Constructs a new instance of {@link Event}.
	 *
	 * @param id identifier of the event.
	 * @param title title of the event.
	 * @param description description of the event.
	 * @param category category of the event.
	 * @param date date of the event.
	 * @param place place of the event.
	 * @param capacity capacity of the event.
	 * @param image image of the event.
	 * @param extension extension of the image of the event.
	 * @param managerId identifier of the event manager
	 */
	public Event(int id, String title, String description, String category, LocalDateTime date, String place,
			int capacity, String image, String extension, int managerId) {
		this.id = id;
		this.setTitle(title);
		this.setDescription(description);
		this.setCategory(category);
		this.setDate(date);
		this.setPlace(place);
		this.setCapacity(capacity);
		this.setImage(image);
		this.setExtension(extension);
		this.setManagerId(managerId);
	}
	/**
	 * Returns the identifier of the event.
	 * 
	 * @return the identifier of the event.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the title of the event.
	 * 
	 * @return the title of the event.
	 */
	public String getTitle() {
		return title;
	}
	

	/**
	 * Set the title of this event.
	 * 
	 * @param title the new title of the event.
	 * @throws NullPointerException if the {@code title} is {@code null}.
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new NullPointerException("La categoria no puede estar vacia");

		} else {

			this.title = title;
		}
	}
	/**
	 * Returns the description of the event.
	 * 
	 * @return the description of the event.
	 */
	public String getDescription() {

		return description;
	}
	/**
	 * Set the description of this event.
	 * 
	 * @param description the new description of the event.
	 * @throws NullPointerException if the {@code description} is {@code null}.
	 */
	public void setDescription(String description) {
		if (description == null) {
			throw new NullPointerException("La descripcion no puede estar vacia");

		} else {
			this.description = description;
		}
	}
	/**
	 * Returns the category of the event.
	 * 
	 * @return the category of the event.
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * Set the category of this event.
	 * 
	 * @param category the new category of the event.
	 * @throws NullPointerException if the {@code category} is {@code null}.
	 */
	public void setCategory(String category) throws NullPointerException {

		if (category == null) {
			throw new NullPointerException("La categoria no puede estar vacia");

		} else {

			this.category = category;
		}
	}
	/**
	 * Returns the date of the event.
	 * 
	 * @return the date of the event.
	 */
	public LocalDateTime getDate() {

		return date;
	}
	/**
	 * Set the date of this event.
	 * 
	 * @param date the new date of the event.
	 * @throws NullPointerException if the {@code date} is {@code null}.
	 * @throws IllegalArgumentException if the date is before or equal to the current date
	 */
	public void setDate(LocalDateTime date) {
		if (date == null) {
			throw new NullPointerException("La fecha no puede estar vacia");
		}
		else {
			this.date = date;
		}
	}
	/**
	 * Returns the place of the event.
	 * 
	 * @return the place of the event.
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * Set the place of this event.
	 * 
	 * @param place the new place of the event.
	 * @throws NullPointerException if the {@code place} is {@code null}.
	 */
	public void setPlace(String place) {
		if (place == null) {
			throw new NullPointerException("El lugar no puede estar vacio");

		} else {
			this.place = place;
		}
	}
	/**
	 * Returns the capacity of the event.
	 * 
	 * @return the capacity of the event.
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * Set the capacity of this event.
	 * 
	 * @param capacity the new capacity of the event.
	 * @throws IllegalArgumentException if the capacity is below 2 or beyond 20
	 */
	public void setCapacity(int capacity) {
		if (capacity < 2 || capacity > 20) {
			throw new IllegalArgumentException("La capacidad tiene que ser entre 2 y 20 ");
		} else {

			this.capacity = capacity;
		}
	}
	/**
	 * Returns the image of the event.
	 * 
	 * @return the image of the event.
	 */
	public String getImage() {
		return image;
	}
	/**
	 * Set the capacity of this event.
	 * 
	 * @param capacity the new capacity of the event.
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * Returns the extension of the event.
	 * 
	 * @return the extension of the event.
	 */
	public String getExtension() {
		return this.extension;
	}
	/**
	 * Set the capacity of this event.
	 * 
	 * @param extension the new extension of the event.
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * Returns the id of the event manager.
	 * 
	 * @return the id of the event manager.
	 */
	public int getManagerId() {
		return this.managerId;
	}
	
	/**
	 * Set the Manager id of this event.
	 * 
	 * @param managerId the new manager id of the event.
	 */
	
	public void setManagerId(int managerId) {
		if (managerId < 0) {
			throw new IllegalArgumentException("El id del propietario tiene que ser positivo");
		} else {
			this.managerId = managerId;
	}}
	
	
	
	
	

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
		if (!(obj instanceof Event))
			return false;
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
