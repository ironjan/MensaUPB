package de.ironjan.mensaupb.stw.opening_times;

import org.androidannotations.annotations.EBean;

import java.util.Calendar;
import java.util.Date;

@EBean(scope = EBean.Scope.Singleton)
class MensaAcademicaOpeningTimesKeeper implements RestaurantOpeningTimesKeeper {

    public boolean isOpenOn(Date date) {
        Calendar dateInstance = Calendar.getInstance();
        dateInstance.setTime(date);

        final int year = dateInstance.get(Calendar.YEAR);
        final int month = dateInstance.get(Calendar.MONTH);
        final int day = dateInstance.get(Calendar.DAY_OF_MONTH);

        if (year == MensaAcademicaClosedDays.CLOSED_DAY_ONE_YEAR
                && month == MensaAcademicaClosedDays.CLOSED_DAY_ONE_MONTH
                && day == MensaAcademicaClosedDays.CLOSED_DAY_ONE_DAY) {
            return false;
        }

        if (year == MensaAcademicaClosedDays.CLOSED_DAY_TWO_YEAR
                && month == MensaAcademicaClosedDays.CLOSED_DAY_TWO_MONTH
                && day == MensaAcademicaClosedDays.CLOSED_DAY_TWO_DAY) {
            return false;
        }

        if (isInChristmasBreak(dateInstance)) {
            return false;
        }

        return true;
    }

    private static boolean isInChristmasBreak(Calendar dateInstance) {
        Calendar intervalStart = Calendar.getInstance();
        intervalStart.clear();
        intervalStart.set(2015, Calendar.DECEMBER, 18);


        Calendar intervalEnd = Calendar.getInstance();
        intervalEnd.clear();
        intervalEnd.set(2016, Calendar.JANUARY, 5);

        if (intervalStart.before(dateInstance)
                && dateInstance.before(intervalEnd)) {
            return true;
        }
        return false;
    }

    private static final class MensaAcademicaClosedDays {
        static final int CLOSED_DAY_ONE_YEAR = 2015;
        static final int CLOSED_DAY_ONE_MONTH = Calendar.MAY;
        static final int CLOSED_DAY_ONE_DAY = 15;

        static final int CLOSED_DAY_TWO_YEAR = 2015;
        static final int CLOSED_DAY_TWO_MONTH = Calendar.JUNE;
        static final int CLOSED_DAY_TWO_DAY = 5;
    }
}
