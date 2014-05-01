package com.itos.opm;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.itos.opm.screen.MapScreen;

public class MainActivity extends FragmentActivity
{

	private static MapScreen mapScreen;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		mapScreen = (MapScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
		final FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
		transaction.show(new MapScreen());
		transaction.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

	@Override
	protected void onResume()
	{
		MainApplication.activityCreated(this);
		super.onResume();
	}

	public static void changeSource()
	{
		mapScreen.changeSource();
	}

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev)
	// {
	// int actionType = ev.getAction();
	// switch (actionType) {
	// case MotionEvent.ACTION_UP:
	// Projection proj = mapScreen.getMapView().getProjection();
	// IGeoPoint loc = proj.fromPixels((int) ev.getX(), (int) ev.getY());
	// String longitude = Double.toString(((double) loc.getLongitudeE6()) /
	// 1000000);
	// String latitude = Double.toString(((double) loc.getLatitudeE6()) /
	// 1000000);
	// Toast toast = Toast.makeText(getApplicationContext(), "Longitude: " +
	// longitude + " Latitude: " + latitude,
	// Toast.LENGTH_LONG);
	// toast.show();
	//
	// }
	// return super.dispatchTouchEvent(ev);
	// }
}
