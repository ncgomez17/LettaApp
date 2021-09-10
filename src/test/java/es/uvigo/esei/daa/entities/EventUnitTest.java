package es.uvigo.esei.daa.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.activation.MimetypesFileTypeMap;

import org.junit.Test;

import es.uvigo.esei.daa.util.ImageAdapter;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class EventUnitTest {
	@Test

	public void testEventGetters() throws IOException, SQLException {

		final int id = 1;
		final String title = "titulo";
		final String description = "Descripcion";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;

		final String image = ImageAdapter.pathToImage("/images/testImage.jpg");

		File file = new File("src/test/resources/images/testImage.jpg");
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		final String extension = fileTypeMap.getContentType(file.getName());
		
		final int managerId = 1;

		final Event event = new Event(id, title, description, category, date, place, maxCapacity, image, extension, managerId);

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));

	}

	@Test(expected = NullPointerException.class)
	public void testEventNullTitle() {
		new Event(1, null, "Descripcion", "deportes", LocalDateTime.of(2030, 1, 1, 1, 1, 1), "Ourense", 5, null, null, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testEventNullDescripcion() {
		new Event(1, "titulo", null, "deportes", LocalDateTime.of(2030, 1, 1, 1, 1, 1), "Ourense", 5, null, null, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testEventNullCategory() {
		new Event(1, "titulo", "Descripcion", null, LocalDateTime.of(2030, 1, 1, 1, 1, 1), "Ourense", 5, null, null, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testEventNullDate() {
		new Event(1, "titulo", "Descripcion", "deportes", null, "Ourense", 5, null, null, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testEventNullPlace() {
		new Event(1, "titulo", "Descripcion", "deportes", LocalDateTime.of(2030, 1, 1, 1, 1, 1), null, 5, null, null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEventEmptyCapacity() {
		new Event(1, "titulo", "Descripcion", "deportes", LocalDateTime.of(2030, 1, 1, 1, 1, 1), "Ourense", 0, null,
				null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEventOverCapacity() {
		new Event(1, "titulo", "Descripcion", "deportes", LocalDateTime.of(2030, 1, 1, 1, 1, 1), "Ourense", 21, null,
				null, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEventNegativeManagerId() {
		new Event(1, "titulo", "Descripcion", "deportes", LocalDateTime.of(2030, 1, 1, 1, 1, 1), "Ourense", 5, null,
				null, -1);
	}

	@Test
	public void testSetTitle() throws IOException {
		final int id = 1;
		final String description = "Descripcion";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = null;
		final int managerId = 1;

		final Event event = new Event(id, "Ttile", description, category, date, place, maxCapacity, image, extension, managerId);

		event.setTitle("Titulo");

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo("Titulo")));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));

	}

	@Test
	public void testSetDecription() throws IOException {
		final int id = 1;
		final String title = "Titulo";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = null;
		final int managerId = 1;

		final Event event = new Event(id, title, "Descriptino", category, date, place, maxCapacity, image, extension, managerId);

		event.setDescription("Descripcion");

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo("Descripcion")));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));
	}

	@Test
	public void testSetCategory() throws IOException {
		final int id = 1;
		final String title = "Titulo";
		final String description = "Descripcion";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = null;
		final int managerId = 1;

		final Event event = new Event(id, title, description, "cine", date, place, maxCapacity, image, extension, managerId);

		event.setCategory("Category");

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo("Category")));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));
	}

	@Test
	public void testSetDate() throws IOException {
		final int id = 1;
		final String title = "Titulo";
		final String description = "Descripcion";
		final String category = "deportes";
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = null;
		final int managerId = 1;

		final Event event = new Event(id, title, description, category, LocalDateTime.of(2030, 1, 1, 1, 1, 1), place,
				maxCapacity, image, extension, managerId);

		event.setDate(LocalDateTime.of(2031, 2, 2, 2, 2, 2));

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(LocalDateTime.of(2031, 2, 2, 2, 2, 2))));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));
	}

	@Test
	public void testSetPlace() throws IOException {
		final int id = 1;
		final String title = "Titulo";
		final String description = "Descripcion";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final int maxCapacity = 5;
		final String image = null;
		final String extension = null;
		final int managerId = 1;

		final Event event = new Event(id, title, description, category, date, "Ourense", maxCapacity, image, extension, managerId);

		event.setPlace("Madrid");

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo("Madrid")));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));
	}

	@Test
	public void testSetMaxCapacity() throws IOException {
		final int id = 1;
		final String title = "Titulo";
		final String description = "Descripcion";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = null;
		final int managerId = 1;

		final Event event = new Event(id, title, description, category, date, place, 10, image, extension, managerId);

		event.setCapacity(maxCapacity);

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));
	}

	@Test

	public void testSetImage() throws IOException, SQLException {

		final int id = 1;
		final String title = "Titulo";
		final String description = "Descripcion";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = null;
		final int managerId = 1;

		final Event event = new Event(id, title, description, category, date, place, maxCapacity, image, extension, managerId);

		event.setImage(ImageAdapter.pathToImage("/images/testImage.jpg"));


		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));

		assertThat(event.getImage(), is(equalTo(ImageAdapter.pathToImage("/images/testImage.jpg"))));

		assertThat(event.getExtension(), is(equalTo(extension)));
		assertThat(event.getManagerId(), is(equalTo(managerId)));
	}

	@Test
	public void testSetExtension() throws IOException {
		final int id = 1;
		final String title = "Titulo";
		final String description = "Descripcion";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = ".jpeg";
		final int managerId = 1;

		final Event event = new Event(id, title, description, category, date, place, maxCapacity, image, extension, managerId);

		event.setExtension(".png");

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(".png")));

		assertThat(event.getManagerId(), is(equalTo(managerId)));
	}
	
	@Test
	public void testSetManagerId() throws IOException {
		final int id = 1;
		final String title = "Titulo";
		final String description = "Descripcion";
		final String category = "deportes";
		final LocalDateTime date = LocalDateTime.of(2030, 1, 1, 1, 1, 1);
		final String place = "Ourense";
		final int maxCapacity = 5;
		final String image = null;
		final String extension = ".jpeg";

		final Event event = new Event(id, title, description, category, date, place, maxCapacity, image, extension, 0);

		event.setManagerId(1);

		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getDate(), is(equalTo(date)));
		assertThat(event.getPlace(), is(equalTo(place)));
		assertThat(event.getCapacity(), is(equalTo(maxCapacity)));
		assertThat(event.getImage(), is(equalTo(image)));
		assertThat(event.getExtension(), is(equalTo(".jpeg")));
		assertThat(event.getManagerId(), is(equalTo(1)));
	}

	@Test
	public void testEqualsObject() {
		final Event eventA = new Event(1, "tituloA", "DescripcionA", "deportes", LocalDateTime.of(2031, 1, 1, 1, 1, 1),
				"OurenseA", 5, null, null, 1);
		final Event eventB = new Event(1, "titulo", "Descripcion", "cine", LocalDateTime.of(2032, 2, 2, 2, 2, 2),
				"OurenseB", 10, null, null, 2);

		assertTrue(eventA.equals(eventB));
	}

	@Test
	public void testEqualsHashcode() {
		EqualsVerifier
				.forClass(Event.class).withIgnoredFields("title", "description", "category", "date", "place",
						"capacity", "image", "extension", "managerId")
				.suppress(Warning.STRICT_INHERITANCE).suppress(Warning.NONFINAL_FIELDS).verify();
	}
}
