package de.najidev.mensaupb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu
{
	protected Date date;
	protected String name;
	protected String type;
	protected List<String> sides;

	public Menu()
	{
		sides = new ArrayList<String>();
	}

	public Menu(Date date, String name, String type)
	{
		setDate(date);
		setName(name);
		setType(type);
		sides = new ArrayList<String>();
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getSides()
	{
		return sides;
	}

	public void addSide(String side)
	{
		sides.add(side);
	}
}
