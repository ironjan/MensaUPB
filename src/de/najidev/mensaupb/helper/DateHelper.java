package de.najidev.mensaupb.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateHelper
{
	protected static DateHelper instance;
	protected List<Date> dates;
	protected int currentDateIndex;
	protected int week;

	private DateHelper()
	{
	    Calendar gc = GregorianCalendar.getInstance();

	    switch(gc.get(Calendar.DAY_OF_WEEK))
	    {
	    	case Calendar.SATURDAY:
	    		gc.add(Calendar.DAY_OF_MONTH, 1);
	    	case Calendar.SUNDAY:
	    		gc.add(Calendar.DAY_OF_MONTH, 1);
	    		break;
	    }
	    week = gc.get(Calendar.WEEK_OF_YEAR);

	    currentDateIndex = gc.get(Calendar.DAY_OF_WEEK) - 2;

	    while (gc.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
	    	gc.add(Calendar.DAY_OF_WEEK, -1);

	    dates = new ArrayList<Date>();
	    for (int i = 0; i < 5; i++)
	    {
	    	dates.add(new Date(
		    	gc.get(Calendar.YEAR),
		    	gc.get(Calendar.MONTH),
		    	gc.get(Calendar.DAY_OF_MONTH)
	    	));
		    gc.add(Calendar.DAY_OF_WEEK, 1);
	    }
	}

	public static DateHelper getInstance()
	{
		if (null == instance)
			instance = new DateHelper();

		return instance;
	}

	public List<Date> getDates()
	{
		return dates;
	}

	public int getCurrentDateIndex()
	{
		return currentDateIndex;
	}

	public int getWeek()
	{
		return this.week;
	}
}
