package de.najidev.mensaupb.activities;

import java.sql.Date;
import java.util.*;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.res.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.adapter.*;
import de.najidev.mensaupb.dialog.*;
import de.najidev.mensaupb.entity.*;
import de.najidev.mensaupb.helper.*;
import de.najidev.mensaupb.helper.Context;

@EActivity(R.layout.main)
@OptionsMenu(R.menu.main)
public class MainActivity extends SherlockActivity implements
		OnPageChangeListener, TabListener {

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

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
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

	}

	@StringArrayRes(R.array.weekDays)
	String[] germanDays;

	@AfterViews
	void afterViews() {
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
	}

	private void initializeActionBarTabs() {
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
	}

	@OptionsItem(R.id.ab_changeLocation)
	void abChangeLocationClicked() {
		final Intent i = new Intent(this, ChooseOnListDialog.class);

		i.putExtra(ChooseOnListDialog.EXTRA_KEY_TITLE, select_Location);
		i.putExtra(ChooseOnListDialog.EXTRA_KEY_LIST,
				context.getLocationTitle());

		startActivityForResult(i, 1);
	}

	@OptionsItem(R.id.ab_times)
	void abTimesClicked() {
		OpeningTimeDialog_.intent(this).start();
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

	@Override
	public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
		dayPager.setCurrentItem(tab.getPosition());
	}

	public DayPagerAdapter getDayPagerAdapter() {
		return dayPagerAdapter;
	}

	@Override
	public void onTabUnselected(final Tab tab, final FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(final Tab tab, final FragmentTransaction ft) {
	}

	@Override
	public void onPageScrollStateChanged(final int arg0) {
	}

	@Override
	public void onPageScrolled(final int arg0, final float arg1, final int arg2) {
	}
}