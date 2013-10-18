package de.najidev.mensaupb.helper;

import java.util.*;

public class Configuration {

	DatabaseHelper databaseHelper;

	HashMap<String, String> config = new HashMap<String, String>();

	public Configuration(final DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
		config = databaseHelper.getConfig();
	}

	public void setMondayLocation(final String location) {
		setConfig("start_location_monday", location);
	}

	public String getMondayLocation() {
		return config.get("start_location_monday");
	}

	public void setTuesdayLocation(final String location) {
		setConfig("start_location_tuesday", location);
	}

	public String getTuesdayLocation() {
		return config.get("start_location_tuesday");
	}

	public void setWednesdayLocation(final String location) {

		setConfig("start_location_wednesday", location);
	}

	public String getWednesdayLocation() {
		return config.get("start_location_wednesday");
	}

	public void setThursdayLocation(final String location) {
		setConfig("start_location_thursday", location);
	}

	public String getThursdayLocation() {
		return config.get("start_location_thursday");
	}

	public void setFridayLocation(final String location) {
		setConfig("start_location_friday", location);
	}

	public String getFridayLocation() {
		return config.get("start_location_friday");
	}

	protected void setConfig(final String key, final String value) {
		config.put(key, value);
		databaseHelper.updateConfig(key, value);
	}
}