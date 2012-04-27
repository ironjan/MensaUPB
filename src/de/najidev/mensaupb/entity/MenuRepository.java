package de.najidev.mensaupb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.najidev.mensaupb.ApplicationContext;
import de.najidev.mensaupb.helper.DatabaseHelper;
import de.najidev.mensaupb.helper.DownloadHelper;
import de.najidev.mensaupb.helper.XmlParser;

@Singleton
public class MenuRepository
{
	@Inject
	protected XmlParser xmlParser;
	
	
	@Inject
	protected DownloadHelper downloadHelper;
	
	@Inject
	protected static Provider<Context> contextProvider;
	protected Context context = contextProvider.get();
	
	@Inject
	protected ApplicationContext applicationContext;

	protected DatabaseHelper databaseHelper;

	protected List<Menu> menus = new ArrayList<Menu>();

	public MenuRepository()
	{
		databaseHelper = new DatabaseHelper(context);
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
				menu.setDate(new Date(c.getLong(dateI)));
				menu.setLocation(c.getString(locationI));

				menus.add(menu);
				c.moveToNext();
			}

			database.close();
		}

		List<Menu> list = new ArrayList<Menu>();

		for (Menu menu : menus)
			if (date.getDate() == menu.getDate().getDate() && location.equals(menu.getLocation()))
				list.add(menu);

				return list;
	}

	public boolean hasActualData()
	{
		int lines = databaseHelper.getReadableDatabase()
				.rawQuery(
						"SELECT name FROM menu WHERE date=? LIMIT 1",
						new String[] { String.valueOf(this.applicationContext.getAvailableDates()[0].getTime()) }
						)
						.getCount();

		return (lines == 1);
	}

	public void fetchActualData()
	{
		List<Menu> menus = new ArrayList<Menu>();
		List<Menu> tmp;

		for (String location : this.applicationContext.getAvailableLocations().values())
		{
			tmp = xmlParser.getMenus(
					downloadHelper.downloadFile("http://www.studentenwerk-pb.de/fileadmin/xml/" + location + ".xml")
					);

			for (Menu menu : tmp)
			{
				menu.setLocation(location);
				menus.add(menu);
			}
		}

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
			values.put("date", menu.getDate().getTime());
			values.put("sides", menu.getSides());
			database.insert("menu", null, values);
		}

		database.setTransactionSuccessful();
		database.endTransaction();
		database.close();
	}
}