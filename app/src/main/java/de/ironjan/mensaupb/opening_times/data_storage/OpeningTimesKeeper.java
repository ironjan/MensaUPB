package de.ironjan.mensaupb.opening_times.data_storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.ironjan.mensaupb.stw.Restaurant;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * A class to fetch the opening times of the restaurants from. Only considers Mo - Fr
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
public class OpeningTimesKeeper {
    private static final SimpleDateFormat SDF = new SimpleDateFormat(StwMenu.DATE_FORMAT);

    /**
     * @param dateAsKey     a date to check for (yyyy-MM-dd)
     * @param restaurantKey the restaurant to check for
     * @return true, if the restaurant is open on that date
     */
    public static boolean isOpenOn(String restaurantKey, String dateAsKey) {
        Restaurant restaurant = Restaurant.fromKey(restaurantKey);
        Date date = parseDate(dateAsKey);
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


    /**
     * @param dateAsKey     a date to check for (yyyy-MM-dd)
     * @param restaurantKey the restaurant to check for
     * @return The date with the closing time for cheap food (Mensa, Happy Dinner) or the real
     * closing time (Cafete) on this day
     */
    public static Date hasCheapFoodUntil(String restaurantKey, String dateAsKey) {
        Restaurant restaurant = Restaurant.fromKey(restaurantKey);
        Date date = parseDate(dateAsKey);
        switch (restaurant) {
            case MENSA_ACADEMICA:
                return (new MensaAcademicaOpeningTimesKeeper()).hasCheapFoodUntil(date);
            case MENSA_FORUM:
                return (new MensaForumOpeningTimeKeeper()).hasCheapFoodUntil(date);
            case BISTRO_HOTSPOT:
                return (new BistroHotspotOpeningTimeKeeper()).hasCheapFoodUntil(date);
            case GRILL_CAFE:
                return (new GrillCafeOpeningtimeKeeper()).hasCheapFoodUntil(date);
            case CAFETE:
                return (new CafeteOpeningtimeKeeper()).hasCheapFoodUntil(date);
            default:
                return null;
        }
    }

    private static Date parseDate(String dateAsKey) {
        Date date = null;
        try {
            date = SDF.parse(dateAsKey);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Date is formatted incorrectly");
        }
        return date;
    }

}
