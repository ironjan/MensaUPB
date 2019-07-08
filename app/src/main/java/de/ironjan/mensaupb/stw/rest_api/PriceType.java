package de.ironjan.mensaupb.stw.rest_api;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An enum to list all possible price types
 */
public enum PriceType {
    FIXED(0, Constants.FIXED_STRING), WEIGHT(1, Constants.WEIGHT_STRING);

    private final int id;
    private final String name;

    PriceType(final int id, final String name) {
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

    public static class Constants {
        public static final String FIXED_STRING = "fixed";
        public static final String WEIGHT_STRING = "weighted";
    }
}
