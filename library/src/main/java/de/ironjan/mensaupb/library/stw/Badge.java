package de.ironjan.mensaupb.library.stw;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

import de.ironjan.mensaupb.library.stw.deserializer.*;

/**
 * Enum for the badges used
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = BadgeDeserializer.class)
public enum Badge {
    LOW_CALORIE(1, Constants.LOW_CALORIE),
    FAT_FREE(2, Constants.FAT_FREE),
    VEGETARIAN(3, Constants.VEGETARIAN),
    VEGAN(4, Constants.VEGAN),
    NO_LACTOSE(5, Constants.NO_LACTOSE),
    NO_GLUTEN(6, Constants.NO_GLUTEN);

    private final int ordinal;
    private final String type;

    Badge(int ordinal, String type) {
        this.ordinal = ordinal;
        this.type = type;
    }

    public static Badge fromString(String s) {
        if (Constants.LOW_CALORIE.equals(s)) {
            return LOW_CALORIE;
        } else if (Constants.FAT_FREE.equals(s)) {
            return FAT_FREE;
        } else if (Constants.VEGETARIAN.equals(s)) {
            return VEGETARIAN;
        } else if (Constants.VEGAN.equals(s)) {
            return VEGAN;
        } else if (Constants.NO_LACTOSE.equals(s)) {
            return NO_LACTOSE;
        } else if (Constants.NO_GLUTEN.equals(s)) {
            return NO_GLUTEN;
        } else

            return null;
    }

    public String getType() {
        return type;
    }

    private static class Constants {
        public static final String LOW_CALORIE = "low-calorie";
        public static final String FAT_FREE = "nonfat";
        public static final String VEGETARIAN = "vegetarian";
        public static final String VEGAN = "vegan";
        public static final String NO_LACTOSE = "lactose-free";
        public static final String NO_GLUTEN = "gluten-free";
    }
}
