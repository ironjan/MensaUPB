package de.najidev.mensaupb.helper;

import java.util.HashMap;

public class Configuration
{
	DatabaseHelper databaseHelper;
	
	HashMap<String, String> config = new HashMap<String, String>();
	
	public Configuration(DatabaseHelper databaseHelper)
	{
		this.databaseHelper = databaseHelper;
		this.config         = databaseHelper.getConfig();
	}

	public void setMondayLocation(String location)
	{
		this.setConfig("start_location_monday", location);
	}
	
	public String getMondayLocation()
	{
		return this.config.get("start_location_monday");
	}
	
	public void setTuesdayLocation(String location)
	{
		this.setConfig("start_location_tuesday", location);
	}
	
	public String getTuesdayLocation()
	{
		return this.config.get("start_location_tuesday");
	}
	
	public void setWednesdayLocation(String location)
	{

		this.setConfig("start_location_wednesday", location);
	}
	
	public String getWednesdayLocation()
	{
		return this.config.get("start_location_wednesday");
	}
	
	public void setThursdayLocation(String location)
	{
		this.setConfig("start_location_thursday", location);
	}
	
	public String getThursdayLocation()
	{
		return this.config.get("start_location_thursday");
	}
	
	public void setFridayLocation(String location)
	{
		this.setConfig("start_location_friday", location);
	}
	
	public String getFridayLocation()
	{
		return this.config.get("start_location_friday");
	}

	protected void setConfig(String key, String value)
	{
		this.config.put(key, value);
		this.databaseHelper.updateConfig(key, value);
	}
}