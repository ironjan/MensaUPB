package de.ironjan.mensaupb.stw;

import de.ironjan.mensaupb.R;

/**
 * Enum to represent restaurants. Each restaurant has a key and a string id - the latter
 * is used to retrieve a potentially localized name.
 */
public enum Restaurant {
    MENSA_ACADEMICA(0, Constants.MENSA_ACADEMICA_PADERBORN, R.string.nameMensaAcademica),
    MENSA_FORUM(1, Constants.MENSA_FORUM_PADERBORN, R.string.nameMensaForum),
    BISTRO_HOTSPOT(2, Constants.BISTRO_HOTSPOT, R.string.nameBistroHotspot),
    GRILL_CAFE(3, Constants.GRILL_CAFE, R.string.nameGrillCafe),
    CAFETE(4, Constants.CAFETE, R.string.nameCafete);

    private static String[] keys = {Constants.MENSA_ACADEMICA_PADERBORN,
            Constants.MENSA_FORUM_PADERBORN,
            Constants.BISTRO_HOTSPOT,
            Constants.GRILL_CAFE,
            Constants.CAFETE};
    private static Integer[] nameStringIds;
    public final String key;
    private final int ordinal;
    private final int nameStringId;

    private Restaurant(int ordinal, String key, int nameStringId) {
        this.ordinal = ordinal;
        this.key = key;
        this.nameStringId = nameStringId;
    }

    /**
     * Retrieves a Restaurant by key
     *
     * @param key the restaurant's key
     * @return the corresponding restaurant
     * @throws IllegalArgumentException if the key is unknown
     */
    public static Restaurant fromKey(String key) {
        switch (key) {
            case Constants.MENSA_ACADEMICA_PADERBORN:
                return MENSA_ACADEMICA;
            case Constants.MENSA_FORUM_PADERBORN:
                return MENSA_FORUM;
            case Constants.BISTRO_HOTSPOT:
                return BISTRO_HOTSPOT;
            case Constants.GRILL_CAFE:
                return GRILL_CAFE;
            case Constants.CAFETE:
                return CAFETE;
        }
        throw new IllegalArgumentException("Unknown Restaurant key.");
    }

    public static String[] getKeys() {
        return keys;
    }

    public static Integer[] getNameStringIds() {
        // construction it once at runtime to make sure order is correct
        if (nameStringIds == null) {
            nameStringIds = new Integer[keys.length];
            for (int i = 0; i < keys.length; i++) {
                nameStringIds[i] = getNameFromKey(keys[i]);
            }
        }
        return nameStringIds;
    }

    private static int getNameFromKey(String key) {
        return fromKey(key).getNameStringId();
    }

    public int getNameStringId() {
        return nameStringId;
    }

    private static class Constants {
        public static final String MENSA_ACADEMICA_PADERBORN = "mensa-academica-paderborn";
        public static final String MENSA_FORUM_PADERBORN = "mensa-forum-paderborn";
        public static final String BISTRO_HOTSPOT = "bistro-hotspot";
        public static final String GRILL_CAFE = "grill-cafe";
        public static final String CAFETE = "cafete";
    }
}
