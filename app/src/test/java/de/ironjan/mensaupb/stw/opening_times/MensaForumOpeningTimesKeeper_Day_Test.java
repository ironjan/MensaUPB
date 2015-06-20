package de.ironjan.mensaupb.stw.opening_times;

import junit.framework.TestCase;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.ironjan.mensaupb.stw.Restaurant;
import de.ironjan.mensaupb.opening_times.data_storage.OpeningTimesKeeper;

/**
 * Ganzjährig
 * Montag - Donnerstag 11:15 - 14:00 Uhr
 * Freitag 11:15 - 13:30 Uhr
 * <p/>
 * Betriebsferien vom 21.02.2015 bis 22.03.2015
 * Betriebsferien vom 18.07.2015 bis 11.10.2015
 * Betriebsferien Weihnachten und Jahreswechsel	vom 19.12.2015 bis 03.01.2016
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(BlockJUnit4ClassRunner.class)
public class MensaForumOpeningTimesKeeper_Day_Test extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void mensaForumShouldBeOpenFrom_2015_03_23_To_2015_07_17() {
        calendar.set(2015, Calendar.MARCH, 23);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.JULY, 18);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            boolean mensaForumIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_FORUM.key, sdf.format(date));

            String message = "Mensa Forum is closed on " + sdf.format(date);
            assertTrue(message, mensaForumIsOpenOn);


            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void mensaForumShouldBeClosedFrom_2015_07_18_To_2015_10_11() {
        calendar.set(2015, Calendar.JULY, 18);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.OCTOBER, 11);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();

            boolean mensaForumIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_FORUM.key, sdf.format(date));

            String message = "Mensa Forum is opened on " + sdf.format(date);
            assertFalse(message, mensaForumIsOpenOn);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void mensaForumShouldBeOpenFrom_2015_10_12_To_2015_12_18() {
        calendar.set(2015, Calendar.OCTOBER, 12);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.DECEMBER, 19);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            boolean mensaForumIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_FORUM.key, sdf.format(date));

            String message = "Mensa Forum is closed on " + sdf.format(date);
            assertTrue(message, mensaForumIsOpenOn);


            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void mensaForumShouldBeClosedFrom_2015_12_19_To_2016_01_03() {
        calendar.set(2015, Calendar.DECEMBER, 19);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2016, Calendar.JANUARY, 3);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();

            boolean mensaForumIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_FORUM.key, sdf.format(date));

            String message = "Mensa Forum is opened on " + sdf.format(date);
            assertFalse(message, mensaForumIsOpenOn);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
