package de.ironjan.mensaupb.stw.opening_times;

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
        boolean isInWinterBreak = IntervalChecker.isInInterval(calendar, winterBreakStart, winterBreakEnd);

        if (isInWinterBreak)
            return false;

        return true;
    }
}
