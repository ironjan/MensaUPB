package de.najidev.mensaupb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.najidev.mensaupb.helper.DateHelper;
import de.najidev.mensaupb.helper.XmlParser;

public class MenuRepository
{
	protected static MenuRepository instance = null;
	protected List<Menu> menus = new ArrayList<Menu>();

	public static MenuRepository getInstance()
	{
		if (instance == null)
			instance = new MenuRepository();

		return instance;
	}

	public List<Menu> getMenus(Date date)
	{
		if (menus.isEmpty())
			menus = XmlParser.getMenus(DateHelper.getInstance().getWeek());

		List<Menu> list = new ArrayList<Menu>();

		for (Menu menu : menus)
			if (menu.getDate().equals(date))
				list.add(menu);

		return list;
	}
}
