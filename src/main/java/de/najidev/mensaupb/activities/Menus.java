package de.najidev.mensaupb.activities;


import android.annotation.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.text.*;
import android.view.*;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.Trace;
import org.slf4j.*;

import java.text.*;
import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.fragments.*;
import de.najidev.mensaupb.stw.Menu;
import de.najidev.mensaupb.sync.*;

@EActivity(R.layout.activity_menu_listing)
public class Menus extends ActionBarActivity implements ActionBar.OnNavigationListener, SyncStatusObserver {
    private final Logger LOGGER = LoggerFactory.getLogger(Menus.class.getSimpleName());
    public static final int WEEKEND_OFFSET = 2;
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private static final SimpleDateFormat SDF = Menu.DATABASE_DATE_FORMAT;
    public static final String[] RESTAURANTS = new String[]{"Mensa", "Gownsmen's Pub", "HNF (Hotspot)"};
    private String mLocation = "Mensa";
    private DemoCollectionPagerAdapter[] adapters = new DemoCollectionPagerAdapter[4];
    private static final int DISPLAYED_DAYS_COUNT = 3;
    private String[] weekDaysAsString = new String[DISPLAYED_DAYS_COUNT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mask = ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING | ContentResolver.SYNC_OBSERVER_TYPE_SETTINGS;
        ContentResolver.addStatusChangeListener(mask, this);
        this.requestWindowFeature(Window.FEATURE_PROGRESS);
    }

    @Trace
    @AfterViews
    void init() {
        initActionBar();
        initDays();
        initPager();
    }

    @Trace
    @Background
    void initDays() {
        for (int i = 0; i < DISPLAYED_DAYS_COUNT; i++) {
            getNextWeekDayAsString(i);
        }
    }

    @Trace
    void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Specify a SpinnerAdapter to populate the dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                RESTAURANTS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapter, this);
    }

    @Trace
    void initPager() {
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        loadPagerAdapter(0);
    }

    @Background
    @Trace
    void loadPagerAdapter(int i) {
        mDemoCollectionPagerAdapter =
                getPagerAdapter(i);
        if (BuildConfig.DEBUG) LOGGER.info("Got adapter: {}", mDemoCollectionPagerAdapter);
        if (mDemoCollectionPagerAdapter != null) {
            switchAdapter();
        }
    }

    @UiThread
    @Trace
    void switchAdapter() {
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    @Trace
    DemoCollectionPagerAdapter getPagerAdapter(int i) {
        if (adapters[i] == null) {
            createNewAdapter(i);
        }
        return adapters[i];
    }

    @Trace
    @Background
    void createNewAdapter(int i) {
        adapters[i] =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager(), RESTAURANTS[i]);
        loadPagerAdapter(i);
    }

    @Trace
    synchronized String getNextWeekDayAsString(int i) {
        if (weekDaysAsString[i] == null) {
            weekDaysAsString[i] = SDF.format(getNextWeekDay(i));
        }
        return weekDaysAsString[i];
    }


    @Trace
    boolean dayIsWeekend(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    @Override
    @Trace
    public boolean onNavigationItemSelected(int i, long l) {
        if (TextUtils.equals(mLocation, RESTAURANTS[i]))
            return true;

        mLocation = RESTAURANTS[i];

        loadPagerAdapter(i);

        return true;
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] fragments = new Fragment[3];
        String mLocation;

        public DemoCollectionPagerAdapter(FragmentManager fm, String location) {
            super(fm);
            mLocation = location;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = getMenuListingFragment(i);
            return fragment;
        }

        Fragment getMenuListingFragment(int i) {
            if (fragments[i] == null) {
                Fragment fragment = initMenuListingFragment(i);
                fragments[i] = fragment;
            }

            return fragments[i];
        }

        Fragment initMenuListingFragment(int i) {
            Fragment fragment = new MenuListingFragment_();
            Bundle arguments = new Bundle();

            arguments.putString(MenuListingFragment.ARG_DATE, getNextWeekDayAsString(i));
            arguments.putString(MenuListingFragment.ARG_LOCATION, mLocation);

            fragment.setArguments(arguments);
            return fragment;
        }

        @Override
        public int getCount() {
            return DISPLAYED_DAYS_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getNextWeekDayAsString(position);
        }
    }


    @Trace
    String getLocation() {
        return mLocation;
    }

    @Trace
    Date getNextWeekDay(int offset) {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_WEEK, offset);

        if (dayIsWeekend(cal)) {
            cal.add(Calendar.DAY_OF_WEEK, WEEKEND_OFFSET);
        }
        return cal.getTime();
    }


    @Override
    @UiThread
    public void onStatusChanged(int status) {
        final boolean isSyncing;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            isSyncing = checkSyncingStateHC();
        } else {
            isSyncing = checkSyncingStatePreHC();
        }
        setProgressBarIndeterminate(isSyncing);
        setProgressBarVisibility(isSyncing);
    }

    @SuppressWarnings("deprecation")
    private boolean checkSyncingStatePreHC() {
        final SyncInfo currentSync = ContentResolver.getCurrentSync();
        if (isMensaUpbSync(currentSync)) return true;

        return false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean checkSyncingStateHC() {
        List<SyncInfo> currentSyncs = ContentResolver.getCurrentSyncs();


        for (SyncInfo syncInfo : currentSyncs) {
            if (isMensaUpbSync(syncInfo)) return true;
        }
        return false;
    }

    private boolean isMensaUpbSync(SyncInfo syncInfo) {
        String name = syncInfo.account.name;
        String authority = syncInfo.authority;


        if (name.equals(AccountCreator.ACCOUNT) && authority.equals(authority)) {
            return true;
        }
        return false;
    }
}
