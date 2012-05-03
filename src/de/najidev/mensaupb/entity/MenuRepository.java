package de.najidev.mensaupb.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.najidev.mensaupb.helper.Context;
import de.najidev.mensaupb.helper.DatabaseHelper;

public class MenuRepository
{
	protected Context context;
	protected DatabaseHelper databaseHelper;

	protected final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	protected List<Menu> menus = new ArrayList<Menu>();

	public MenuRepository(Context context, DatabaseHelper databaseHelper)
	{
		this.context        = context;
		this.databaseHelper = databaseHelper;
		
		this.menus          = databaseHelper.getMenus();
	}

	public List<Menu> getMenus(String location, Date date)
	{
		List<Menu> list = new ArrayList<Menu>();

		for (Menu menu : this.menus)
			if (dateFormat.format(date).equals(dateFormat.format(menu.getDate())) && location.equals(menu.getLocation()))
				list.add(menu);

				return list;
	}

	public boolean dataIsLocallyAvailable()
	{
		return databaseHelper.menusAvailable(this.context.getAvailableDates()[0]);
	}

	/**
	 * Reset the menus of the MenuRepository instance to menus and persists them in the Database
	 * @param menus
	 */
	public void persistMenus(List<Menu> menus)
	{
		this.menus = menus;
		this.databaseHelper.persistMenus(this.menus);

	}
}