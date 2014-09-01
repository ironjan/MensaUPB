package de.ironjan.mensaupb.adapters;

import org.androidannotations.annotations.*;
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
     * @param offset offset applied to dayOfWeek
     * @param dayOfWeek the day which is "today"
     * @return true, if weekend offset has to be added
     */
    private boolean needsWeekendOffset(int offset, int dayOfWeek) {
        return (dayOfWeek == Calendar.THURSDAY && offset == 2)
                || (dayOfWeek == Calendar.FRIDAY && offset >= 1)
                || (dayOfWeek == Calendar.SATURDAY);
    }


}
