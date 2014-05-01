package com.itos.opm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;

import com.itos.opm.database.Database;

public class MainApplication extends Application
{

	private static AlarmManager alarmManager;
	private static MainApplication instance;
	private static Activity activity;
	private static Database database;

	@Override
	public void onCreate()
	{
		super.onCreate();

		instance = this;
		initKeepConnection();
		MainApplication.database = new Database(this);
	}

	private void initKeepConnection()
	{
		// alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// final Intent service = new Intent(this, TileService.class);
		// final PendingIntent pending = PendingIntent.getService(this, 0,
		// service, 0);
		// alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
		// System.currentTimeMillis() + 10000, 30000, pending);
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}

	public static MainApplication getInstance()
	{
		return instance;
	}

	public static void activityCreated(Activity activity)
	{
		MainApplication.activity = activity;
	}

	public static Activity getActivity()
	{
		return MainApplication.activity;
	}

	public static AlarmManager getAlarmManager()
	{
		return alarmManager;
	}

	public static Database getDatabase()
	{
		return database;
	}

	public static void setDatabase(Database database)
	{
		MainApplication.database = database;
	}

}
