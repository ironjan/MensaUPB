package de.najidev.mensaupb.sync;

import android.accounts.*;
import android.content.*;
import android.os.*;

import org.slf4j.*;

import de.najidev.mensaupb.rest.*;

/**
 * Created by ljan on 10.01.14.
 */
public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String SYNC_FINISHED = "SYNC_FINISHED";
    private final Logger LOGGER = LoggerFactory.getLogger(MenuSyncAdapter.class.getSimpleName());

    IUpb mIUpb;
    ContentResolver mContentResolver;

    public MenuSyncAdapter(Context context, boolean autoInitialize) {
        this(context, autoInitialize, false);
    }

    public MenuSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        mIUpb = new IUpb_();
    }


    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{})", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }

        final Restaurant[] restaurants = syncRestaurants();
        syncMenus(restaurants);
        broadcastSyncEnd();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{}) done", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
    }

    private void broadcastSyncEnd() {
        Intent i = new Intent(SYNC_FINISHED);
        getContext().sendBroadcast(i);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("broadcastSyncEnd() done");
        }
    }

    private void syncMenus(Restaurant[] restaurants) {
        for (Restaurant restaurant : restaurants) {
            final String name = restaurant.getName();
            LOGGER.info("Got restaurant {}", name);
            final MenuEntry[] menus = mIUpb.getMenus(name);
            for (MenuEntry menu : menus) {
                LOGGER.info("Got menu {}", menu);
            }
            persistMenus(menus);
        }
    }

    private void persistMenus(MenuEntry[] menus) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("persistMenus() NYI");
        }

        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("persistMenus() NYI done");
        }
    }

    private Restaurant[] syncRestaurants() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("syncRestaurants()");
        }

        final Restaurant[] restaurants = mIUpb.getRestaurants();
        persistRestaurants();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("syncRestaurants()");
        }
        return restaurants;
    }

    private void persistRestaurants() {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("persistRestaurants() NYI");
        }

        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("persistRestaurants() NYI done");
        }
    }
}
