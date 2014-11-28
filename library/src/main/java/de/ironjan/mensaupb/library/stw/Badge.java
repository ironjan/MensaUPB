package de.ironjan.mensaupb.library.stw;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;

import de.ironjan.mensaupb.library.*;
import de.ironjan.mensaupb.library.stw.deserializer.*;

/**
 * Enum for the badges used
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = BadgeDeserializer.class)
public enum Badge {
    LOW_CALORIE(1, Constants.LOW_CALORIE, R.string.lowCalorie),
    FAT_FREE(2, Constants.FAT_FREE, R.string.fatFree),
    VEGETARIAN(3, Constants.VEGETARIAN, R.string.vegetarian),
    VEGAN(4, Constants.VEGAN, R.string.vegan),
    NO_LACTOSE(5, Constants.NO_LACTOSE, R.string.noLactose),
    NO_GLUTEN(6, Constants.NO_GLUTEN, R.string.noGluten),
    EMPTY(7, Constants.EMPTY, R.string.empty);

    private final int ordinal;
    private final String type;
    private final int stringId;

    Badge(int ordinal, String type, int stringId) {
        this.ordinal = ordinal;
        this.type = type;
        this.stringId = stringId;
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
            return EMPTY;
    }

    public String getType() {
        return type;
    }

    public int getStringId() {
        return stringId;
    }

    private static class Constants {
        public static final String LOW_CALORIE = "low-calorie";
        public static final String FAT_FREE = "nonfat";
        public static final String VEGETARIAN = "vegetarian";
        public static final String VEGAN = "vegan";
        public static final String NO_LACTOSE = "lactose-free";
        public static final String NO_GLUTEN = "gluten-free";
        public static final String EMPTY = "";
    }
}
