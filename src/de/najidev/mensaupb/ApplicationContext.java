package de.najidev.mensaupb;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.google.inject.Singleton;

@Singleton
public class ApplicationContext
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

	
	public ApplicationContext()
	{
		this.currentDate = new Date();
		
		Calendar calendar = Calendar.getInstance();
		this.firstDayOfWeek = new Date();
		
		// adjust calendar to point to first day of week
		if (2 < calendar.get(Calendar.DAY_OF_WEEK))
			while (2 != calendar.get(Calendar.DAY_OF_WEEK))
				calendar.add(Calendar.DATE, -1);
		else
			while (2 != calendar.get(Calendar.DAY_OF_WEEK))
				calendar.add(Calendar.DATE, +1);
		
		this.firstDayOfWeek = calendar.getTime();
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
