package de.ironjan.mensaupb.library;

import de.ironjan.mensaupb.library.stw.*;

/**
 * Created by ljan on 28.11.14.
 */
public class BadgesStringConverter {
    public static Badge[] convert(String badgesAsString) {
        String[] split = badgesAsString.split(";");
        Badge[] tmpBadges = new Badge[split.length];
        for (int i = 0; i < split.length; i++) {
            tmpBadges[i] = Badge.fromString(split[i]);
        }
        return tmpBadges.clone();
    }

    public static String convert(Badge[] badges) {
        if (badges == null || badges.length < 1) {
            return "";
        }

        StringBuilder builder = new StringBuilder(badges[0].getType());

        for (int i = 1; i < badges.length; i++) {
            Badge badge = badges[i];
            builder.append(";")
                    .append(badge.getType());
        }

        return builder.toString();
    }
}
