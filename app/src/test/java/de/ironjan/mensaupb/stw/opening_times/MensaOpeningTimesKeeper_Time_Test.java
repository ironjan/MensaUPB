package de.ironjan.mensaupb.stw.opening_times;

import junit.framework.TestCase;

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
 * Mensa Academica and Mensa Forum both have
 * Ganzjährig
 * Montag - Donnerstag	11:15 - 14:00 Uhr
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
@Config(emulateSdk = 18)
@RunWith(BlockJUnit4ClassRunner.class)
public class MensaOpeningTimesKeeper_Time_Test extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void mensaAcademicaClosesAt_1400_Monday_to_Thursday_And_1330_OnFriday() {
        checkRestaurant(Restaurant.MENSA_ACADEMICA.key);
    }

    @Test
    public void mensaForumClosesAt_1400_Monday_to_Thursday_And_1330_OnFriday() {
        checkRestaurant(Restaurant.MENSA_FORUM.key);
    }

    private void checkRestaurant(String restaurantKey) {
        calendar.set(2015, Calendar.JANUARY, 1);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.DECEMBER, 31);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            String dateAsKey = sdf.format(date);
            Date openUntil = OpeningTimesKeeper.hasCheapFoodUntil(restaurantKey, dateAsKey);

            boolean isFriday = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY);

            if (isFriday) {
                assertEquals("Doesn't close at 13:30 on " + dateAsKey, "13:30", time.format(openUntil));
            } else {
                assertEquals("Doesn't close at 14:00 on  " + dateAsKey, "14:00", time.format(openUntil));
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }


}