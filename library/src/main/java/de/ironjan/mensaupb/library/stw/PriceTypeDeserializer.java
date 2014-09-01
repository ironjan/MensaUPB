package de.ironjan.mensaupb.library.stw;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.j256.ormlite.stmt.query.*;

import java.io.*;

/**
 * Created by ljan on 01.09.14.
 */
public class PriceTypeDeserializer extends JsonDeserializer<NewPriceType> {

    @Override
    public NewPriceType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
            jp.nextToken();
        }
        if (RawMenu.PRICETYPE.equals(jp.getCurrentName())) {
            return NewPriceType.fromString(jp.getValueAsString());
        }

        return null;
    }
}
