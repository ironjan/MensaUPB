package de.ironjan.mensaupb.activities;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.stw.Menu;

/**
 * Created by ljan on 4/28/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class WeekdayHelper {
    protected static final int DISPLAYED_DAYS_COUNT = 3;
    private String[] weekDaysAsString = new String[DISPLAYED_DAYS_COUNT];

    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());
    public static final int WEEKEND_OFFSET = 2;
    private static final SimpleDateFormat SDF = Menu.DATABASE_DATE_FORMAT;

    @Trace
    synchronized String getNextWeekDayAsString(int i) {

        if (weekDaysAsString[i] == null) {
            weekDaysAsString[i] = SDF.format(getNextWeekDay(i));
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsString({}) -> {}", i, weekDaysAsString[i]);
        return weekDaysAsString[i];
    }

    @AfterInject
    @Trace
    void initDays() {
        for (int i = 0; i < DISPLAYED_DAYS_COUNT; i++) {
            getNextWeekDayAsString(i);
        }
    }

    @Trace
    Date getNextWeekDay(int offset) {
        if (BuildConfig.DEBUG) LOGGER.debug("getNextWeekDay({})", offset);
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_WEEK, offset);

        if (dayIsWeekend(cal)) {
            cal.add(Calendar.DAY_OF_WEEK, WEEKEND_OFFSET);
        }
        return cal.getTime();
    }

    @Trace
    boolean dayIsWeekend(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }


}
