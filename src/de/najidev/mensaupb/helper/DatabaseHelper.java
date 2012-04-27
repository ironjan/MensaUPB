package de.najidev.mensaupb.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME = "mensaupb";
	public static final int DATABASE_VERSION = 1;

	public DatabaseHelper(final Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE menu (title TEXT, name TEXT, type TEXT, sides TEXT, location TEXT, date TEXT)");
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS menu");
		onCreate(db);
	}
}