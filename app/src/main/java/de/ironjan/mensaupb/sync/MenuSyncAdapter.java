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
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.web.client.RestClientException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.ironjan.mensaupb.menus_ui.WeekdayHelper_;
import de.ironjan.mensaupb.persistence.DatabaseHelper;
import de.ironjan.mensaupb.persistence.DatabaseManager;
import de.ironjan.mensaupb.prefs.InternalKeyValueStore_;
import de.ironjan.mensaupb.stw.Restaurant;
import de.ironjan.mensaupb.stw.filters.FilterChain;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;
import de.ironjan.mensaupb.stw.rest_api.StwRestWrapper;
import de.ironjan.mensaupb.stw.rest_api.StwRestWrapper_;


/**
 * SyncAdapter to download and persist menus.
 */
public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {


    private static final Object lock = new Object();

    private static MenuSyncAdapter instance;
    private final Logger LOGGER = LoggerFactory.getLogger(MenuSyncAdapter.class.getSimpleName());
    private final ContentResolver mContentResolver;
    private final String[] restaurants = Restaurant.getKeys();
    private final WeekdayHelper_ mWeekdayHelper;
    private final ContentResolver contentResolver;
    private final StwRestWrapper stwRestWrapper;
    private final FilterChain filterChain = new FilterChain();
    private final InternalKeyValueStore_ mInternalKeyValueStore;

    @SuppressWarnings("SameParameterValue")
    private MenuSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        stwRestWrapper = StwRestWrapper_.getInstance_(context);
        contentResolver = context.getContentResolver();
        mWeekdayHelper = WeekdayHelper_.getInstance_(context);
        mInternalKeyValueStore = new InternalKeyValueStore_(context);
    }

    @SuppressWarnings("SameParameterValue")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MenuSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        stwRestWrapper = StwRestWrapper_.getInstance_(context);
        contentResolver = context.getContentResolver();
        mWeekdayHelper = WeekdayHelper_.getInstance_(context);
        mInternalKeyValueStore = new InternalKeyValueStore_(context);
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
            updateLastSyncTime();
        } catch (SQLException | NestedRuntimeException e) {
            LOGGER.warn("onPerformeSync({},{},{},{},{}) failed because of exception", new Object[]{account, bundle, s, contentProviderClient, syncResult});
            LOGGER.error(e.getMessage(), e);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{}) done", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
    }

    private void updateLastSyncTime() {
        mInternalKeyValueStore.edit().lastSyncTimeStamp().put(System.currentTimeMillis()).apply();
    }

    private void tryMenuSync() throws java.sql.SQLException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("tryMenuSync()");
        }

        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseHelper helper = (databaseManager.getHelper(getContext()));
        ConnectionSource connectionSource =
                new AndroidConnectionSource(helper);
        Dao<StwMenu, ?> dao = DaoManager.createDao(connectionSource, StwMenu.class);

        String[] cachedDaysAsStrings = mWeekdayHelper.getCachedDaysAsStrings();

        Date now = new Date();

        for (String date : cachedDaysAsStrings) {
            for (String restaurant : restaurants) {
                syncMenus(dao, restaurant, date, now);
            }
        }
        removeUnneededMenusFromDatabase(dao, now);
        databaseManager.releaseHelper(helper);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("tryMenuSync() done");
        }
    }

    @org.androidannotations.annotations.Trace
    void syncMenus(Dao<StwMenu, ?> dao, String restaurant, String date, Date now) throws java.sql.SQLException, RestClientException {
        StwMenu[] menus = downloadMenus(restaurant, date);
        List<StwMenu> menuList = Arrays.asList(menus);
        List<StwMenu> filteredList = filterChain.filter(menuList);
        persistMenus(dao, filteredList, now);
    }

    @org.androidannotations.annotations.Trace
    StwMenu[] downloadMenus(String restaurant, String date) {
        return stwRestWrapper.getMenus(restaurant, date);
    }

    @org.androidannotations.annotations.Trace
    void persistMenus(Dao<StwMenu, ?> dao, Iterable<StwMenu> menus, Date now) throws java.sql.SQLException {
        SelectArg nameArg = new SelectArg(),
                dateArg = new SelectArg(),
                restaurantArg = new SelectArg();
        PreparedQuery<StwMenu> preparedQuery = dao.queryBuilder().where().eq(StwMenu.NAME_GERMAN, nameArg)
                .and().eq(StwMenu.DATE, dateArg)
                .and().eq(StwMenu.RESTAURANT, restaurantArg)
                .prepare();

        for (StwMenu stwMenu : menus) {
            stwMenu.setUpdatedOn(now);

            nameArg.setValue(stwMenu.getName_de());
            dateArg.setValue(stwMenu.getDate());
            restaurantArg.setValue(stwMenu.getRestaurant());

            List<StwMenu> local = dao.query(preparedQuery);
            if (local.size() > 0) {
                stwMenu.set_id(local.get(0).get_id());
                dao.update(stwMenu);
            } else {
                dao.create(stwMenu);
            }
            contentResolver.notifyChange(MenuContentProvider.MENU_URI, null, false);
        }
    }

    private void removeUnneededMenusFromDatabase(Dao<StwMenu, ?> dao, Date now) throws SQLException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("removeUnneededMenusFromDatabase(dao,{})", now);
        }

        int deleted = 0;
        try {
            DeleteBuilder<StwMenu, ?> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().not().eq(StwMenu.UPDATED_ON, now);
            deleted = deleteBuilder.delete();
        } catch (Exception e) {
            LOGGER.error("Error", e);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("removeUnneededMenusFromDatabase(dao,{}) done - deleted {} menus", now, deleted);
        }
    }


}
