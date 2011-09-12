package de.najidev.mensaupb.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

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
	    		default:
	    			resId = -1;
	    	}

		    spec = tabHost.newTabSpec("tab"+date.getDay())
	    			.setIndicator(
	    				date.getDate() + "." + date.getMonth() + ".",
	    				res.getDrawable(resId)
	    			)
	    			.setContent(intent);

		    tabHost.addTab(spec);
	    }

	    tabHost.setCurrentTab(DateHelper.getInstance().getCurrentDateIndex());
	}
}
