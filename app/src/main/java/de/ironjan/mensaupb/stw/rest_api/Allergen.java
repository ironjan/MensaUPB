package de.ironjan.mensaupb.stw.rest_api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.stw.rest_api.deserializer.AllergenDeserializer;

/**
 * Class that represents Allergens and Additionals.
 */
@SuppressWarnings("MagicNumber")
@JsonDeserialize(using = AllergenDeserializer.class)
public enum Allergen implements Comparable<Allergen> {
    UNKNOWN(0, "", R.string.empty),
    COLORED(1, Constants.COLORED, R.string.colored),
    CONSERVED(2, Constants.CONSERVED, R.string.conserved),
    ANTIOXIDANTS(3, Constants.ANTIOXIDANTS, R.string.antioxidants),
    FLAVOR_ENHANCERS(4, Constants.FLAVOR_ENHANCERS, R.string.tasteEnhancer),
    PHOSPHAT(5, Constants.PHOSPHAT, R.string.phosphate),
    SULFURATED(6, Constants.SULFURATED, R.string.sulfured),
    WAXED(7, Constants.WAXED, R.string.waxed),
    BLACKENED(8, Constants.BLACKENED, R.string.blackened),
    SWEETENER(9, Constants.SWEETENER, R.string.sweetener),
    PHENYLALANINE(10, Constants.PHENYLALANINE, R.string.phenylalanine),
    TAURINE(11, Constants.TAURINE, R.string.taurine),
    NITRATE_SALT(12, Constants.NITRATE_SALT, R.string.nitrate_salt),
    COFFEINE(13, Constants.COFFEINE, R.string.caffeine),
    QUININE(14, Constants.QUININE, R.string.quinine),
    LACTOPROTEIN(15, Constants.LACTOPROTEIN, R.string.lacto_protein),
    CRUSTACEAN(16, Constants.CRUSTACEAN, R.string.crustacean),
    EGGS(17, Constants.EGGS, R.string.eggs),
    FISH(18, Constants.FISH, R.string.fish),
    SOYA(19, Constants.SOYA, R.string.soy),
    LACTOSE(20, Constants.LACTOSE, R.string.milk),
    NUTS(21, Constants.NUTS, R.string.nuts),
    CELERIAC(22, Constants.CELERIAC, R.string.celeriac),
    MUSTARD(23, Constants.MUSTARD, R.string.mustard),
    SESAME(24, Constants.SESAME, R.string.sesame),
    SULFITES(25, Constants.SULFITES, R.string.sulfates),
    MOLLUSKS(26, Constants.MOLLUSKS, R.string.mollusks),
    LUPINE(27, Constants.LUPINE, R.string.lupines),
    GLUTEN(28, Constants.GLUTEN, R.string.gluten),
    PEANUTS(29, Constants.PEANUTS, R.string.peanuts);

    private final int ordinal;
    private final String type;
    private final int stringResourceID;

    Allergen(int ordinal, String type, int stringResourceID) {
        this.ordinal = ordinal;
        this.type = type;
        this.stringResourceID = stringResourceID;
    }

    /**
     * Converts a string to a Allergen
     *
     * @param string the string
     * @return the Allergen which is textually represented as String, if known. Else "Unknown".
     */
    public static Allergen fromString(String string) {
        switch (string) {
            case Constants.COLORED:
                return COLORED;
            case Constants.CONSERVED:
                return CONSERVED;
            case Constants.ANTIOXIDANTS:
                return ANTIOXIDANTS;
            case Constants.FLAVOR_ENHANCERS:
                return FLAVOR_ENHANCERS;
            case Constants.PHOSPHAT:
                return PHOSPHAT;
            case Constants.SULFURATED:
                return SULFURATED;
            case Constants.WAXED:
                return WAXED;
            case Constants.BLACKENED:
                return BLACKENED;
            case Constants.SWEETENER:
                return SWEETENER;
            case Constants.PHENYLALANINE:
                return PHENYLALANINE;
            case Constants.TAURINE:
                return TAURINE;
            case Constants.NITRATE_SALT:
                return NITRATE_SALT;
            case Constants.COFFEINE:
                return COFFEINE;
            case Constants.QUININE:
                return QUININE;
            case Constants.LACTOPROTEIN:
                return LACTOPROTEIN;
            case Constants.CRUSTACEAN:
                return CRUSTACEAN;
            case Constants.EGGS:
                return EGGS;
            case Constants.FISH:
                return FISH;
            case Constants.SOYA:
                return SOYA;
            case Constants.LACTOSE:
                return LACTOSE;
            case Constants.NUTS:
                return NUTS;
            case Constants.CELERIAC:
                return CELERIAC;
            case Constants.MUSTARD:
                return MUSTARD;
            case Constants.SESAME:
                return SESAME;
            case Constants.SULFITES:
                return SULFITES;
            case Constants.MOLLUSKS:
                return MOLLUSKS;
            case Constants.LUPINE:
                return LUPINE;
            case Constants.GLUTEN:
                return GLUTEN;
            case Constants.PEANUTS:
                return PEANUTS;
        }

        LoggerFactory.getLogger(Allergen.class).debug("Requested unknown value: " + string);
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return type;
    }

    public String getType() {
        return type;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public int getStringId() {
        return stringResourceID;
    }


    private static class Constants {
        public static final String COLORED = "1";
        public static final String CONSERVED = "2";
        public static final String ANTIOXIDANTS = "3";
        public static final String FLAVOR_ENHANCERS = "4";
        public static final String PHOSPHAT = "5";
        public static final String SULFURATED = "6";
        public static final String WAXED = "7";
        public static final String BLACKENED = "8";
        public static final String SWEETENER = "9";
        public static final String PHENYLALANINE = "10";
        public static final String TAURINE = "11";
        public static final String NITRATE_SALT = "12";
        public static final String COFFEINE = "13";
        public static final String QUININE = "14";
        public static final String LACTOPROTEIN = "15";
        public static final String CRUSTACEAN = "A2";
        public static final String EGGS = "A3";
        public static final String FISH = "A4";
        public static final String SOYA = "A6";
        public static final String LACTOSE = "A7";
        public static final String NUTS = "A8";
        public static final String CELERIAC = "A9";
        public static final String MUSTARD = "A10";
        public static final String SESAME = "A11";
        public static final String SULFITES = "A12";
        public static final String LUPINE = "A13";
        public static final String MOLLUSKS = "A14";
        public static final String GLUTEN = "A1";
        public static final String PEANUTS = "A5";
    }


}