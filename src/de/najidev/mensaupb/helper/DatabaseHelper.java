package de.najidev.mensaupb.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.najidev.mensaupb.entity.Menu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME = "mensaupb";
	public static final int DATABASE_VERSION = 2;
	
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public DatabaseHelper(final Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db)
	{
		db.beginTransaction();
		db.execSQL("CREATE TABLE IF NOT EXISTS menu (title TEXT, name TEXT, type TEXT, sides TEXT, location TEXT, date TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS config (key TEXT, value TEXT)");
		

		String[] keys = new String[] {
				"start_location_monday", "start_location_tuesday", "start_location_wednesday", "start_location_thursday", "start_location_friday"
		};
		
		for (String key : keys)
			db.execSQL("INSERT INTO config (key, value) VALUES (\"" + key + "\", \"Mensa\")");
				
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS menu");
		db.execSQL("DROP TABLE IF EXISTS config");
		onCreate(db);
	}

	public HashMap<String, String> getConfig()
	{
		HashMap<String, String> config = new HashMap<String, String>();

		SQLiteDatabase database = getWritableDatabase();
		
		Cursor c = database.query("config", new String[] { "key", "value" }, null, null, null, null, null);

		int key   = c.getColumnIndex("key");
		int value = c.getColumnIndex("value");

		c.moveToFirst();
		while(!c.isAfterLast())
		{
			config.put(c.getString(key), c.getString(value));
			c.moveToNext();
		}
		
		database.close();

		return config;
	}

	public List<Menu> getMenus()
	{
		List<Menu> list = new ArrayList<Menu>();
		
		SQLiteDatabase database = getWritableDatabase();
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

			list.add(menu);
			c.moveToNext();
		}

		database.close();
		
		return list;
	}
	
	public void persistMenus(List<Menu> menus)
	{
		SQLiteDatabase database = getWritableDatabase();
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

	public boolean menusAvailable(Date date)
	{
		SQLiteDatabase database = getReadableDatabase();

		int lines = database.rawQuery("SELECT name FROM menu WHERE date=? LIMIT 1", new String[] { dateFormat.format(date) }).getCount();
		
		database.close();

		return (lines == 1);
	}

	public void updateConfig(String key, String value)
	{
		SQLiteDatabase database = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("key", key);
		values.put("value", value);
		database.update("config", values, "key = ?", new String[] { key });
		database.close();
	}
}