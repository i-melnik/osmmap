package com.itos.opm.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.itos.opm.CafeSingleton;
import com.itos.opm.data.Cafe;

public class Database
{
	private static final String DATABASE_NAME = "osmcafe";
	private static final int DATABASE_VERSION = 1;

	public static final String COLUMN_ID = "_id";

	// Table names
	public static final String CAFE_TABLE_NAME = "cafe";
	public static final String RESTAURANT_TABLE_NAME = "restaurant";

	// Column for cafe table
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_WORK_HOURS = "workhours";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_PHONE_NUMBER = "phonenumber";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_LATITUDE = "latitude";

	private SQLiteDatabase _database;
	private CafeSingleton cafe = CafeSingleton.getInstance();

	public Database(final Context context)
	{
		final File path = context.getDatabasePath(DATABASE_NAME);
		Boolean deleted = path.delete();
		if (!path.exists())
		{
			InputStream input = null;
			OutputStream output = null;
			try
			{
				// noinspection ResultOfMethodCallIgnored
				path.getParentFile().mkdirs();
				input = context.getAssets().open(DATABASE_NAME);
				output = new FileOutputStream(path, true);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buffer)) > 0)
				{
					output.write(buffer, 0, bytesRead);
				}
			}
			catch (final IOException exception)
			{
				exception.printStackTrace();
			}
			finally
			{
				try
				{
					if (null != input)
						input.close();
					if (null != output)
						output.close();
				}
				catch (IOException ignored)
				{
				}
			}
		}

		try
		{
			this._database = SQLiteDatabase.openDatabase(path.getCanonicalPath(), null, SQLiteDatabase.OPEN_READWRITE);
		}
		catch (IOException e)
		{
			this._database = null;
			e.printStackTrace();
		}
	}

	public List<Cafe> getAllCafes()
	{
		return cafe.getAllCafes(this._database);
	}

}
