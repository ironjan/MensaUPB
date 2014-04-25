package de.ironjan.mensaupb.stw;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;

/**
 * TODO this class needs to be transformed into a "resource access point"
 */
@EBean(scope = EBean.Scope.Singleton)
public class Restaurants {
    @StringRes(R.string.mensa)
    String mensa;
    @StringRes(R.string.abendmensa)
    String abendmensa;
    @StringRes(R.string.pub)
    String pub;
    @StringRes(R.string.hotspot)
    String hotspot;

    @StringArrayRes(R.array.restaurants)
    String[] restaurants;

    private final Logger LOGGER = LoggerFactory.getLogger(Restaurants.class.getSimpleName());

    private int getIdOf(String restaurant) {
        if(BuildConfig.DEBUG) LOGGER.trace("getIdOf({})", restaurant);

        for (int i = 0; i < restaurants.length; i++) {
            if (restaurants[i].equals(restaurant)) {
                if(BuildConfig.DEBUG) LOGGER.trace("getIdOf({}) -> {}", restaurant,i);
                return i;
            }
        }

        if(BuildConfig.DEBUG) LOGGER.warn("getIdOf({}) -> 0 (restaurant not found!)", restaurant);
        return 0;
    }

    public int getIdOfMensa() {
        return getIdOf(mensa);
    }

    public int getIdOfAbendmensa() {
        return getIdOf(abendmensa);
    }

    public int getIdOfPub() {
        return getIdOf(pub);
    }

    public int getIdOfHotspot() {
        return getIdOf(hotspot);
    }

    public String getMensa() {
        return mensa;
    }

    public String getAbendmensa() {
        return abendmensa;
    }

    public String getPub() {
        return pub;
    }

    public String getHotspot() {
        return hotspot;
    }
}
