package de.ironjan.mensaupb.adapters;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import java.text.*;
import java.util.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.library.stw.*;

/**
 * Created by ljan on 4/28/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class WeekdayHelper {
    public static final int WEEKEND_OFFSET = 2;
    public static final int DISPLAYED_DAYS_COUNT = 3;
    private static final int CACHED_DAYS_COUNT = DISPLAYED_DAYS_COUNT + 2;
    private String[] weekDaysAsString = new String[CACHED_DAYS_COUNT];
    private String[] weekDaysforUi = new String[DISPLAYED_DAYS_COUNT];
    private static final SimpleDateFormat SDF = new SimpleDateFormat(RawMenu.DATE_FORMAT);
    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());
    @StringRes(R.string.today)
    String mToday;
    @StringRes(R.string.tomorrow)
    String mTomorrow;
    @StringRes(R.string.localizedDatePattern)
    String localizedDatePattern;
    private volatile boolean mDaysNotInitializedYet = true;

    /**
     * <p>Checks if a date is today. <a href="http://www.java2s.com/Code/Java/Data-Type/Checksifacalendardateistoday.htm">Source</a></p>
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isToday(Date date) {
        Calendar todayInstance = Calendar.getInstance();
        Calendar dateInstance = Calendar.getInstance();
        dateInstance.setTime(date);
        return isSameDay(dateInstance, todayInstance);
    }

    /**
     * <p>Checks if two calendars represent the same day ignoring time. <a href="http://www.java2s.com/Code/Java/Data-Type/Checksifacalendardateistoday.htm">Source</a></p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    @Trace
    public synchronized String getNextWeekDayAsKey(int i) {
        if (weekDaysAsString[i] == null) {
            weekDaysAsString[i] = SDF.format(getNextWeekDay(i));
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsKey({}) -> {}", i, weekDaysAsString[i]);
        return weekDaysAsString[i];
    }

    public String[] getDisplayedDays() {
        if (mDaysNotInitializedYet) {
            initDays();
        }
        return weekDaysforUi.clone();
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
        for (int i = 0; i < DISPLAYED_DAYS_COUNT; i++) {
            getNextWeekDayForUI(i);
        }
        mDaysNotInitializedYet = false;
    }

    @Trace
    Date getNextWeekDay(int offset) {
        if (BuildConfig.DEBUG) LOGGER.debug("getNextWeekDay({})", offset);
        Calendar cal = Calendar.getInstance();

        final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (needsWeekendOffset(offset, dayOfWeek)) {
            cal.add(Calendar.DAY_OF_WEEK, WEEKEND_OFFSET);
        } else if (needsSundayOffset(dayOfWeek)) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        cal.add(Calendar.DAY_OF_WEEK, offset);

        return cal.getTime();
    }

    private boolean needsSundayOffset(int dayOfWeek) {
        return dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Checks for weekend offset according to the following table; +2 means that day+offset is
     * on weekend and needs offset:
     * {@code
     * day, offset  |  0 |  1 |  2 |
     * thursday     |    |    | +2 |
     * friday       |    | +2 | +2 |
     * saturday     | +2 | +2 | +2 |
     * }
     *
     * @param offset    offset applied to dayOfWeek
     * @param dayOfWeek the day which is "today"
     * @return true, if weekend offset has to be added
     */
    private boolean needsWeekendOffset(int offset, int dayOfWeek) {
        return (dayOfWeek == Calendar.THURSDAY && offset == 2)
                || (dayOfWeek == Calendar.FRIDAY && offset >= 1)
                || (dayOfWeek == Calendar.SATURDAY);
    }

    public String getNextWeekDayForUI(int i) {
        if (weekDaysforUi[i] == null) {
            Date nextWeekDay = getNextWeekDay(i);
            weekDaysforUi[i] = formatForUi(nextWeekDay);
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsKey({}) -> {}", i, weekDaysforUi[i]);
        return weekDaysforUi[i];
    }

    private String formatForUi(Date nextWeekDay) {
        if (isToday(nextWeekDay)) {
            return mToday;
        } else if (isTomorrow(nextWeekDay)) {
            return mTomorrow;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(localizedDatePattern);
            return simpleDateFormat.format(nextWeekDay);
        }
    }

    private boolean isTomorrow(Date date) {
        Calendar tomorrowInstance = Calendar.getInstance();
        tomorrowInstance.roll(Calendar.DAY_OF_MONTH, true);
        Calendar dateInstace = Calendar.getInstance();
        dateInstace.setTime(date);
        return isSameDay(dateInstace, tomorrowInstance);
    }

}
