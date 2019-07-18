package de.ironjan.mensaupb.menus_ui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import arrow.core.Either;
import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.api.ClientV3Implementation;
import de.ironjan.mensaupb.api.model.Menu;
import de.ironjan.mensaupb.api.model.Restaurant;
import de.ironjan.mensaupb.app_info.About_;
import de.ironjan.mensaupb.prefs.InternalKeyValueStore_;

@SuppressWarnings("WeakerAccess")
@SuppressLint("Registered")
@EActivity(R.layout.activity_menu_listing)
@OptionsMenu(R.menu.main)
public class Menus extends AppCompatActivity implements ActionBar.OnNavigationListener, MenusNavigationCallback {

    public static final String KEY_DAY_OFFSET = "KEY_DAY_OFFSET";
    public static final String KEY_DATE = "KEY_DATE";
    public static final String KEY_RESTAURANT = "KEY_RESTAURANT";
    private final Logger LOGGER = LoggerFactory.getLogger(Menus.class.getSimpleName());
    @Extra(value = KEY_DATE)
    String dateAsString = null;

    @ViewById(R.id.pager)
    ViewPager mViewPager;
    @ViewById(R.id.pager_title_strip)
    PagerTabStrip mPagerTabStrip;
    String[] mRestaurantKeys = Restaurant.Companion.getKeys();
    @Bean
    WeekdayHelper mwWeekdayHelper;
    @Extra(value = KEY_RESTAURANT)
    String restaurant = mRestaurantKeys[0];
    @InstanceState
    int mLocation = 0;
    @InstanceState
    int mDayOffset = 0;
    @Pref
    InternalKeyValueStore_ mInternalKeyValueStore;
    @Bean
    WeekdayHelper mWeekdayHelper;
    private WeekdayPagerAdapter[] adapters;
    private WeekdayPagerAdapter mWeekdayPagerAdapter;

    @Trace
    @AfterViews
    @Background
    void init() {
        mLocation = mInternalKeyValueStore.lastLocation().get();
        initPager();
        initActionBar();
    }


    void loadTest() {
        final long t1 = System.currentTimeMillis();
        final ClientV3Implementation client = new ClientV3Implementation(this);
        Either<String, Menu[]> either = client.getMenus();

        final long t2 = System.currentTimeMillis();

        AtomicInteger requests = new AtomicInteger();
        AtomicInteger discards = new AtomicInteger();

        HashMap<String, Menu> menusByKey = new HashMap<>();
        LinkedList<String> errors = new LinkedList<>();
        if(either.isRight()){
            either.map(menus -> {
                LOGGER.warn(menus.length + " menus loaded via ion.");
                for (Menu menu : menus) {
                    final String key = menu.getKey();
                    final Either<String, Menu> loadedMenuEither = client.getMenu(key);
                    if(loadedMenuEither.isRight()) {
                        loadedMenuEither.map(m -> {menusByKey.put(key,m); return m;});
                    }else{
                        loadedMenuEither.mapLeft(s -> {errors.add(s); return s;});
                    }
                    requests.getAndIncrement();
                    LOGGER.debug("Did "+requests+"/"+menus.length+ " requests.");
                }
                return menus;
            });
        }else{
            either.mapLeft(s -> {
                LOGGER.warn(s);
                return s;
            });
        }

        final long t3 = System.currentTimeMillis();

        long originalRequestTime = t2-t1;
        long singleMenusRequestsTime = t3-t2;

        int errorCount = errors.size();
        int successCount = menusByKey.size();

        LOGGER.warn("Timing. orignal request = " + originalRequestTime + "ms. single menus = "+singleMenusRequestsTime+"ms. " + requests+ " requests and " + discards + " discards. Successes: "+ successCount+", Errors: "+errorCount);
    }

    @Trace
    @UiThread
    void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        List<Integer> nameStringIds = Restaurant.Companion.getNameStringIds();
        int restaurantCount = nameStringIds.size();
        String[] mDisplayedRestaurantNames = new String[restaurantCount];
        final Resources resources = getResources();
        for (int i = 0; i < restaurantCount; i++) {
            mDisplayedRestaurantNames[i] = resources.getString(nameStringIds.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                mDisplayedRestaurantNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapter, this);
        actionBar.setSelectedNavigationItem(mLocation);
    }

    @Trace
    @UiThread
    void initPager() {
        mPagerTabStrip.setTabIndicatorColorResource(R.color.iconBg);
        mPagerTabStrip.setDrawFullUnderline(true);

        loadPagerAdapter(mLocation);
    }

    @Background
    @Trace
    void loadPagerAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("loadPagerAdapter({})", i);
        mWeekdayPagerAdapter =
                getPagerAdapter(i);
        if (BuildConfig.DEBUG) LOGGER.info("Got adapter: {}", mWeekdayPagerAdapter);
        if (mWeekdayPagerAdapter != null) {
            switchAdapterTo(mDayOffset);
        }
    }

    @UiThread
    void switchAdapterTo(int currentItem) {
        LOGGER.warn("switch to {}", currentItem);
        mViewPager.setAdapter(mWeekdayPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
    }

    @Trace
    WeekdayPagerAdapter getPagerAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("getPagerAdapter({})", i);
        if (adapters == null) {
            adapters = new WeekdayPagerAdapter[mRestaurantKeys.length];
        }
        if (adapters[i] == null) {
            createNewAdapter(i);
        }
        return adapters[i];
    }

    @Trace
    @Background
    void createNewAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("createNewAdapter({})", i);
        adapters[i] =
                new WeekdayPagerAdapter(this, getSupportFragmentManager(), mRestaurantKeys[i]);
        loadPagerAdapter(i);
    }


    @Override
    @Trace
    public boolean onNavigationItemSelected(int i, long l) {
        mLocation = i;
        if (BuildConfig.DEBUG)
            LOGGER.debug("onNavigationItemSelected({},{}), location := {}", new Object[]{i, l, mLocation});
        mDayOffset = mViewPager.getCurrentItem();
        loadPagerAdapter(i);

        restaurant = mRestaurantKeys[i];

        return true;
    }

    @Override
    public void showMenu(String key) {
        MenuDetails_.intent(this)
                .menuKey(key)
                .start();
    }

    @Override
    public void showMenu(Menu m) {
        MenuDetails_.intent(this)
                .restaurant(m.getRestaurant())
                .date(m.getDate())
                .nameEn(m.getName_en())
                .start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mInternalKeyValueStore.edit().lastLocation().put(mLocation).apply();
    }


    @OptionsItem(R.id.ab_refresh)
    void refreshClicked() {
        mWeekdayPagerAdapter.onRefresh();
    }

    @OptionsItem(R.id.ab_openingTimes)
    void openBrowserWithOpeningTimes(){
        final String OPENING_TIMES_URL = "http://www.studierendenwerk-pb.de/gastronomie/allgemein/oeffnungszeiten/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(OPENING_TIMES_URL));
        startActivity(intent);

    }

    @OptionsItem(R.id.ab_about)
    void aboutClicked() {
        About_.intent(this).start();
    }
}
