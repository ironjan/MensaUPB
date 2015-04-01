package de.ironjan.mensaupb.stw.opening_times;

import java.util.Date;

import de.ironjan.mensaupb.stw.Restaurant;

/**
 * A class to fetch the opening times of the restaurants from. Only considers Mo - Fr
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
public class OpeningTimesKeeper {
    public static boolean isOpenOn(Restaurant restaurant, Date date) {
        switch (restaurant) {
            case MENSA_ACADEMICA:
                return (new MensaAcademicaOpeningTimesKeeper()).isOpenOn(date);
            case MENSA_FORUM:
                return (new MensaForumOpeningTimeKeeper()).isOpenOn(date);
            case BISTRO_HOTSPOT:
                return (new BistroHotspotOpeningTimeKeeper()).isOpenOn(date);
            case GRILL_CAFE:
                return (new GrillCafeOpeningtimeKeeper()).isOpenOn(date);
            case CAFETE:
                return (new CafeteOpeningtimeKeeper()).isOpenOn(date);
            default:
                return true;
        }

    }

}
