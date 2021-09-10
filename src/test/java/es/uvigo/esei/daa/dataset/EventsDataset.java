package es.uvigo.esei.daa.dataset;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.function.Predicate;
import java.io.IOException;
import java.sql.SQLException;

import es.uvigo.esei.daa.util.ImageAdapter;
import es.uvigo.esei.daa.entities.Event;

public final class EventsDataset {
	private EventsDataset() {}
	
	public static Event[] event() throws SQLException {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return new Event[] {
					
				new Event(3, "Gimnasio", "Ir al gimnasio en comuna", "deportes", LocalDateTime.parse("2022-01-10 19:00:00",formatter), "Gimnasio" , 4, ImageAdapter.pathToImage("/images/deportes.jpg"),"jpg",1),	
				new Event(2, "Charla sobre Aladín","Charla sosegada sobre la última película protagonizada por Will Smith" ,"cine", LocalDateTime.parse("2022-05-24 17:00:00",formatter), "Cafetería Informática, Ourense", 10, ImageAdapter.pathToImage("/images/cine.jpg"),"jpg",1),
				new Event(1, "Paseo por el parque","Reunión para dar un paseo por el parque con las mascotas","ocio", LocalDateTime.parse("2022-08-16 12:00:00",formatter),"Parque de Vigo", 2, ImageAdapter.pathToImage("/images/ocio.jpg"),"jpg",1),
				new Event(4, "Nos comen los teletubbies", "Charlando sobre las últimas tendencias en televisión infantil", "television", LocalDateTime.parse("2022-09-27 15:00:00",formatter), "Centro Comercial, Ourense", 19, ImageAdapter.pathToImage("/images/television.jpg"),"jpg",1),
				new Event(5, "Reunion de lectura", "Comentando los ultimos libros de Brandon Sanderson", "libros", LocalDateTime.parse("2022-09-27 18:00:00",formatter), "Cafetería Noustum", 4, ImageAdapter.pathToImage("/images/libros.jpg"),"jpg",1),
				new Event(6, "Partida de mus", "La partida de todos los domingos", "ocio", LocalDateTime.parse("2022-11-02 15:00:00",formatter), "El graduado", 5, ImageAdapter.pathToImage("/images/ocio.jpg"),"jpg",1)
				
			};
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static Event[] eventPaged() throws SQLException {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return new Event[] {
				new Event(9, "Charla de ingles", "El ingles se enseña mal", "ocio", LocalDateTime.parse("2028-09-01 15:00:00",formatter), "Centro Comercial, Ourense", 20, ImageAdapter.pathToImage("/images/ocio.jpg"),"jpg",1),
				new Event(7, "Club de ajedrez", "Clases de ajedrez y practicas", "deportes", LocalDateTime.parse("2028-09-11 15:00:00",formatter), "Pabellon de estudiantes", 15, ImageAdapter.pathToImage("/images/deportes.jpg"),"jpg",1),
				new Event(8, "Reunion de filosofia", "Analisis del trabajo de Imanuel Kant", "libros", LocalDateTime.parse("2028-09-27 15:00:00",formatter), "Biblioteca", 8, ImageAdapter.pathToImage("/images/libros.jpg"),"jpg",1)

			};
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static Event[] eventWithout(int ... ids) throws SQLException {
		Arrays.sort(ids);
		
		final Predicate<Event> hasValidId = event ->
			binarySearch(ids, event.getId()) < 0;
		
		return stream(event())
			.filter(hasValidId)
		.toArray(Event[]::new);
	}
	
	public static Event event(int id) throws IllegalArgumentException, SQLException {
		return stream(event())
			.filter(event -> event.getId() == id)
			.findAny()
		.orElseThrow(IllegalArgumentException::new);
	}
	
	public static int existentId() {
		return 1;
	}
	
	public static int filledExistentId() {
		return 1;
	}
	
	public static int nonExistentId() {
		return 1234;
	}
	public static Event existentEvent() throws IllegalArgumentException, SQLException {
		return event(existentId());
	}
	
	public static int pastExistentId() {
		return 10;
	}

	public static Event nonExistentEvent() throws IOException, SQLException {

		return new Event(nonExistentId(), "titulo","descripcion","ocio", LocalDateTime.parse("2030-10-01T12:00"),"lugar", 1,  ImageAdapter.pathToImage("./images/ocio.jpg"),"jpg", 1);
	}

	public static String newTitle() {
		return "Pepe se ha perdido";
	}
	
	public static String newDescription() {
		return "Soy un nuevo evento, unete";
	}
	
	public static String newCategory() {
		return "libros";
	}
	
	public static LocalDateTime newDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse("2030-10-11 20:00:00",formatter);
	}
	
	public static String newPlace() {
		return "Ayuntamiento,Vigo";
	}
	
	public static int newCapacity() {
		return 10;
	}
	

	public static String newImage() throws IOException, SQLException {

		return  ImageAdapter.pathToImage("./images/libros.jpg");
	}
	
	public static String newExtension() {
		return "jpg";
	}
	

	public static Event newEvent() throws IOException, SQLException {

		return new Event(event().length + 1, newTitle(),newDescription(),newCategory(),newDate(),newPlace(),newCapacity(),newImage(),newExtension(), 1);
	}
}
