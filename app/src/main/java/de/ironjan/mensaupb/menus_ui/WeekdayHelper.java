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
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private final String[] weekDaysAsString = new String[CACHED_DAYS_COUNT];
    private final String[] weekDaysforUi = new String[CACHED_DAYS_COUNT];
    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());
    @SuppressWarnings("WeakerAccess")
    @StringRes
    String localizedDatePattern;
    private volatile boolean mDaysNotInitializedYet = true;

    @Trace
    public synchronized String getNextWeekDayAsKey(int i) {
        if (weekDaysAsString[i] == null) {
            weekDaysAsString[i] = SDF.format(getNextWeekDay(i));
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsKey({}) -> {}", i, weekDaysAsString[i]);
        return weekDaysAsString[i];
    }

    @Trace
    public synchronized String[] getCachedDaysAsStrings() {
        if (mDaysNotInitializedYet) {
            initDays();
        }
        return weekDaysAsString.clone();
    }

    @AfterInject
    @Trace
    void initDays() {
        for (int i = 0; i < CACHED_DAYS_COUNT; i++) {
            getNextWeekDayAsKey(i);
        }
        mDaysNotInitializedYet = false;
    }

    @Trace
    Date getNextWeekDay(int offset) {
        if (BuildConfig.DEBUG) LOGGER.debug("getNextWeekDay({})", offset);
        Calendar cal = Calendar.getInstance();

        final int today = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_WEEK, offset);
        cal.add(Calendar.DAY_OF_WEEK, computeAdditionalOffset(today, offset));

        return cal.getTime();
    }

    private boolean needsSundayOffset(int dayOfWeek) {
        return dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Computes the additional offset to skip the weekend.
     * {@code
     * day, offset  |  0 |  1 |  2 |  3 |  4 |
     * monday       |    |    |    |    |    |
     * tuesday      |    |    |    |    | +2 |
     * wednesday    |    |    |    | +2 | +2 |
     * thursday     |    |    | +2 | +2 | +2 |
     * friday       |    | +2 | +2 | +2 | +2 |
     * saturday     | +2 | +2 | +2 | +2 | +2 |
     * sunday       | +1 | +1 | +1 | +1 | +1 |
     * }
     *
     * @param today the day which is "today"
     * @param offset    offset applied to "today"
     * @return the additional offset
     */
    private int computeAdditionalOffset(int today, int offset){
        switch (today) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return offset>=4 ? 2 : 0;
            case Calendar.WEDNESDAY:
                return offset>=3 ? 2 : 0;
            case Calendar.THURSDAY:
                return offset>=2 ? 2 : 0;
            case Calendar.FRIDAY:
                return offset>=1 ? 2 : 0;
            case Calendar.SATURDAY:
                return 2;
            case Calendar.SUNDAY:
                return 1;

                // today is not one of the known weekdays ¯\_(ツ)_/¯
                default: return 0;
        }
    }
    private boolean needsWeekendOffset(int today, int offset) {
        return (today == Calendar.THURSDAY && offset == 2)
                || (today == Calendar.FRIDAY && offset >= 1)
                || (today == Calendar.SATURDAY);
    }


    public String getNextWeekDayForUI(int i) {
        if (weekDaysforUi[i] == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(localizedDatePattern);
            weekDaysforUi[i] = simpleDateFormat.format(getNextWeekDay(i));
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsKey({}) -> {}", i, weekDaysforUi[i]);
        return weekDaysforUi[i];
    }
}
