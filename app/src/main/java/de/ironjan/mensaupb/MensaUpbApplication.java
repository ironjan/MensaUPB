package de.ironjan.mensaupb;

import android.app.*;
import android.content.*;
import android.os.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.sync.*;

/**
 * Used to set up synchronization on first start.
 */
@SuppressWarnings("WeakerAccess")
@EApplication
public class MensaUpbApplication extends Application {
    private final Logger LOGGER = LoggerFactory.getLogger(MensaUpbApplication.class.getSimpleName()
    );
    @Bean
    AccountCreator mAccountCreator;

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) LOGGER.debug("onCreate()");
        super.onCreate();
        setupSynchronization();
        if (BuildConfig.DEBUG) LOGGER.debug("onCreate() done");
    }

    private void setupSynchronization() {
        if (BuildConfig.DEBUG) LOGGER.debug("setupSynchronization()");
        ContentResolver.addPeriodicSync(mAccountCreator.getAccount(), mAccountCreator.getAuthority(), new Bundle(), BuildConfig.SYNC_INTERVAL);

        forceFirstSync();

        if (BuildConfig.DEBUG) LOGGER.debug("setupSynchronization() done");
    }

    private void forceFirstSync() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccountCreator.getAccount(), mAccountCreator.getAuthority(), settingsBundle);
    }
}
