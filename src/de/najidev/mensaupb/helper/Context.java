package de.najidev.mensaupb.helper;

import java.sql.Date;
import java.util.*;

public class Context {

	protected String currentLocation = "Mensa";

	protected final HashMap<String, String> availableLocations = new HashMap<String, String>() {

		private static final long serialVersionUID = 98693404924759613L;
		{
			put("Mensa", "mensa");
			put("Pub", "gownsmenspub");
			put("Hotspot", "palmengarten");
		}
	};
	protected Date[] availableDates = new Date[5];

	public Context(final Configuration config) {
		final Calendar calendar = Calendar.getInstance();

		// adjust calendar to point to first day of week
		{
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			// if you're working on friday and the xml for next week is online,
			// following case will help you testing
			/*
			 * case Calendar.FRIDAY: calendar.add(Calendar.DAY_OF_MONTH, 1);
			 */
			case Calendar.SATURDAY:
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			case Calendar.SUNDAY:
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				break;
			}

			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				calendar.add(Calendar.DAY_OF_WEEK, -1);
			}
		}

		for (int i = 0; i < availableDates.length; i++) {
			availableDates[i] = new Date(calendar.getTimeInMillis());
			calendar.add(Calendar.DATE, 1);
		}
	}

	public Date[] getAvailableDates() {
		return availableDates;
	}

	public HashMap<String, String> getAvailableLocations() {
		return availableLocations;
	}

	public String getCurrentLocation() {
		return availableLocations.get(currentLocation);
	}

	public void setCurrentLocation(final String location) {
		currentLocation = location;
	}

	public String getCurrentLocationTitle() {
		return currentLocation;
	}

	public String[] getLocationTitle() {
		final Set<String> locationSet = ServiceContainer.getInstance()
				.getContext().getAvailableLocations().keySet();
		return locationSet.toArray(new String[locationSet.size()]);
	}
}