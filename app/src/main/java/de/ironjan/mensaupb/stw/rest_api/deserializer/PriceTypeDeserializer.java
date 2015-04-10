package de.ironjan.mensaupb.stw.rest_api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import de.ironjan.mensaupb.stw.rest_api.PriceType;

/**
 * A class to deserialize NewPriceTypes
 */
public class PriceTypeDeserializer extends JsonDeserializer<PriceType> {

    @Override
    public PriceType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
            jp.nextToken();
        }
        return PriceType.fromString(jp.getValueAsString());
    }
}
