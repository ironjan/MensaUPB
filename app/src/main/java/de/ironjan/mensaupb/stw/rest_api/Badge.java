package de.ironjan.mensaupb.stw.rest_api;

import de.ironjan.mensaupb.R;

/**
 * Enum for the badges used
 */
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
        switch (s) {
            case Constants.LOW_CALORIE:
                return LOW_CALORIE;
            case Constants.FAT_FREE:
                return FAT_FREE;
            case Constants.VEGETARIAN:
                return VEGETARIAN;
            case Constants.VEGAN:
                return VEGAN;
            case Constants.NO_LACTOSE:
                return NO_LACTOSE;
            case Constants.NO_GLUTEN:
                return NO_GLUTEN;
            default:
                return EMPTY;
        }
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
