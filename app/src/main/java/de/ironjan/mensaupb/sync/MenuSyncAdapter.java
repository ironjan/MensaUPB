package de.ironjan.mensaupb.sync;

import android.accounts.*;
import android.annotation.*;
import android.content.*;
import android.os.*;

import com.j256.ormlite.android.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.stmt.*;
import com.j256.ormlite.stmt.query.*;
import com.j256.ormlite.support.*;

import org.slf4j.*;
import org.springframework.core.*;
import org.springframework.web.client.*;

import java.sql.*;
import java.util.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.adapters.*;
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
    private final String[] restaurants;
    private final WeekdayHelper_ mWeekdayHelper;
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
        } catch (ResourceAccessException e) {
            LOGGER.warn("onPerformeSync({},{},{},{},{}) failed because of exception", new Object[]{account, bundle, s, contentProviderClient, syncResult});
            LOGGER.error(e.getMessage(), e);
        } catch (RestClientException e) {
            LOGGER.warn("onPerformeSync({},{},{},{},{}) failed because of exception", new Object[]{account, bundle, s, contentProviderClient, syncResult});
            LOGGER.error(e.getMessage(), e);
        } catch (NestedRuntimeException e) {
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
        Dao<RawMenu, ?> dao = DaoManager.createDao(connectionSource, RawMenu.class);

        String[] cachedDaysAsStrings = mWeekdayHelper.getCachedDaysAsStrings();

        for (String date : cachedDaysAsStrings) {
            for (String restaurant : restaurants) {
                syncMenus(dao, restaurant, date);
            }
        }
        cleanOldMenus(dao, cachedDaysAsStrings);
        databaseManager.releaseHelper(helper);
    }

    @org.androidannotations.annotations.Trace
    void syncMenus(Dao<RawMenu, ?> dao, String restaurant, String date) throws java.sql.SQLException, RestClientException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("syncMenus(dao,{},{})", restaurant, date);
        }

        RawMenu[] menus = downloadMenus(restaurant, date);
        persistMenus(dao, menus);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("syncMenus(dao,{},{}) done", restaurant, date);
        }
    }

    @org.androidannotations.annotations.Trace
    RawMenu[] downloadMenus(String restaurant, String date) {
        return stwRestWrapper.getMenus(restaurant, date);
    }

    @org.androidannotations.annotations.Trace
    void persistMenus(Dao<RawMenu, ?> dao, RawMenu[] menus) throws java.sql.SQLException {
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

    private void cleanOldMenus(Dao<RawMenu, ?> dao, String[] cachedDaysAsStrings) throws SQLException {
        if (cachedDaysAsStrings == null || cachedDaysAsStrings.length < 1) {
            return;
        }

        StringBuilder rawQueryBuilder = new StringBuilder("DELETE FROM ")
                .append(RawMenu.TABLE)
                .append(" WHERE ").append(RawMenu.DATE).append(" not in ('")
                .append(cachedDaysAsStrings[0]);
        for (int i = 1; i < cachedDaysAsStrings.length; i++) {
            rawQueryBuilder.append("', '")
                    .append(cachedDaysAsStrings[i]);
        }
        rawQueryBuilder.append("');");
        String rawQuery = rawQueryBuilder.toString();

        int rows = dao.executeRawNoArgs(rawQuery);

        if (BuildConfig.DEBUG) {
            LOGGER.info("Deleted {} rows of old menus.", rows);
        }
    }


}
