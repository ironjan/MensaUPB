package de.ironjan.mensaupb.stw.rest_api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import de.ironjan.mensaupb.stw.rest_api.NewAllergen;

/**
 * A class to deserialize NewAllergens
 */
public class AllergenDeserializer extends JsonDeserializer<NewAllergen> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllergenDeserializer.class);

    @Override
    public NewAllergen deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
        }
        try {
            String valueAsString = jp.getValueAsString();
            return NewAllergen.fromString(valueAsString);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Could not deserialize Allergen");
            return null;
        }
    }
}
