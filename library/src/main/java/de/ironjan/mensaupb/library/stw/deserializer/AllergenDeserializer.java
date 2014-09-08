package de.ironjan.mensaupb.library.stw.deserializer;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;

import de.ironjan.mensaupb.library.stw.*;

/**
 * A class to deserialize NewAllergens
 */
public class AllergenDeserializer extends JsonDeserializer<NewAllergen> {

    @Override
    public NewAllergen deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
            jp.nextToken();
        }
        try {
            return NewAllergen.fromType(jp.getValueAsString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
