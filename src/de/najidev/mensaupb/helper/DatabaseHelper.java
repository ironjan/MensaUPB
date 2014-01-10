package de.najidev.mensaupb.helper;

import android.annotation.*;
import android.content.*;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;

import org.slf4j.*;

import java.text.*;
import java.util.*;

import de.najidev.mensaupb.entity.*;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("nls")
public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "mensaupb";
	public static final int DATABASE_VERSION = 2;

	protected final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final String TAG = DatabaseHelper.class.getSimpleName();
	private static final Logger LOGGER = LoggerFactory.getLogger(TAG);

	public DatabaseHelper(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Object[] superParams = { context, DATABASE_NAME, null, DATABASE_VERSION };
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Created DatabaseHelper({})", context);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("\t super({},{},{},{})", superParams);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("onCreate({})", db);
		db.beginTransaction();
		db.execSQL("CREATE TABLE IF NOT EXISTS menu (title TEXT, name TEXT, type TEXT, sides TEXT, location TEXT, date TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS config (key TEXT, value TEXT)");

		final String[] keys = new String[] { "start_location_monday",
				"start_location_tuesday", "start_location_wednesday",
				"start_location_thursday", "start_location_friday" };

		for (final String key : keys) {
			db.execSQL("INSERT INTO config (key, value) VALUES (\"" + key
					+ "\", \"Mensa\")");
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("onCreate({}) -> VOID", db);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		Object[] params = { db, oldVersion, newVersion };
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("onUpgrade({},{},{})", params);
		db.execSQL("DROP TABLE IF EXISTS menu");
		db.execSQL("DROP TABLE IF EXISTS config");
		onCreate(db);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("onUpgrade({},{},{}) -> VOID", params);
	}

	public HashMap<String, String> getConfig() {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("getConfig()");
		final HashMap<String, String> config = new HashMap<String, String>();

		final SQLiteDatabase database = getWritableDatabase();

		final Cursor c = database.query("config",
				new String[] { "key", "value" }, null, null, null, null, null);

		final int key = c.getColumnIndex("key");
		final int value = c.getColumnIndex("value");

		c.moveToFirst();
		while (!c.isAfterLast()) {
			config.put(c.getString(key), c.getString(value));
			c.moveToNext();
		}

		c.close();
		database.close();

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("getConfig() -> {}", config);
		return config;
	}

	public List<Menu> getMenus() {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("getMenus()");
		final List<Menu> list = new ArrayList<Menu>();

		final SQLiteDatabase database = getWritableDatabase();
		final Cursor c = database.query("menu", new String[] { "title", "name",
				"type", "sides", "location", "date" }, null, null, null, null,
				null, null);

		Menu menu;
		final int title = c.getColumnIndex("title");
		final int name = c.getColumnIndex("name");
		final int type = c.getColumnIndex("type");
		final int sides = c.getColumnIndex("sides");
		final int locationI = c.getColumnIndex("location");
		final int dateI = c.getColumnIndex("date");

		c.moveToFirst();
		while (!c.isAfterLast()) {
			menu = new Menu();
			menu.setTitle(c.getString(title));
			menu.setName(c.getString(name));
			menu.setType(c.getString(type));
			menu.addSide(c.getString(sides));
			try {
				menu.setDate(dateFormat.parse(c.getString(dateI)));
			} catch (final ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menu.setLocation(c.getString(locationI));

			list.add(menu);
			c.moveToNext();
		}

		c.close();
		database.close();

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("getMenus() -> {}",
					Arrays.deepToString(list.toArray()));
		return list;
	}

	public void persistMenus(final List<Menu> menus) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("persistMenus({})", menus);
		final SQLiteDatabase database = getWritableDatabase();
		database.beginTransaction();
		database.delete("menu", null, null);

		ContentValues values;
		for (final Menu menu : menus) {
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
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("persistMenus({}) -> VOID",
					Arrays.deepToString(menus.toArray()));
	}

	public boolean menusAvailable(final Date date) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("menusAvailable({})", date);
		final SQLiteDatabase database = getReadableDatabase();

		final int lines = database.rawQuery(
				"SELECT name FROM menu WHERE date=? LIMIT 1",
				new String[] { dateFormat.format(date) }).getCount();

		database.close();

		boolean result = (lines == 1);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("menusAvailable({}) -> {}", date, result);
		return result;
	}

	public void updateConfig(final String key, final String value) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("updateConfig({},{})", key, value);
		final SQLiteDatabase database = getWritableDatabase();

		final ContentValues values = new ContentValues();
		values.put("key", key);
		values.put("value", value);
		database.update("config", values, "key = ?", new String[] { key });
		database.close();
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("updateConfig({},{}) -> VOID", key, value);
	}

	@Override
	public String toString() {
		return "DatabaseHelper [dateFormat=" + dateFormat + "]";
	}
}