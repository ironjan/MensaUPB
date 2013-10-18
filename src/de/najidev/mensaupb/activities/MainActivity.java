package de.najidev.mensaupb.activities;

import java.util.Calendar;
import java.sql.Date;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.DayPagerAdapter;
import de.najidev.mensaupb.dialog.ChooseOnListDialog;
import de.najidev.mensaupb.dialog.OpeningTimeDialog;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.Configuration;
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
	Configuration config;

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
		this.config         = container.getConfiguration();
		

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

		if (menuRepository.dataIsNotLocallyAvailable()){
			new PrepareMenuRepositoryTask(this, context, menuRepository).execute();
		}
		
		Date today = new Date(new java.util.Date().getTime());
		i = 0;
		for (Date date : this.context.getAvailableDates())
		{
			if (date.toString().equals(today.toString()))
			{
				this.dayPager.setCurrentItem(i);
				break;
			}

			i++;
		}
		
		
		String location;
		
		if (0 == i)
			location = config.getMondayLocation();
		else if (1 == i)
			location = config.getTuesdayLocation();
		else if (2 == i)
			location = config.getWednesdayLocation();
		else if (3 == i)
			location = config.getThursdayLocation();
		else
			location = config.getFridayLocation();

		context.setCurrentLocation(location);
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
		
		menu.add("Einstellungen")
			.setIcon(R.drawable.action_settings_dark_holo)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getTitle().equals("Ortswechsel"))
		{
			Intent i = new Intent(this, ChooseOnListDialog.class);
			
			i.putExtra("title", "Ort wählen");
			i.putExtra("list", context.getLocationTitle());
			
			this.startActivityForResult(i, 1);
		}
		else if (item.getTitle().equals("Öffnungszeiten"))
			this.startActivity(new Intent().setClass(this, OpeningTimeDialog.class));
		else if (item.getTitle().equals("Einstellungen"))
			this.startActivity(new Intent().setClass(this, SettingsActivity.class));

		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (1 == requestCode && 1 == resultCode)
		{
			context.setCurrentLocation(context.getLocationTitle()[data.getIntExtra("chosen", 0)]);
			this.changedLocation();
		}
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