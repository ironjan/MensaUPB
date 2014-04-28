package de.ironjan.mensaupb.stw;

import org.androidannotations.annotations.*;

import java.text.*;
import java.util.*;

@EBean(scope = EBean.Scope.Singleton)
public class OpeningTimesLookup {
    @Bean
    Restaurants mRestaurants;
    private static final SimpleDateFormat SDF = Menu.DATABASE_DATE_FORMAT;

    public boolean isPotentiallyClosed(String argDate, String argLocation) {
        try {
            Date date = SDF.parse(argDate);
            return !isOpen(date, argLocation);
        } catch (ParseException e) {
            // should not happen
            // but if it happens, we show standard empty note
            return false;
        }
    }

    private boolean isOpen(Date date, String argLocation) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        final boolean isFriday = Calendar.FRIDAY == dayOfWeek;
        final boolean isAbendmensa = mRestaurants.getAbendmensa().equals(argLocation);

        calendar.set(2014,07,17,0,0,0);
        final Date specialTimeEnd = calendar.getTime();

        final boolean isSpecialTime = date.before(specialTimeEnd);
        return !(isFriday && isAbendmensa && isSpecialTime);
    }
}
