package de.ironjan.mensaupb.stw.deserializer;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;

import de.ironjan.mensaupb.stw.*;

/**
 * Deserializer for badges
 */
public class BadgeDeserializer extends JsonDeserializer<Badge> {
    @Override
    public Badge deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
            jp.nextToken();
        }

        return Badge.fromString(jp.getValueAsString());
    }
}
