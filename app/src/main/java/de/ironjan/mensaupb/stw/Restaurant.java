package de.ironjan.mensaupb.stw;

import de.ironjan.mensaupb.R;

/**
 * Created by ljan on 31.03.15.
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
    private final int ordinal;
    private final String key;
    private final int nameStringId;

    private Restaurant(int ordinal, String key, int nameStringId) {
        this.ordinal = ordinal;
        this.key = key;
        this.nameStringId = nameStringId;
    }

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

    public String getKey() {
        return key;
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
