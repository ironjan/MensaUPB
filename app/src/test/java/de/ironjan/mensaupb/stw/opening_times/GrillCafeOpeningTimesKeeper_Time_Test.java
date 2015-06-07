package de.ironjan.mensaupb.stw.opening_times;

import junit.framework.Assert;
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
 * Vorlesungszeit
 * Montag - Donnerstag	09:00 - 22:00 Uhr
 * Freitag	09:00 - 18:00 Uhr
 * <p/>
 * <p/>
 * Vorlesungsfreie Zeit	vom 25.07.2015	bis 18.10.2015
 * Montag - Freitag	09:00 - 18:00 Uhr
 * <p/>
 * Brückentag	vom 15.05.2015	bis 15.05.2015
 * Brückentag	vom 05.06.2015	bis 05.06.2015
 * Betriebsferien Weihnachten und Jahreswechsel	vom 19.12.2015	bis 03.01.2016
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
@Config(emulateSdk = 18)
@RunWith(BlockJUnit4ClassRunner.class)
public class GrillCafeOpeningTimesKeeper_Time_Test extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void happyDinnerShouldEndAt_1900() {
        String dateAsKey = sdf.format(new Date());
        Date openUntil = OpeningTimesKeeper.hasCheapFoodUntil(Restaurant.GRILL_CAFE.key, dateAsKey);
        Assert.assertEquals("19:00", time.format(openUntil));
    }

}
