package de.najidev.mensaupb.entity;

import java.text.*;
import java.util.*;

import android.annotation.*;
import de.najidev.mensaupb.helper.*;

public class MenuRepository {

	protected Context context;
	protected DatabaseHelper databaseHelper;

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("nls")
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	protected List<Menu> menus = new ArrayList<Menu>();

	public MenuRepository(final Context context,
			final DatabaseHelper databaseHelper) {
		this.context = context;
		this.databaseHelper = databaseHelper;
		menus = databaseHelper.getMenus();
	}

	public List<Menu> getMenus(final String location, final Date date) {
		final List<Menu> list = new ArrayList<Menu>();

		for (final Menu menu : menus) {
			final String formattedReqeuestedDate = dateFormat.format(date);
			final String formattedMenuDate = dateFormat.format(menu.getDate());
			final boolean datesAreEqual = formattedReqeuestedDate
					.equals(formattedMenuDate);
			final boolean locationsAreEqual = location.equals(menu
					.getLocation());

			if (datesAreEqual && locationsAreEqual) {
				list.add(menu);
			}
		}

		return list;
	}

	public boolean dataIsNotLocallyAvailable() {
		return !databaseHelper.menusAvailable(context.getAvailableDates()[0]);
	}

	/**
	 * Reset the menus of the MenuRepository instance to menus and persists them
	 * in the Database
	 * 
	 * @param menus
	 */
	public void persistMenus(final List<Menu> menus) {
		this.menus = menus;
		databaseHelper.persistMenus(menus);

	}
}