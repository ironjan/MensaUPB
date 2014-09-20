package de.ironjan.mensaupb.sync;

import android.accounts.*;
import android.annotation.*;
import android.content.*;
import android.os.*;

import com.j256.ormlite.android.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;

import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.library.stw.*;
import de.ironjan.mensaupb.persistence.*;

/**
 * SyncAdapter to download and persist menus.
 */
public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {


    private static final Object lock = new Object();

    private static MenuSyncAdapter instance;
    private final Logger LOGGER = LoggerFactory.getLogger(MenuSyncAdapter.class.getSimpleName());
    private final ContentResolver mContentResolver;
    private final StwRest stwRest;
    private final String[] restaurants;
    private Dao<RawMenu, ?> dao;
    private final ContentResolver contentResolver;

    private MenuSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        stwRest = new StwRest_(context);
        restaurants = context.getResources().getStringArray(de.ironjan.mensaupb.R.array.restaurants);
        contentResolver = context.getContentResolver();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MenuSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        stwRest = new StwRest_(context);
        restaurants = context.getResources().getStringArray(de.ironjan.mensaupb.R.array.restaurants);
        contentResolver = context.getContentResolver();
    }

    public static MenuSyncAdapter getInstance(Context context) {
        synchronized (lock) {
            if (instance == null) {
                instance = createSyncAdapterSingleton(context);
            }

            return instance;
        }
    }

    private static MenuSyncAdapter createSyncAdapterSingleton(Context context) {
        Context applicationContext = context.getApplicationContext();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            return new MenuSyncAdapter(applicationContext, true, false);
        } else {
            return new MenuSyncAdapter(applicationContext, true);
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{})", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseHelper helper = (databaseManager.getHelper(getContext()));
        ConnectionSource connectionSource =
                new AndroidConnectionSource(helper);

        try {
            dao = DaoManager.createDao(connectionSource, RawMenu.class);

            for (String restaurant : restaurants) {
                downloadMenusForRestaurant(restaurant);
            }
            databaseManager.releaseHelper(helper);
        } catch (java.sql.SQLException e) {
            LOGGER.warn("onPerformeSync({},{},{},{},{}) failed because of exception", new Object[]{account, bundle, s, contentProviderClient, syncResult});
            LOGGER.error(e.getMessage(), e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{}) done", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
    }

    private void downloadMenusForRestaurant(String restaurant) throws java.sql.SQLException {
        for (RawMenu rawMenu : stwRest.getMenus(restaurant)) {
            List<RawMenu> local = dao.queryBuilder().where().eq(RawMenu.NAME_GERMAN, rawMenu.getName_de())
                    .and().eq(RawMenu.DATE, rawMenu.getDate())
                    .and().eq(RawMenu.RESTAURANT, rawMenu.getRestaurant())
                    .query();

            if (local.size() > 0) {
                rawMenu.set_id(local.get(0).get_id());
                dao.update(rawMenu);
            } else {
                dao.create(rawMenu);
            }
            contentResolver.notifyChange(MenuContentProvider.MENU_URI, null, false);
        }
    }


}
