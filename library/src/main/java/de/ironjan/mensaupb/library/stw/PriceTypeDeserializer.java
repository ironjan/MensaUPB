package de.ironjan.mensaupb.library.stw;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;

/**
 * A class to deserialize NewPriceTypes
 */
public class PriceTypeDeserializer extends JsonDeserializer<PriceType> {

    @Override
    public PriceType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
            jp.nextToken();
        }
        return PriceType.fromString(jp.getValueAsString());
    }
}
