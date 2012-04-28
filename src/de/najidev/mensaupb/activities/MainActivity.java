package de.najidev.mensaupb.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import roboguice.activity.RoboTabActivity;

import com.google.inject.Inject;

import de.najidev.mensaupb.ApplicationContext;
import de.najidev.mensaupb.R;
import de.najidev.mensaupb.dialogs.OpeningTimeDialog;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.PrepareMenuRepositoryTask;

public class MainActivity extends RoboTabActivity implements OnClickListener, OnTabChangeListener
{
	@Inject
	MenuRepository repo;
	
	@Inject
	ApplicationContext applicationContext;

	public void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Resource object to get Draw-able
		Resources res = getResources();
		
		// The activity TabHost
		TabHost tabHost = getTabHost();

		
		// Reusable TabSpec for each tab
		TabHost.TabSpec spec;
		Intent intent = new Intent().setClass(this, DayActivity.class);
		int resId;

		// for each of the available dates, we build a tab
		for (Date date : applicationContext.getAvailableDates())
		{
			// we use +1 here, as for Monday: date.getDay() + 1 == Calendar.Monday
			switch (date.getDay() + 1)
			{
				case Calendar.MONDAY:
					resId = R.drawable.ic_tab_mon;
					break;
				case Calendar.TUESDAY:
					resId = R.drawable.ic_tab_tue;
					break;
				case Calendar.WEDNESDAY:
					resId = R.drawable.ic_tab_wed;
					break;
				case Calendar.THURSDAY:
					resId = R.drawable.ic_tab_thu;
					break;
				case Calendar.FRIDAY:
				default:
					resId = R.drawable.ic_tab_fri;
					break;
			}

			spec = tabHost
					.newTabSpec(String.valueOf(date.getDay()))
					// as indicator: the date, which the tab represents and the tab image
					.setIndicator(
						date.getDate() + "." + (date.getMonth() + 1) + ".",
						res.getDrawable(resId)
					)
					// add as content the date and the current location
					.setContent(intent);

			tabHost.addTab(spec);
		}
		
		tabHost.setOnTabChangedListener(this);
	}
	
	
	@Override
	protected void onStart()
	{
		super.onStart();

		// set to current tab
		getTabHost().setCurrentTab(this.applicationContext.getCurrentDate().getDay() - 1);
		
		if (!this.repo.dataIsLocallyAvailable())
			new PrepareMenuRepositoryTask(this, applicationContext, repo).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
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
				Set<String> locationSet = this.applicationContext.getAvailableLocations().keySet();
				builder.setItems(locationSet.toArray(new String[locationSet.size()]), this);
				AlertDialog alert = builder.create();
				alert.show();
				break;
			case R.id.openingtime:
				new OpeningTimeDialog(this).show();
				break;
		}
		return true;
	}

	public void onClick(DialogInterface dialog, int locationId)
	{
		// set location
		Set<String> locationSet = this.applicationContext.getAvailableLocations().keySet();
		this.applicationContext.setCurrentLocation(locationSet.toArray(new String[locationSet.size()])[locationId]);
		
		getTabHost().setCurrentTabByTag(getTabHost().getCurrentTabTag());
		// redraw current tab
		this.redrawTab(getTabHost().getCurrentTabTag());
	}

	public void onTabChanged(String tabId)
	{
		for (Date date : this.applicationContext.getAvailableDates())
			if (String.valueOf(date.getDay()).equals(tabId))
				this.applicationContext.setCurrentDate(date);

		this.redrawTab(tabId);
	}

	protected void redrawTab(String tabTag)
	{
		// get the activity by the local activity manager
		DayActivity tabActivity = (DayActivity) this.getLocalActivityManager().getActivity(tabTag);
		
		// draw the user interface
		tabActivity.drawUserInterface();	
	}
}
