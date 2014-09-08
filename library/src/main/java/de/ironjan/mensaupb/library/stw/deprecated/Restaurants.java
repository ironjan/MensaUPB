package de.ironjan.mensaupb.library.stw.deprecated;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.library.BuildConfig;

/**
 * TODO this class needs to be transformed into a "resource access point"
 */
@EBean(scope = EBean.Scope.Singleton)
@Deprecated
public class Restaurants {
    @StringRes(resName="mensa")
    String mensa;
    @StringRes(resName="abendmensa")
    String abendmensa;
    @StringRes(resName="pub")
    String pub;
    @StringRes(resName="hotspot")
    String hotspot;

    @StringArrayRes(resName="restaurants")
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
