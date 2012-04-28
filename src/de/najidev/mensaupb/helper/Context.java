package de.najidev.mensaupb.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Context
{	
	protected Date firstDayOfWeek = null;
	protected Date currentDate    = null;
	
	protected String currentLocation = "Mensa";

	protected final HashMap<String, String> availableLocations = new HashMap<String, String>() {
		private static final long serialVersionUID = 98693404924759613L;
		{
			put("Mensa", "mensa");
			put("Gownsmen's Pub", "gownsmenspub");
			put("Palmengarten", "palmengarten");
		}
	};
	protected Date[] availableDates = new Date[5];

	
	public Context()
	{
		Calendar calendar = Calendar.getInstance();
		
		this.firstDayOfWeek = new Date();
		
		// adjust calendar to point to first day of week
		{
			switch (calendar.get(Calendar.DAY_OF_WEEK))
			{
				// if you're working on friday and the xml for next week is online,
				// following case will help you testing
				/*
					case Calendar.FRIDAY:
						calendar.add(Calendar.DAY_OF_MONTH, 1);
				 */
				case Calendar.SATURDAY:
					calendar.add(Calendar.DAY_OF_MONTH, 1);
				case Calendar.SUNDAY:
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					break;
			}
			
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
				calendar.add(Calendar.DAY_OF_WEEK, -1);	
		}
		
		this.firstDayOfWeek = calendar.getTime();
		this.currentDate    = calendar.getTime();
	}
	
	
	public void setCurrentDate(Date currentDate)
	{
		this.currentDate = currentDate;
	}
	
	public Date getCurrentDate()
	{
		return this.currentDate;
	}

	public Date[] getAvailableDates()
	{
		// prepare calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.firstDayOfWeek);

		// create array of dates
		Date[] dates = new Date[5];
		for (int i = 0; i < dates.length; i++)
		{
			dates[i] = calendar.getTime();
			calendar.add(Calendar.DATE, 1);
		}
		
		return dates;
	}

	public HashMap<String, String> getAvailableLocations()
	{
		return this.availableLocations;
	}
	
	public String getCurrentLocation()
	{ 
		return this.availableLocations.get(this.currentLocation);
	}
	
	public void setCurrentLocation(String location)
	{
		this.currentLocation = location;
	}
}
