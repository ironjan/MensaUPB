package de.ironjan.mensaupb.stw.rest_api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import de.ironjan.mensaupb.stw.rest_api.Badge;

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
