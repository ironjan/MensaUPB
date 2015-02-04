package de.ironjan.mensaupb.stw;

import android.text.*;

import com.j256.ormlite.logger.*;

/**
 * Converter between an array of Badges and Strings.
 */
public class BadgesStringConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BadgesStringConverter.class);

    public static Badge[] convert(String badgesAsString) {
        if (badgesAsString == null || TextUtils.isEmpty(badgesAsString)) {
            return null;
        }
        String[] split = badgesAsString.split(";");
        Badge[] tmpBadges = new Badge[split.length];
        for (int i = 0; i < split.length; i++) {
            String string = split[i];
            Badge badge = Badge.fromString(string);
            tmpBadges[i] = badge;
            LOGGER.debug("{} <-- {}", badge, string);
        }


        return tmpBadges.clone();
    }

    public static String convert(Badge[] badges) {
        if (badges == null || badges.length < 1) {
            return null;
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
