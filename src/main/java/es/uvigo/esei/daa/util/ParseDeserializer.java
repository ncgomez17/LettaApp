package es.uvigo.esei.daa.util;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import es.uvigo.esei.daa.dao.DAOException;

public class ParseDeserializer extends StdDeserializer<LocalDateTime> {
	/**
	 * Class to deserialize LocalDateTime
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * Constructs new instance of {@link ParseDeserializer}
	 */
	public ParseDeserializer() {
        super(LocalDateTime.class);
    }
	/**
	 * Returns a LocalDateTime in LocalDateTime format and deserialized
	 * 
	 * @return a LocalDateTime in LocalDateTime format and deserialized
	 * @param p that is a JsonParse used to parse the localdate
	 * @param ctxt is a DeserializationContext.Used to allow passing in configuration settings and reusable temporary objects.
	 * @throws IOException if an error happens.
	 * @throws JsonProcessingException if json gives an error while it is processing

	 */
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(p.getValueAsString()); 
    }

}
