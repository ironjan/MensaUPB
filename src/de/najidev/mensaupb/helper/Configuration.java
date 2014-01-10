package de.najidev.mensaupb.helper;

import org.slf4j.*;

import java.util.*;

public class Configuration {

	DatabaseHelper databaseHelper;

	HashMap<String, String> config = new HashMap<String, String>();

	private static final String TAG = Configuration.class.getSimpleName();
	private static final Logger LOGGER = LoggerFactory.getLogger(TAG);

	public Configuration(final DatabaseHelper databaseHelper) {
		if (LOGGER.isDebugEnabled()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Configuration({})", databaseHelper); //$NON-NLS-1$
			}

			this.databaseHelper = databaseHelper;
			config = databaseHelper.getConfig();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Constructed Configuration({})", databaseHelper); //$NON-NLS-1$
			}
		}
	}

	public void setMondayLocation(final String location) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setMondayLocation({})", location); //$NON-NLS-1$
		}

		setConfig("start_location_monday", location);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setMondayLocation({}) -> VOID", location); //$NON-NLS-1$
		}
	}

	public String getMondayLocation() {
		String mondayLocation = config.get("start_location_monday");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getMondayLocation() -> {}", mondayLocation); //$NON-NLS-1$
		}

		return mondayLocation;
	}

	public void setTuesdayLocation(final String location) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setTuesdayLocation({})", location); //$NON-NLS-1$
		}
		setConfig("start_location_tuesday", location);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setTuesdayLocation({}) -> VOID", location); //$NON-NLS-1$
		}
	}

	public String getTuesdayLocation() {
		String tuesdayLocation = config.get("start_location_tuesday");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getTuesdayLocation() -> {}", tuesdayLocation); //$NON-NLS-1$
		}
		return tuesdayLocation;
	}

	public void setWednesdayLocation(final String location) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setWednesdayLocation({})", location); //$NON-NLS-1$
		}
		setConfig("start_location_wednesday", location);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setWednesdayLocation({}) -> VOID", location); //$NON-NLS-1$
		}
	}

	public String getWednesdayLocation() {
		String wednesdayLocation = config.get("start_location_wednesday");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getWednesdayLocation() -> {}", wednesdayLocation); //$NON-NLS-1$
		}
		return wednesdayLocation;
	}

	public void setThursdayLocation(final String location) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setThursdayLocation({})", location); //$NON-NLS-1$
		}
		setConfig("start_location_thursday", location);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setThursdayLocation({}) -> VOID", location); //$NON-NLS-1$
		}
	}

	public String getThursdayLocation() {
		String thursdayLocation = config.get("start_location_thursday");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getThursdayLocation() -> {}", thursdayLocation); //$NON-NLS-1$
		}
		return thursdayLocation;
	}

	public void setFridayLocation(final String location) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setFridayLocation({})", location); //$NON-NLS-1$
		}
		setConfig("start_location_friday", location);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setFridayLocation({}) -> VOID", location); //$NON-NLS-1$
		}
	}

	public String getFridayLocation() {
		String fridayLocation = config.get("start_location_friday"); //$NON-NLS-1$
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getFridayLocation() -> {}", fridayLocation); //$NON-NLS-1$
		}
		return fridayLocation;
	}

	protected void setConfig(final String key, final String value) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setConfig({},{})", key, value); //$NON-NLS-1$
		}
		config.put(key, value);
		databaseHelper.updateConfig(key, value);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setConfig({},{}) -> VOID", key, value); //$NON-NLS-1$
		}
	}

	@Override
	public String toString() {
		return String.format("Configuration [databaseHelper=%s,config=%s]", //$NON-NLS-1$
				databaseHelper, config);
	}
}