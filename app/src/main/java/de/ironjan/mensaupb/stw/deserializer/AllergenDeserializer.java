package de.ironjan.mensaupb.stw.deserializer;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import org.slf4j.*;

import java.io.*;

import de.ironjan.mensaupb.stw.*;

/**
 * A class to deserialize NewAllergens
 */
public class AllergenDeserializer extends JsonDeserializer<NewAllergen> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllergenDeserializer.class);

    @Override
    public NewAllergen deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
        }
        try {
            String valueAsString = jp.getValueAsString();
            return NewAllergen.fromType(valueAsString);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Could not deserialize Allergen");
            return null;
        }
    }
}
