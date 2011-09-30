package de.najidev.mensaupb.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.inject.Singleton;

@Singleton
public class DateHelper
{
	protected List<Date> dates;
	protected int currentDateIndex;
	protected int week;

	public DateHelper()
	{
		final Calendar gc = GregorianCalendar.getInstance();
		gc.clear(Calendar.HOUR_OF_DAY);
		gc.clear(Calendar.MINUTE);
		gc.clear(Calendar.SECOND);
		gc.clear(Calendar.MILLISECOND);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);

		switch (gc.get(Calendar.DAY_OF_WEEK))
		{
		// if you're working on friday and the xml for next week is online,
		// following case will help you testing
		/*
			case Calendar.FRIDAY:
				gc.add(Calendar.DAY_OF_MONTH, 1);
		 */
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
			dates.add(gc.getTime());
			gc.add(Calendar.DAY_OF_WEEK, 1);
		}
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
