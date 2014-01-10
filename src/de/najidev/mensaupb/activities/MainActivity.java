package de.najidev.mensaupb.activities;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager.*;
import android.support.v7.app.*;
import android.widget.*;


import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.androidannotations.annotations.rest.*;
import org.slf4j.*;

import java.sql.Date;
import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.adapter.*;
import de.najidev.mensaupb.dialog.*;
import de.najidev.mensaupb.entity.*;
import de.najidev.mensaupb.helper.*;
import de.najidev.mensaupb.helper.Context;
import de.najidev.mensaupb.rest.*;
import de.najidev.mensaupb.rest.Menu;

@EActivity(R.layout.main)
//@OptionsMenu(R.menu.main)
public class MainActivity extends ActionBarActivity implements
		OnPageChangeListener, ActionBar.TabListener {

	public static final String EXTRA_KEY_CHOSEN_LOCATION = "chosenLocation";

	private static final int TAB_THURSDAY = 3;
	private static final int TAB_WEDNESDAY = 2;
	private static final int TAB_TUESDAY = 1;
	private static final int TAB_MONDAY = 0;

	String actionBarTitle;

	@ViewById(R.id.viewpager)
	ViewPager dayPager;

	DayPagerAdapter dayPagerAdapter;
	MenuRepository menuRepository;
	Context context;
	Configuration config;

	@StringRes
	String select_Location;
	Logger LOGGER = LoggerFactory.getLogger(MainActivity.class.getSimpleName());

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		Object[] params = { savedInstanceState };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({})", params);
		}
		super.onCreate(savedInstanceState);

		ServiceContainer container = ServiceContainer.getInstance();
		if (!container.isInitialized()) {
			try {
				container = ServiceContainer.getInstance().initialize(
						getApplicationContext());
			} catch (final Exception e) {
				e.printStackTrace();
				finish();
			}
		}

		context = container.getContext();
		menuRepository = container.getMenuRepository();
		config = container.getConfiguration();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({}) -> {}", params, "VOID");
		}
	}

	@StringArrayRes(R.array.weekDays)
	String[] germanDays;


	@AfterViews
	void afterViews() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("afterViews()");
		}
		dayPagerAdapter = new DayPagerAdapter(this);

		dayPager.setOnPageChangeListener(this);
		dayPager.setAdapter(dayPagerAdapter);

		initializeActionBarTabs();

		if (menuRepository.dataIsNotLocallyAvailable()) {
			new PrepareMenuRepositoryTask(this, context, menuRepository)
					.execute();
		}

		final Date today = new Date(new java.util.Date().getTime());

		int i;
		i = 0;
		for (final Date date : context.getAvailableDates()) {
			if (date.toString().equals(today.toString())) {
				dayPager.setCurrentItem(i);
				break;
			}

			i++;
		}

		String location;

		if (TAB_MONDAY == i) {
			location = config.getMondayLocation();
		}
		else if (TAB_TUESDAY == i) {
			location = config.getTuesdayLocation();
		}
		else if (TAB_WEDNESDAY == i) {
			location = config.getWednesdayLocation();
		}
		else if (TAB_THURSDAY == i) {
			location = config.getThursdayLocation();
		}
		else {
			location = config.getFridayLocation();
		}

		context.setCurrentLocation(location);
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
		for (final Date date : context.getAvailableDates()) {
			calendar.setTime(date);
			final ActionBar.Tab tab = getSupportActionBar().newTab();
			tab.setText(germanDays[i++] + "\n"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "."
					+ (calendar.get(Calendar.MONTH) + 1) + ".");
			tab.setTabListener(this);
			getSupportActionBar().addTab(tab);
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
		new PrepareMenuRepositoryTask(this, context, menuRepository).execute();
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
			context.setCurrentLocation(context.getLocationTitle()[data
					.getIntExtra(EXTRA_KEY_CHOSEN_LOCATION, 0)]);
			changedLocation();
		}
	}

	protected void changedLocation() {
		getSupportActionBar().setTitle(context.getCurrentLocationTitle());
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