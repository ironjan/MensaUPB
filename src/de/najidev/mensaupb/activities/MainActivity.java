package de.najidev.mensaupb.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.MenuAdapter;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.Context;
import de.najidev.mensaupb.helper.PrepareMenuRepositoryTask;
import de.najidev.mensaupb.helper.ServiceContainer;

public class MainActivity extends TabActivity implements OnClickListener, OnTabChangeListener
{
	MenuRepository menuRepository;
	Context context;
	

	public void onCreate(Bundle savedInstanceState)
	{
		ServiceContainer container = null;
		try
		{
			container = ServiceContainer.getInstance().initialize(this.getApplicationContext());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		this.menuRepository = container.getMenuRepository();
		this.context        = container.getContext();
		
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
		for (Date date : context.getAvailableDates())
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
		getTabHost().setCurrentTab(this.context.getCurrentDate().getDay() - 1);
		
		if (!this.menuRepository.dataIsLocallyAvailable())
			new PrepareMenuRepositoryTask(this, context, menuRepository).execute();
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
				Set<String> locationSet = this.context.getAvailableLocations().keySet();
				builder.setItems(locationSet.toArray(new String[locationSet.size()]), this);
				AlertDialog alert = builder.create();
				alert.show();
				break;
			case R.id.openingtime:
				Dialog dialog = new Dialog(this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.openingtime_dialog);
				dialog.show();
		}
		return true;
	}

	public void onClick(DialogInterface dialog, int locationId)
	{
		// set location
		Set<String> locationSet = this.context.getAvailableLocations().keySet();
		this.context.setCurrentLocation(locationSet.toArray(new String[locationSet.size()])[locationId]);
		
		getTabHost().setCurrentTabByTag(getTabHost().getCurrentTabTag());
		// redraw current tab
		this.redrawTab(getTabHost().getCurrentTabTag());
	}

	public void onTabChanged(String tabId)
	{
		for (Date date : this.context.getAvailableDates())
			if (String.valueOf(date.getDay()).equals(tabId))
				this.context.setCurrentDate(date);

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
