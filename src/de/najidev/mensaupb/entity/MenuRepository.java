package de.najidev.mensaupb.entity;

import java.text.*;
import java.util.*;

import org.slf4j.*;

import android.annotation.*;
import de.najidev.mensaupb.helper.*;

public class MenuRepository {

	protected Context context;
	protected DatabaseHelper databaseHelper;

	Logger LOGGER = LoggerFactory.getLogger(MenuRepository.class
			.getSimpleName());

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("nls")
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	protected List<Menu> menus = new ArrayList<Menu>();

	public MenuRepository(final Context context,
			final DatabaseHelper databaseHelper) {
		Object[] params = { context, databaseHelper };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}({},{})", MenuRepository.class.getSimpleName(),
					params);
		}
		this.context = context;
		this.databaseHelper = databaseHelper;
		menus = databaseHelper.getMenus();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created {}({},{})",
					MenuRepository.class.getSimpleName(), params);
		}
	}

	public List<Menu> getMenus(final String location, final Date date) {
		Object[] params = { location, date };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getMenus({},{})", params);
		}
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

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getMenus({},{}) -> {}", params, list);
		}
		return list;
	}

	public boolean dataIsNotLocallyAvailable() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("dataIsNotLocallyAvailable()");
		}
		boolean b = !databaseHelper
				.menusAvailable(context.getAvailableDates()[0]);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("dataIsNotLocallyAvailable() -> {}", b);
		}
		return b;
	}

	/**
	 * Reset the menus of the MenuRepository instance to menus and persists them
	 * in the Database
	 * 
	 * @param menus
	 */
	public void persistMenus(final List<Menu> menus) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("persistMenus({})", menus);
		}
		
		this.menus = menus;
		databaseHelper.persistMenus(menus);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("persistMenus({}) -> VOID", menus);
		}
	}

	@Override
	public String toString() {
		return "MenuRepository [context=" + context + ", databaseHelper="
				+ databaseHelper + ", dateFormat=" + dateFormat + ", menus="
				+ menus + "]";
	}
}