package de.najidev.mensaupb.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	}

	public List<Menu> getMenus(String location, Date date)
	{
		if (menus.isEmpty())
		{
			SQLiteDatabase database = databaseHelper.getReadableDatabase();
			Cursor c = database.query("menu", new String[] { "title", "name", "type", "sides", "location", "date" },
					null, null, null, null, null, null);

			Menu menu;
			int title     = c.getColumnIndex("title");
			int name      = c.getColumnIndex("name");
			int type      = c.getColumnIndex("type");
			int sides     = c.getColumnIndex("sides");
			int locationI = c.getColumnIndex("location");
			int dateI     = c.getColumnIndex("date");

			c.moveToFirst();
			while (!c.isAfterLast())
			{
				menu = new Menu();
				menu.setTitle(c.getString(title));
				menu.setName(c.getString(name));
				menu.setType(c.getString(type));
				menu.addSide(c.getString(sides));
				try {
					menu.setDate(this.dateFormat.parse(c.getString(dateI)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				menu.setLocation(c.getString(locationI));

				menus.add(menu);
				c.moveToNext();
			}

			database.close();
		}

		List<Menu> list = new ArrayList<Menu>();

		for (Menu menu : menus)
			if (dateFormat.format(date).equals(dateFormat.format(menu.getDate())) && location.equals(menu.getLocation()))
				list.add(menu);

				return list;
	}

	public boolean dataIsLocallyAvailable()
	{
		int lines = databaseHelper.getReadableDatabase()
				.rawQuery(
						"SELECT name FROM menu WHERE date=? LIMIT 1",
						new String[] { dateFormat.format(this.context.getAvailableDates()[0]) }
						)
						.getCount();

		return (lines == 1);
	}

	/**
	 * Reset the menus of the MenuRepository instance to menus and persists them in the Database
	 * @param menus
	 */
	public void persistMenus(List<Menu> menus)
	{
		this.menus = menus;

		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		database.beginTransaction();
		database.delete("menu", null, null);

		ContentValues values;
		for (Menu menu : menus)
		{
			values = new ContentValues();
			values.put("title", menu.getTitle());
			values.put("name", menu.getName());
			values.put("type", menu.getType());
			values.put("location", menu.getLocation());
			values.put("date", dateFormat.format(menu.getDate()));
			values.put("sides", menu.getSides());
			database.insert("menu", null, values);
		}

		database.setTransactionSuccessful();
		database.endTransaction();
		database.close();	
	}
}