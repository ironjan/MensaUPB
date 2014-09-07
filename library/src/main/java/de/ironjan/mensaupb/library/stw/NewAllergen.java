package de.ironjan.mensaupb.library.stw;

/**
 * Created by ljan on 07.09.14.
 */
public enum NewAllergen {
    UNKNOWN(0, ""),
    COLORED(1, Constants.COLORED),
    CONSERVED(2, Constants.CONSERVED),
    ANTIOXIDANTS(3, Constants.ANTIOXIDANTS),
    FLAVOR_ENHANCERS(4, Constants.FLAVOR_ENHANCERS),
    PHOSPHAT(5, Constants.PHOSPHAT),
    SULFURATED(6, Constants.SULFURATED),
    WAXED(7, Constants.WAXED),
    BLACKENED(8, Constants.BLACKENED),
    SWEETENER(9, Constants.SWEETENER),
    PHENYLALANINE(10, Constants.PHENYLALANINE),
    TAURINE(11, Constants.TAURINE),
    NITRATE_SALT(12, Constants.NITRATE_SALT),
    COFFEINE(13, Constants.COFFEINE),
    QUININE(14, Constants.QUININE),
    LACTOPROTEIN(15, Constants.LACTOPROTEIN),
    CRUSTACEAN(16, Constants.CRUSTACEAN),
    EGGS(17, Constants.EGGS),
    FISH(18, Constants.FISH),
    SOYA(19, Constants.SOYA),
    LACTOSE(20, Constants.LACTOSE),
    NUTS(21, Constants.NUTS),
    CELERIAC(22, Constants.CELERIAC),
    MUSTARD(23, Constants.MUSTARD),
    SESAME(24, Constants.SESAME),
    SULFITES(25, Constants.SULFITES),
    MOLLUSKS(26, Constants.MOLLUSKS),
    LUPINE(27, Constants.LUPINE);

    private int ordinal;
    private String type;

    NewAllergen(int ordinal, String type) {
        this.ordinal = ordinal;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public static NewAllergen fromType(String s) {
        if (COLORED.equals(s)) {
            return COLORED;
        } else if (CONSERVED.equals(s)) {
            return CONSERVED;
        } else if (ANTIOXIDANTS.equals(s)) {
            return ANTIOXIDANTS;
        } else if (FLAVOR_ENHANCERS.equals(s)) {
            return FLAVOR_ENHANCERS;
        } else if (PHOSPHAT.equals(s)) {
            return PHOSPHAT;
        } else if (SULFURATED.equals(s)) {
            return SULFURATED;
        } else if (WAXED.equals(s)) {
            return WAXED;
        } else if (BLACKENED.equals(s)) {
            return BLACKENED;
        } else if (SWEETENER.equals(s)) {
            return SWEETENER;
        } else if (BLACKENED.equals(s)) {
            return BLACKENED;
        } else if (PHENYLALANINE.equals(s)) {
            return PHENYLALANINE;
        } else if (TAURINE.equals(s)) {
            return TAURINE;
        } else if (NITRATE_SALT.equals(s)) {
            return NITRATE_SALT;
        } else if (COFFEINE.equals(s)) {
            return COFFEINE;
        } else if (QUININE.equals(s)) {
            return QUININE;
        } else if (LACTOPROTEIN.equals(s)) {
            return LACTOPROTEIN;
        } else if (CRUSTACEAN.equals(s)) {
            return CRUSTACEAN;
        } else if (EGGS.equals(s)) {
            return EGGS;
        } else if (FISH.equals(s)) {
            return FISH;
        } else if (SOYA.equals(s)) {
            return SOYA;
        } else if (LACTOSE.equals(s)) {
            return LACTOSE;
        } else if (NUTS.equals(s)) {
            return NUTS;
        } else if (CELERIAC.equals(s)) {
            return CELERIAC;
        } else if (MUSTARD.equals(s)) {
            return MUSTARD;
        } else if (SESAME.equals(s)) {
            return SESAME;
        } else if (SULFITES.equals(s)) {
            return SULFITES;
        } else if (MOLLUSKS.equals(s)) {
            return MOLLUSKS;
        } else if (LUPINE.equals(s)) {
            return LUPINE;
        } else {
            return UNKNOWN;
        }
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
        public static final String MOLLUSKS = "A14";
        public static final String LUPINE = "A13";
    }
}
