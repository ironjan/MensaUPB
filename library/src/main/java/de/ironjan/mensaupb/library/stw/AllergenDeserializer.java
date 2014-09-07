package de.ironjan.mensaupb.library.stw;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;

/**
 * Created by ljan on 01.09.14.
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
