package de.ironjan.mensaupb.library.stw;

import android.content.*;

import com.j256.ormlite.logger.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;

import de.ironjan.mensaupb.library.*;

/**
 * Wrapper for the StwRest
 */
@EBean
public class StwRestWrapper implements StwRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StwRestWrapper.class);
    @RestService
    StwRest stwRest;
    @RootContext
    Context mContext;

    @Override
    public RawMenu[] getMenus(String restaurant, String date) {
        if (BuildConfig.STW_URL.isEmpty()) {
            LOGGER.warn("STW_URL is empty. Mocking response for ({},{})", restaurant, date);
            return MockRestWrapper.getInstance().getMenus(restaurant, date);
        }
        return stwRest.getMenus(restaurant, date);
    }

}
