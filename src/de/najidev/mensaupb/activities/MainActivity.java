package de.najidev.mensaupb.activities;

import android.accounts.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager.*;
import android.support.v7.app.*;
import android.util.*;
import android.widget.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.stmt.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.adapter.*;
import de.najidev.mensaupb.dialog.*;
import de.najidev.mensaupb.entity.*;
import de.najidev.mensaupb.persistence.*;
import de.najidev.mensaupb.rest.*;
import de.najidev.mensaupb.sync.*;

@EActivity(R.layout.main)
//@OptionsMenu(R.menu.main)
public class MainActivity extends ActionBarActivity implements
        OnPageChangeListener, ActionBar.TabListener {

    public static final int TWELVE_HOURS_IN_MILLIS = 60 * 60 * 12;
    private static final long SYNC_INTERVAL = TWELVE_HOURS_IN_MILLIS;
    @Bean
    AccountCreator mAccountCreator;

    public static final String EXTRA_KEY_CHOSEN_LOCATION = "chosenLocation";

    private static final int TAB_THURSDAY = 3;
    private static final int TAB_WEDNESDAY = 2;
    private static final int TAB_TUESDAY = 1;
    private static final int TAB_MONDAY = 0;

    String actionBarTitle;

    @ViewById(R.id.viewpager)
    ViewPager dayPager;

    DayPagerAdapter dayPagerAdapter;

    @StringRes
    String select_Location;
    Logger LOGGER = LoggerFactory.getLogger(MainActivity.class.getSimpleName());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Object[] params = {savedInstanceState};
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onCreate({})", params);
        }
        super.onCreate(savedInstanceState);

        setupSynchronization();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onCreate({}) -> {}", params, "VOID");
        }
    }

    private final SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver() {
        @Override
        public void onStatusChanged(int which) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Account account = mAccountCreator.build(MainActivity.this);
                    boolean syncActive = ContentResolver.isSyncActive(
                            account, mAccountCreator.getAuthority());
                    boolean syncPending = ContentResolver.isSyncPending(
                            account, mAccountCreator.getAuthority());
                    setRefreshState(syncActive || syncPending);
                }
            });
        }
    };

    private void setRefreshState(boolean isSyncing) {
        LOGGER.error("Sync status, isSyncing={}", isSyncing);
    }

    private void setupSynchronization() {
        registerReceiver(syncFinishedReceiver, new IntentFilter(MenuSyncAdapter.SYNC_FINISHED));
        final Account account = mAccountCreator.build(this);
        if (mAccountCreator.wasAccountCreated()) {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(account, mAccountCreator.getAuthority(), settingsBundle);
        }

        ContentResolver.addPeriodicSync(account, mAccountCreator.getAuthority(), new Bundle(), SYNC_INTERVAL);
    }

    private BroadcastReceiver syncFinishedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            LOGGER.debug("Sync finished, should refresh nao!!");
        }
    };
    @StringArrayRes(R.array.weekDays)
    String[] germanDays;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(syncFinishedReceiver, new IntentFilter(MenuSyncAdapter.SYNC_FINISHED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(syncFinishedReceiver);
    }

    @AfterViews
    void afterViews() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("afterViews()");
        }
        dayPagerAdapter = new DayPagerAdapter(this);

        dayPager.setOnPageChangeListener(this);
        dayPager.setAdapter(dayPagerAdapter);

        initializeActionBarTabs();

        final Date today = new Date(new java.util.Date().getTime());

        int i;
        i = 0;

        // TODO check
//        for (final Date date : context.getAvailableDates()) {
//            if (date.toString().equals(today.toString())) {
//                dayPager.setCurrentItem(i);
//                break;
//            }
//
//            i++;
//        }


        String location;

        if (TAB_MONDAY == i) {
            location = "mensa";
        } else if (TAB_TUESDAY == i) {
            location = "mensa";
        } else if (TAB_WEDNESDAY == i) {
            location = "mensa";
        } else if (TAB_THURSDAY == i) {
            location = "mensa";
        } else {
            location = "mensa";
        }
        // set location

        changedLocation();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("afterViews() -> {}", "VOID");
        }
    }

    private void initializeActionBarTabs() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("initializeActionBarTabs()");
        }

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        int i = 0;
        final Calendar calendar = Calendar.getInstance();
        List<MenuContent> dates = new ArrayList<MenuContent>();
        try {
            DatabaseHelper databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
            final Dao<MenuContent, Long> menuContentDao = databaseHelper.getMenuContentDao();
            final PreparedQuery<MenuContent> query = menuContentDao.queryBuilder()
                    .selectColumns("date").distinct()
                    .prepare();
            dates = menuContentDao.query(query);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Got {} different dates)", dates.size());
        }

        for (MenuContent mc: dates) {
            final Date date = mc.getDate();
            calendar.setTime(date);
            final ActionBar.Tab tab = getSupportActionBar().newTab();
            tab.setText(germanDays[i++] + "\n"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "."
                    + (calendar.get(Calendar.MONTH) + 1) + ".");
            tab.setTabListener(this);
            getSupportActionBar().addTab(tab);
            LOGGER.debug("Added tab for {}.", date);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("abChangeLocationClicked() -> {}", "VOID");
        }
    }

    @OptionsItem(R.id.ab_changeLocation)
    void abChangeLocationClicked() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("abChangeLocationClicked()");
        }

        Toast.makeText(this, "Temporary not available", Toast.LENGTH_SHORT).show();
        // TODO show dialog!
//        final Intent i = new Intent(this, ChooseOnListDialog.class);
//
//		i.putExtra(ChooseOnListDialog.EXTRA_KEY_TITLE, select_Location);
//		i.putExtra(ChooseOnListDialog.EXTRA_KEY_LIST,
//				context.getLocationTitle());
//
//		startActivityForResult(i, 1);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("abChangeLocationClicked() -> {}", "VOID");
        }
    }

    @OptionsItem(R.id.ab_times)
    void abTimesClicked() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("abTimesClicked()");
        }
        OpeningTimeDialog_.intent(this).start();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("abTimesClicked() -> {}", "VOID");
        }
    }

    @OptionsItem(R.id.ab_refresh)
    void abRefreshClicked() {
        // TODO force sync?
    }

    @OptionsItem(R.id.ab_settings)
    void abSettingsClicked() {
        SettingsActivity_.intent(this).start();
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (1 == requestCode && 1 == resultCode) {
            // TODO set location
//            context.setCurrentLocation(context.getLocationTitle()[data
//                    .getIntExtra(EXTRA_KEY_CHOSEN_LOCATION, 0)]);
            changedLocation();
        }
    }

    protected void changedLocation() {
        getSupportActionBar().setTitle("new location"); // TODO
        dayPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageSelected(final int arg0) {
        getSupportActionBar().getTabAt(arg0).select();
    }


    public DayPagerAdapter getDayPagerAdapter() {
        return dayPagerAdapter;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        dayPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onPageScrollStateChanged(final int arg0) {
    }

    @Override
    public void onPageScrolled(final int arg0, final float arg1, final int arg2) {
    }

}