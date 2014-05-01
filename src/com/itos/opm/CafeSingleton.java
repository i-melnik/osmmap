package com.itos.opm;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itos.opm.data.Cafe;
import com.itos.opm.database.Database;

public class CafeSingleton
{

	private static final CafeSingleton instance = new CafeSingleton();

	private CafeSingleton()
	{

	}

	public static CafeSingleton getInstance()
	{
		if (instance == null)
		{
			return new CafeSingleton();
		}
		return instance;
	}

	public Long insertCafe(final SQLiteDatabase database, Cafe cafe)
	{
		return database.insert(Database.CAFE_TABLE_NAME, null, cafe.toSQLite());
	}

	public List<Cafe> getAllCafes(final SQLiteDatabase database)
	{
		final Cursor cursor = database.query(Database.CAFE_TABLE_NAME, null, null, null, null, null, null);
		List<Cafe> messages = new ArrayList<Cafe>();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			Cafe message = new Cafe().fromSQLite(cursor);
			messages.add(message);
		}
		return messages;
	}
}
