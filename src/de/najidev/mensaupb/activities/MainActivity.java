package de.najidev.mensaupb.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

import java.util.Calendar;
import java.util.Date;

import roboguice.activity.RoboTabActivity;

import com.google.inject.Inject;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.dialogs.OpeningTimeDialog;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.DateHelper;

public class MainActivity extends RoboTabActivity
{
	@Inject
	MenuRepository repo;

	@Inject
	DateHelper dateHelper;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost();  // The activity TabHost
		TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		Intent intent = new Intent().setClass(this, DayActivity.class);
		int resId = 0;

		for (Date date : dateHelper.getDates())
		{
			// we use +1 here, as for monday: date.getDay() + 1 == Calendar.Monday
			// according to the docs, this shouldn't be... but it is....
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
					resId = R.drawable.ic_tab_fri;
					break;
			}

			spec = tabHost.newTabSpec("tab"+date.getDay())
					.setIndicator(
							date.getDate() + "." + (date.getMonth()) + ".",
							res.getDrawable(resId)
					)
					.setContent(intent);

			tabHost.addTab(spec);
		}
	}

	protected void onResume()
	{
		super.onResume();

		if (!repo.hasActualData())
			repo.fetchActualData();

		getTabHost().setCurrentTab(dateHelper.getCurrentDateIndex());
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
				// TODO
				break;
			case R.id.openingtime:
				new OpeningTimeDialog(this).show();
				break;
		}
		return true;
	}
}
