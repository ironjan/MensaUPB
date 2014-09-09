package de.ironjan.mensaupb.library.stw.deserializer;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import org.slf4j.*;

import java.io.*;

import de.ironjan.mensaupb.library.stw.*;

/**
 * A class to deserialize NewAllergens
 */
public class AllergenDeserializer extends JsonDeserializer<NewAllergen> {
    Logger LOGGER = LoggerFactory.getLogger(AllergenDeserializer.class);
    @Override
    public NewAllergen deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
        }
        try {
            String valueAsString = jp.getValueAsString();
            NewAllergen newAllergen = NewAllergen.fromType(valueAsString);
            JsonToken jsonToken = jp.getCurrentToken();
            LOGGER.info("", jsonToken);
            return newAllergen;
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Could not deserialize Allergen");
            return null;
        }
    }
}
