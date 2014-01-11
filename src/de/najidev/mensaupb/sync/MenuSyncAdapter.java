package de.najidev.mensaupb.sync;

import android.accounts.*;
import android.content.*;
import android.os.*;

import com.j256.ormlite.android.*;
import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;

import org.slf4j.*;

import java.sql.*;
import java.util.*;

import de.najidev.mensaupb.persistence.*;
import de.najidev.mensaupb.rest.*;

/**
 * Created by ljan on 10.01.14.
 */
public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String SYNC_FINISHED = "SYNC_FINISHED";
    private final Logger LOGGER = LoggerFactory.getLogger(MenuSyncAdapter.class.getSimpleName());
    private final Context mContext;

    IUpb mIUpb;
    ContentResolver mContentResolver;
    private DatabaseHelper databaseHelper;

    public MenuSyncAdapter(Context context, boolean autoInitialize) {
        this(context, autoInitialize, false);
    }

    public MenuSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        mIUpb = new IUpb_();
        mContext = context;
    }


    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{})", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }

        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
        }

        final Restaurant[] restaurants = syncRestaurants();
        syncMenus(restaurants);
        broadcastSyncEnd();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }

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
            persistMenus(restaurant, menus);
        }
    }

    private void persistMenus(Restaurant restaurant, MenuEntry[] menus) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("persistMenus() NYI");
        }

        try {
            Dao<MenuContent, Long> menuContentDao = databaseHelper.getMenuContentDao();

            final List<MenuContent> existingMenuContents = menuContentDao.queryForAll();

            int deleteCounter = 0;
            for (MenuContent existingMenuContent : existingMenuContents) {
                menuContentDao.delete(existingMenuContent);
                LOGGER.debug("Deleted {}", existingMenuContent);
                deleteCounter++;
            }
            LOGGER.info("Deleted {} menu contents.", deleteCounter);

            int createCounter = 0;
            for (MenuEntry menu : menus) {
                final MenuContent menuContent = menu.getMenuContent();
                menuContent .setRestaurant(restaurant);
                menuContentDao.create(menuContent);
                LOGGER.debug("Created {}", menu);
                createCounter++;
            }
            LOGGER.info("Created {} menu contents.", createCounter);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
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
        persistRestaurants(restaurants);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("syncRestaurants()");
        }
        return restaurants;
    }

    private void persistRestaurants(Restaurant[] restaurants) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("persistRestaurants() NYI");
        }
        try {
            Dao<Restaurant, Long> restaurantDao = databaseHelper.getRestaurantDao();

            final List<Restaurant> existingRestaurants = restaurantDao.queryForAll();
            int deleteCounter = 0;
            for (Restaurant existingRestaurant : existingRestaurants) {
                restaurantDao.delete(existingRestaurant);
                LOGGER.debug("Deleted {}", existingRestaurant);
                deleteCounter++;
            }
            LOGGER.info("Deleted {} restaurants.", deleteCounter);

            int createCounter = 0;
            for (Restaurant restaurant : restaurants) {
                restaurantDao.create(restaurant);
                LOGGER.debug("Created {}", restaurant);
                createCounter++;
            }
            LOGGER.info("Created {} restaurants.", createCounter);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("persistRestaurants() NYI done");
        }
    }
}
