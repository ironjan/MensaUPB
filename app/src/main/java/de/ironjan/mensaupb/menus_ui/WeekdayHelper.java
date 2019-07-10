package de.ironjan.mensaupb.menus_ui;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.res.StringRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.ironjan.mensaupb.BuildConfig;

/**
 * Helper class to find the next weekdays etc.
 */
@EBean(scope = EBean.Scope.Singleton)
public class WeekdayHelper {
    public static final int DISPLAYED_DAYS_COUNT = 5;
    private static final int CACHED_DAYS_COUNT = DISPLAYED_DAYS_COUNT + 2;
    private static final int WEEKEND_OFFSET = 2;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private final String[] weekDaysAsString = new String[CACHED_DAYS_COUNT];
    private final String[] weekDaysforUi = new String[CACHED_DAYS_COUNT];
    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());
    @SuppressWarnings("WeakerAccess")
    @StringRes
    String localizedDatePattern;

    @Trace
    public synchronized String getNextWeekDayAsKey(int i) {
        if (weekDaysAsString[i] == null) {
            weekDaysAsString[i] = SDF.format(getNextWeekDayByOffset(i));
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsKey({}) -> {}", i, weekDaysAsString[i]);
        return weekDaysAsString[i];
    }

    @AfterInject
    @Trace
    void initDays() {
        for (int i = 0; i < CACHED_DAYS_COUNT; i++) {
            getNextWeekDayAsKey(i);
        }
    }

    @Trace
    Date getNextWeekDayByOffset(int offset) {
        if (BuildConfig.DEBUG) LOGGER.debug("getNextWeekDay({})", offset);
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_WEEK, offset);

        boolean isOnWeekend =
                cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
        if (isOnWeekend) {
            cal.add(Calendar.DAY_OF_WEEK, WEEKEND_OFFSET);
        }

        return cal.getTime();
    }


    public String getNextWeekDayForUI(int i) {
        if (weekDaysforUi[i] == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(localizedDatePattern);
            weekDaysforUi[i] = simpleDateFormat.format(getNextWeekDayByOffset(i));
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsKey({}) -> {}", i, weekDaysforUi[i]);
        return weekDaysforUi[i];
    }
}
