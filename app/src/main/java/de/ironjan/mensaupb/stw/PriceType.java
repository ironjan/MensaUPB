package de.ironjan.mensaupb.stw;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

import de.ironjan.mensaupb.stw.deserializer.*;

/**
 * An enum to list all possible price types
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = PriceTypeDeserializer.class)
public enum PriceType {
    FIXED(0, Constants.FIXED_STRING), WEIGHT(1, Constants.WEIGHT_STRING);

    private final int id;
    private final String name;

    private PriceType(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static PriceType fromString(String s) {
        if (Constants.FIXED_STRING.equals(s)) {
            return FIXED;
        }
        if (Constants.WEIGHT_STRING.equals(s)) {
            return WEIGHT;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    private static class Constants {
        public static final String FIXED_STRING = "fixed";
        public static final String WEIGHT_STRING = "weighted";
    }
}
