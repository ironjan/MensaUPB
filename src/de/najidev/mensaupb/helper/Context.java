package de.najidev.mensaupb.helper;

import java.sql.Date;
import java.util.*;

import org.slf4j.*;

public class Context {

	protected String currentLocation = "Mensa";

	private static final String TAG = Context.class.getSimpleName();
	private static final Logger LOGGER = LoggerFactory.getLogger(TAG);

	protected HashMap<String, String> availableLocations = new HashMap<String, String>() {

		private static final long serialVersionUID = 98693404924759613L;
		{
			put("Mensa", "mensa");
			put("Pub", "gownsmenspub");
			put("Hotspot", "palmengarten");
		}
	};
	protected Date[] availableDates = new Date[5];

	public Context(final Configuration config) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Context({})", config);

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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Constructed: Context({})", config);
		}
	}

	public Date[] getAvailableDates() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getAvailableDates() -> {}",
					Arrays.deepToString(availableDates));
		}
		return availableDates;
	}

	public HashMap<String, String> getAvailableLocations() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getAvailableLocations() -> {}", availableLocations);
		}
		return availableLocations;
	}

	public String getCurrentLocation() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getCurrentLocation() -> {}", currentLocation);
		}
		return availableLocations.get(currentLocation);
	}

	public void setCurrentLocation(final String location) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setCurrentLocation({})", location);
		}
		currentLocation = location;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setCurrentLocation({}) -> VOID", location);
		}
	}

	public String getCurrentLocationTitle() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getCurrentLocationTitle() -> {}", currentLocation);
		}
		return currentLocation;
	}

	public String[] getLocationTitle() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getLocationTitle()");
		}
		final Set<String> locationSet = ServiceContainer.getInstance()
				.getContext().getAvailableLocations().keySet();
		String[] result = locationSet.toArray(new String[locationSet.size()]);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("getLocationTitle() -> {}",
					Arrays.deepToString(result));
		return result;
	}

	@Override
	public String toString() {
		return "Context [currentLocation=" + currentLocation
				+ ", availableLocations=" + availableLocations
				+ ", availableDates=" + Arrays.toString(availableDates) + "]";
	}
}