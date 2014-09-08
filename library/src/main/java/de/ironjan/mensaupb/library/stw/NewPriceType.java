package de.ironjan.mensaupb.library.stw;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

/**
 * An enum to list all possible price types
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = PriceTypeDeserializer.class)
public enum NewPriceType {
    FIXED(0, Constants.FIXED_STRING), WEIGHT(1, Constants.WEIGHT_STRING);

    private final int id;
    private final String name;

    private NewPriceType(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static NewPriceType fromString(String s) {
        if (Constants.FIXED_STRING.equals(s)) {
            return FIXED;
        }
        if (Constants.WEIGHT_STRING.equals(s)) {
            return WEIGHT;
        }
        return null;
    }

    private static class Constants {
        public static final String FIXED_STRING = "fixed";
        public static final String WEIGHT_STRING = "weighted";
    }
}
