package de.ironjan.mensaupb.stw.opening_times;

import junit.framework.Assert;
import junit.framework.TestCase;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.ironjan.mensaupb.stw.Restaurant;
import de.ironjan.mensaupb.opening_times.data_storage.OpeningTimesKeeper;

/**
 * Ganzjährig
 * Montag 09:00 - 15:30 Uhr
 * Dienstag - Freitag 09:00 - 17:00 Uhr
 * Samstag, Sonntag, Feiertage 11:00 - 17:00 Uhr
 * <p/>
 * Geänderte Öffnungszeiten	vom 29.06.2015 bis 19.07.2015
 * Montag - Freitag 09:00-15:00 Uhr
 * Samstag und Sonntag geschlossen
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(BlockJUnit4ClassRunner.class)
public class HotspotOpeningTimesKeeper_Time_Test extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm");


    @Test
    public void hotspotShouldCloseAt_14() {
        String dateAsKey = sdf.format(new Date());
        Date openUntil = OpeningTimesKeeper.hasCheapFoodUntil(Restaurant.BISTRO_HOTSPOT.key, dateAsKey);
        Assert.assertEquals("14:00", time.format(openUntil));
    }
}
