package com.itos.opm.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.itos.opm.database.Database;

public class Cafe
{
	private Long id;
	private String name;
	private String address;
	private String workHours;
	private String phoneNumber;
	private String description;
	private double latitude;
	private double longitude;

	public Cafe()
	{

	}

	public Cafe(String name, String address, String workHours, String phoneNumber, String description)
	{
		super();
		this.name = name;
		this.address = address;
		this.workHours = workHours;
		this.phoneNumber = phoneNumber;
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getWorkHours()
	{
		return workHours;
	}

	public void setWorkHours(String workHours)
	{
		this.workHours = workHours;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Long getId()
	{
		return id;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public ContentValues toSQLite()
	{
		final ContentValues values = new ContentValues();

		values.put(Database.COLUMN_NAME, this.name);
		values.put(Database.COLUMN_DESCRIPTION, this.description);
		values.put(Database.COLUMN_ADDRESS, this.address);
		values.put(Database.COLUMN_PHONE_NUMBER, this.phoneNumber);
		values.put(Database.COLUMN_WORK_HOURS, this.workHours);
		values.put(Database.COLUMN_LATITUDE, this.latitude);
		values.put(Database.COLUMN_LONGITUDE, this.longitude);

		return values;
	}

	public Cafe fromSQLite(final Cursor cursor)
	{
		this.id = cursor.getLong(cursor.getColumnIndex(Database.COLUMN_ID));
		this.name = cursor.getString(cursor.getColumnIndex(Database.COLUMN_NAME));
		this.description = cursor.getString(cursor.getColumnIndex(Database.COLUMN_DESCRIPTION));
		this.address = cursor.getString(cursor.getColumnIndex(Database.COLUMN_ADDRESS));
		this.workHours = cursor.getString(cursor.getColumnIndex(Database.COLUMN_WORK_HOURS));
		this.phoneNumber = cursor.getString(cursor.getColumnIndex(Database.COLUMN_PHONE_NUMBER));
		this.latitude = cursor.getDouble(cursor.getColumnIndex(Database.COLUMN_LATITUDE));
		this.longitude = cursor.getDouble(cursor.getColumnIndex(Database.COLUMN_LONGITUDE));

		return this;
	}
}
