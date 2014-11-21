package de.ironjan.mensaupb.sync;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import java.util.List;

import de.ironjan.mensaupb.adapters.WeekdayHelper_;
import de.ironjan.mensaupb.library.stw.RawMenu;
import de.ironjan.mensaupb.library.stw.StwRestWrapper;
import de.ironjan.mensaupb.library.stw.StwRestWrapper_;
import de.ironjan.mensaupb.persistence.DatabaseHelper;
import de.ironjan.mensaupb.persistence.DatabaseManager;

/**
 * SyncAdapter to download and persist menus.
 */
public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {


    private static final Object lock = new Object();

    private static MenuSyncAdapter instance;
    private final Logger LOGGER = LoggerFactory.getLogger(MenuSyncAdapter.class.getSimpleName());
    private final ContentResolver mContentResolver;
    private final String[] restaurants;
    private final WeekdayHelper_ mWeekdayHelper;
    private Dao<RawMenu, ?> dao;
    private final ContentResolver contentResolver;
    private final StwRestWrapper stwRestWrapper;

    private MenuSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        stwRestWrapper = StwRestWrapper_.getInstance_(context);
        restaurants = context.getResources().getStringArray(de.ironjan.mensaupb.R.array.restaurants);
        contentResolver = context.getContentResolver();
        mWeekdayHelper = WeekdayHelper_.getInstance_(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MenuSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        stwRestWrapper = StwRestWrapper_.getInstance_(context);
        restaurants = context.getResources().getStringArray(de.ironjan.mensaupb.R.array.restaurants);
        contentResolver = context.getContentResolver();
        mWeekdayHelper = WeekdayHelper_.getInstance_(context);
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


        try {
            tryMenuSync();
        } catch (java.sql.SQLException e) {
            LOGGER.warn("onPerformeSync({},{},{},{},{}) failed because of exception", new Object[]{account, bundle, s, contentProviderClient, syncResult});
            LOGGER.error(e.getMessage(), e);
        } catch (RestClientException e) {
            LOGGER.warn("onPerformeSync({},{},{},{},{}) failed because of exception", new Object[]{account, bundle, s, contentProviderClient, syncResult});
            LOGGER.error(e.getMessage(), e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{}) done", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
    }

    private void tryMenuSync() throws java.sql.SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseHelper helper = (databaseManager.getHelper(getContext()));
        ConnectionSource connectionSource =
                new AndroidConnectionSource(helper);
        dao = DaoManager.createDao(connectionSource, RawMenu.class);

        String[] cachedDaysAsStrings = mWeekdayHelper.getCachedDaysAsStrings();

        for (String date : cachedDaysAsStrings) {
            for (String restaurant : restaurants) {
                syncMenus(restaurant, date);
            }
        }
        databaseManager.releaseHelper(helper);
    }

    @org.androidannotations.annotations.Trace
    void syncMenus(String restaurant, String date) throws java.sql.SQLException, RestClientException {
        RawMenu[] menus = downloadMenus(restaurant, date);
        persistMenus(menus);
    }

    @org.androidannotations.annotations.Trace
    RawMenu[] downloadMenus(String restaurant, String date) {
        return stwRestWrapper.getMenus(restaurant, date);
    }

    @org.androidannotations.annotations.Trace
    void persistMenus(RawMenu[] menus) throws java.sql.SQLException {
        for (RawMenu rawMenu : menus) {
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
