package de.najidev.mensaupb;

import android.accounts.*;
import android.app.*;
import android.content.*;
import android.os.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.najidev.mensaupb.sync.*;

/**
 * Used to set up synchronization on first start.
 */
@EApplication
public class MensaUpbApplication extends Application {
    @Bean
    AccountCreator mAccountCreator;

    private final Logger LOGGER = LoggerFactory.getLogger(MensaUpbApplication.class.getSimpleName()
    );

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) LOGGER.debug("onCreate()");
        super.onCreate();
        setupSynchronization();
        initFirstSync();
        if (BuildConfig.DEBUG) LOGGER.debug("onCreate() done");
    }

    private void initFirstSync() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(mAccountCreator.build(this), mAccountCreator.getAuthority(), settingsBundle);
    }

    private void setupSynchronization() {
        if (BuildConfig.DEBUG) LOGGER.debug("setupSynchronization()");
        final Account account = mAccountCreator.build(this);

        Bundle settingsBundle = new Bundle();
        ContentResolver.requestSync(account, mAccountCreator.getAuthority(), settingsBundle);

        ContentResolver.addPeriodicSync(account, mAccountCreator.getAuthority(), new Bundle(), BuildConfig.SYNC_INTERVAL);

        if (BuildConfig.DEBUG) LOGGER.debug("setupSynchronization() done");
    }
}
