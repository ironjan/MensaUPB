package de.najidev.mensaupb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.najidev.mensaupb.helper.XmlParser;

public class MenuRepository
{
	protected static MenuRepository instance = null;
	

	public static MenuRepository getInstance()
	{
		if (instance == null)
			instance = new MenuRepository();

		return instance;
	}

	public List<Menu> getMenus(Date date)
	{
		XmlParser.getMenus(39);
		List<Menu> list = new ArrayList<Menu>();

		list.add(new Menu(new Date(), "Der Name", "Vegetarisch"));
		list.add(new Menu(new Date(), "Der Name", "Vegetarisch"));
		list.add(new Menu(new Date(), "Der Name", "Vegetarisch"));

		return list;
	}
}
