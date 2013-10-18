package de.najidev.mensaupb.entity;

import java.util.*;

public class Menu {

	protected Date date;
	protected String title;
	protected String name;
	protected String type;
	protected String sides;
	protected String location;

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getSides() {
		return sides;
	}

	public void addSide(final String side) {
		if (null == sides) {
			sides = side;
		}
		else {
			sides += ", " + side;
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public boolean isTagesTipp() {
		if (null != title && title.equals("Tages - Tipp")) {
			return true;
		}
		return false;
	}
}