package de.ironjan.mensaupb.opening_times.data_storage;

import java.util.Calendar;
import java.util.Date;

/**
 * Caféte
 * <p/>
 * Vorlesungszeit
 * Montag- Donnerstag	08:00 - 17:00 Uhr
 * Freitag	08:00 - 15:45 Uhr
 * Samstag	10:00 - 14:00 Uhr
 * <p/>
 * Betriebsferien Weihnachten und Jahreswechsel	vom 24.12.2015	bis 03.01.2016
 */
public class CafeteOpeningtimeKeeper implements RestaurantOpeningTimesKeeper {
    @Override
    public boolean isOpenOn(Date date) {
        int[] winterBreakStart = {2015, Calendar.DECEMBER, 24};
        int[] winterBreakEnd = {2016, Calendar.JANUARY, 3};

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        boolean isInWinterBreak = DateTimeUtilities.isInInterval(calendar, winterBreakStart, winterBreakEnd);

        return !isInWinterBreak;

    }


    @Override
    public Date hasCheapFoodUntil(Date date) {
        /*
         * Vorlesungsfreie Zeit	vom 25.07.2015	bis 18.10.2015
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar breakStart = Calendar.getInstance();
        breakStart.set(2015, Calendar.JULY, 25);

        Calendar breakEnd = Calendar.getInstance();
        breakEnd.set(2015, Calendar.JULY, 25);

        if (breakStart.before(calendar) && calendar.before(breakEnd)) {
            return DateTimeUtilities.updateTime(date, 18, 0);
        } else {
            return DateTimeUtilities.updateTime(date, 22, 0);
        }
    }


}
