package de.ironjan.mensaupb.stw.opening_times;

import org.androidannotations.annotations.EBean;

import java.util.Calendar;
import java.util.Date;

@EBean(scope = EBean.Scope.Singleton)
class MensaAcademicaOpeningTimesKeeper implements RestaurantOpeningTimesKeeper {

    public boolean isOpenOn(Date date) {
        Calendar dateInstance = Calendar.getInstance();
        dateInstance.setTime(date);

        int[] closedDayOne = {2015, Calendar.MAY, 15};
        int[] closedDayTwo = {2015, Calendar.JUNE, 5};

        boolean isClosedDayOne = DateTimeUtilities.isOn(dateInstance, closedDayOne);

        boolean isClosedDayTwo = DateTimeUtilities.isOn(dateInstance, closedDayTwo);

        int[] winterBreakStart = {2015, Calendar.DECEMBER, 18};
        int[] winterBreakEnd = {2016, Calendar.JANUARY, 5};
        boolean isInWinterBreak = DateTimeUtilities.isInInterval(dateInstance, winterBreakStart, winterBreakEnd);

        if (isClosedDayOne
                || isClosedDayTwo
                || isInWinterBreak) {
            return false;
        }

        return true;
    }

    @Override
    public Date hasCheapFoodUntil(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        boolean isFriday = Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK);
        if (isFriday) {
            return DateTimeUtilities.updateTime(date, 13, 30);
        } else {
            return DateTimeUtilities.updateTime(date, 14, 0);
        }
    }
}
