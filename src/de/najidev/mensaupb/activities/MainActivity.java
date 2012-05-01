package de.najidev.mensaupb.activities;

import java.util.Calendar;
import java.util.Date;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.DayPagerAdapter;
import de.najidev.mensaupb.dialog.ChooseLocationDialog;
import de.najidev.mensaupb.dialog.OpeningTimeDialog;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.Context;
import de.najidev.mensaupb.helper.PrepareMenuRepositoryTask;
import de.najidev.mensaupb.helper.ServiceContainer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;


public class MainActivity extends SherlockActivity implements OnPageChangeListener, TabListener
{
	String actionBarTitle;
	ViewPager dayPager;
	DayPagerAdapter dayPagerAdapter;
	MenuRepository menuRepository;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ServiceContainer container = ServiceContainer.getInstance();
		if (!container.isInitialized())
		{
			try
			{
				container = ServiceContainer.getInstance().initialize(this.getApplicationContext());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.finish();
			}
		}

		this.context        = container.getContext();
		this.menuRepository = container.getMenuRepository();

		dayPagerAdapter = new DayPagerAdapter(this);
		dayPager = (ViewPager) findViewById(R.id.viewpager);
		dayPager.setOnPageChangeListener(this);
		dayPager.setAdapter(dayPagerAdapter);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		String[] germanDays = new String[] {"Mo", "Di", "Mi", "Do", "Fr"};
		int i = 0;
		Calendar calendar = Calendar.getInstance();
		for (Date date : this.context.getAvailableDates())
		{
			calendar.setTime(date);
			ActionBar.Tab tab = this.getSupportActionBar().newTab();
			tab.setText(germanDays[i++] + "\n" + calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + ".");
			tab.setTabListener(this);
			this.getSupportActionBar().addTab(tab);	
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		if (!this.menuRepository.dataIsLocallyAvailable())
			new PrepareMenuRepositoryTask(this, context, menuRepository).execute();

		this.changedLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("Ortswechsel")
			.setIcon(R.drawable.location_place_dark_holo)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		menu.add("Öffnungszeiten")
			.setIcon(R.drawable.action_about_dark_holo)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		/*
		menu.add("Einstellungen")
			.setIcon(R.drawable.action_settings_dark_holo)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		*/

		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getTitle().equals("Ortswechsel"))
			this.startActivityForResult(new Intent().setClass(this, ChooseLocationDialog.class), 1);
		else if (item.getTitle().equals("Öffnungszeiten"))
			this.startActivity(new Intent().setClass(this, OpeningTimeDialog.class));

		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (1 == requestCode && 1 == resultCode)
			this.changedLocation();
	}

	protected void changedLocation()
	{
		this.getSupportActionBar().setTitle(this.context.getCurrentLocationTitle());
		this.dayPagerAdapter.notifyDataSetChanged();
	}

	public void onPageSelected(int arg0)
	{
		getSupportActionBar().getTabAt(arg0).select();	
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		dayPager.setCurrentItem(tab.getPosition());
	}

	public DayPagerAdapter getDayPagerAdapter()
	{
		return dayPagerAdapter;
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) { }
	public void onTabReselected(Tab tab, FragmentTransaction ft) { }
	public void onPageScrollStateChanged(int arg0) { }
	public void onPageScrolled(int arg0, float arg1, int arg2) { }
}