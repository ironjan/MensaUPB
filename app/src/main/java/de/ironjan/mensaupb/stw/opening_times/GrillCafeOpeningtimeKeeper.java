package de.ironjan.mensaupb.stw.opening_times;

import java.util.Calendar;
import java.util.Date;

/**
 * Vorlesungszeit
 * Montag - Donnerstag	09:00 - 22:00 Uhr
 * Freitag	09:00 - 18:00 Uhr
 * <p/>
 * Vorlesungsfreie Zeit	vom 14.02.2015	bis 06.04.2015
 * Montag - Donnerstag	09:00 - 19:30 Uhr
 * Freitag	09:00 - 18:00 Uhr
 * <p/>
 * Vorlesungsfreie Zeit	vom 25.07.2015	bis 18.10.2015
 * Montag - Freitag	09:00 - 18:00 Uhr
 * <p/>
 * Wartungsarbeiten	vom 06.03.2015	bis 06.03.2015
 * Brückentag	vom 15.05.2015	bis 15.05.2015
 * Brückentag	vom 05.06.2015	bis 05.06.2015
 * Betriebsferien Weihnachten und Jahreswechsel	vom 19.12.2015	bis 03.01.2016
 */
public class GrillCafeOpeningtimeKeeper implements RestaurantOpeningTimesKeeper {
    @Override
    public boolean isOpenOn(Date date) {
        Calendar dateInstance = Calendar.getInstance();
        dateInstance.setTime(date);

        int[] closedDayOne = {2015, Calendar.MAY, 15};
        int[] closedDayTwo = {2015, Calendar.JUNE, 5};

        boolean isClosedDayOne = IntervalChecker.isOn(dateInstance, closedDayOne);

        boolean isClosedDayTwo = IntervalChecker.isOn(dateInstance, closedDayTwo);

        int[] winterBreakStart = {2015, Calendar.DECEMBER, 18};
        int[] winterBreakEnd = {2016, Calendar.JANUARY, 5};
        boolean isInWinterBreak = IntervalChecker.isInInterval(dateInstance, winterBreakStart, winterBreakEnd);

        if (isClosedDayOne
                || isClosedDayTwo
                || isInWinterBreak) {
            return false;
        }

        return true;
    }

    @Override
    public Date hasCheapFoodUntil(Date date) {
        return TimeChanger.updateTime(date, 19, 0);
    }
}
