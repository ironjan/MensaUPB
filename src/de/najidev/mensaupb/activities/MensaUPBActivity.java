package de.najidev.mensaupb.activities;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import de.najidev.mensaupb.R;
import de.najidev.mensaupb.helper.DateHelper;

public class MensaUPBActivity extends TabActivity
{
	
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent = new Intent().setClass(this, DayActivity.class);
	    int resId = 0;

	    for (Date date : DateHelper.getInstance().getDates())
	    {
	    	switch (date.getDay())
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
	    				date.getDate() + "." + (date.getMonth() + 1) + ".",
	    				res.getDrawable(resId)
	    			)
	    			.setContent(intent);

		    tabHost.addTab(spec);
	    }
	}

	protected void onResume()
	{
		super.onResume();

	    getTabHost().setCurrentTab(DateHelper.getInstance().getCurrentDateIndex());
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
				
				break;
			case R.id.openingtime:
				Dialog dialog = new Dialog(this);
				//dialog.setTitle("Öffnungszeiten");
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    	dialog.setContentView(R.layout.openingtime_dialog);
		    	dialog.show();
				break;
		}
		return true;
	}
}
