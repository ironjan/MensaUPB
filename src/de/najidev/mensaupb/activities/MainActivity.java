package de.najidev.mensaupb.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.DayPagerAdapter;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.Context;
import de.najidev.mensaupb.helper.PrepareMenuRepositoryTask;
import de.najidev.mensaupb.helper.ServiceContainer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;


public class MainActivity extends SherlockActivity implements OnPageChangeListener, TabListener, OnClickListener
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

		this.actionBarTitle = (String) this.getSupportActionBar().getTitle();

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
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.location:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				Set<String> locationSet = this.context.getAvailableLocations().keySet();
				builder.setItems(locationSet.toArray(new String[locationSet.size()]), this);
				AlertDialog alert = builder.create();
				alert.show();
				break;
			case R.id.openingtime:
				this.startActivity(new Intent().setClass(this, OpeningTimeActivity.class));
				/*
				Dialog dialog = new Dialog(this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.openingtime_dialog);
				dialog.show();
				*/
		}

		return true;
	}

	public void onClick(DialogInterface dialog, int locationId)
	{
		// set location
		Set<String> locationSet = this.context.getAvailableLocations().keySet();
		this.context.setCurrentLocation(locationSet.toArray(new String[locationSet.size()])[locationId]);

		// notify pagerAdapter
		this.changedLocation();
	}

	protected void changedLocation()
	{
		this.getSupportActionBar().setTitle(this.actionBarTitle + " - " + this.context.getCurrentLocationTitle());
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