package com.itos.opm.screen;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.tileprovider.modules.MapTileDownloader;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.itos.opm.MainApplication;
import com.itos.opm.R;
import com.itos.opm.data.Cafe;

public class MapScreen extends Fragment
{

	private MapView mapView;
	private MyOverlay myOverlays;
	private GeoPoint currentLocation;
	private MyLocationListener locationListener;

	private ImageButton findMe;

	private LocationManager locationManager;

	private MapController mapController;
	// private static MapTileDownloader dw = new MapTileDownloader(new
	// XYTileSource("FietsRegionaal", null, 0, 16, 256, ".png",
	// "http://tile.openstreetmap.org"));

	public static String USER_AGENT = null;
	public static String ACCEPT = "text/html, image/png, image/jpeg, image/gif, */*";

	public MapScreen()
	{

	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.map_screen, container, false);
		this.mapView = (MapView) view.findViewById(R.id.map_view);
		findMe = (ImageButton) view.findViewById(R.id.imageButton1);
		findMe.setOnClickListener(findMeListener);
		mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
		mapController = new MapController(mapView);
		mapView.setBuiltInZoomControls(true);
		mapController.setZoom(12);
		mapController.setCenter(new GeoPoint(53.892605 * 1E6, 27.559547 * 1E6));

		TileSourceFactory.addTileSource(TileSourceFactory.MAPQUESTOSM);

		MapTileModuleProviderBase[] baseProvider = new MapTileModuleProviderBase[2];
		baseProvider[1] = new MapTileDownloader(TileSourceFactory.MAPQUESTOSM);

		putMyItemizedOverlay(new GeoPoint(53.892605 * 1E6, 27.559547 * 1E6));

		// GeocoderNominatim geocoder = new GeocoderNominatim(getActivity());
		// try
		// {
		// List<Address> address =
		// geocoder.getFromLocationName("Minsk Nemiga 3", 1);
		// address.get(0);
		// }
		// catch (IOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		locationListener = new MyLocationListener();
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		return view;
	}

	public static String getTileNumber(final double lat, final double lon, final int zoom)
	{
		xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		ytile = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2
				* (1 << zoom));
		return ("/" + zoom + "/" + xtile + "/" + ytile);
	}

	protected void prepareHttpUrlConnection(HttpURLConnection urlConn)
	{
		if (USER_AGENT != null)
			urlConn.setRequestProperty("User-agent", USER_AGENT);
		urlConn.setRequestProperty("Accept", ACCEPT);
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	public void changeSource()
	{
		// ConnectivityManager manager = (ConnectivityManager)
		// getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		// if (manager.getActiveNetworkInfo() == null ||
		// !manager.getActiveNetworkInfo().isConnected())
		// {
		// mapView.setTileSource(dw.getTileSource());
		// }
		// else
		// {
		// mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
		// }
	}

	private static int xtile;
	private static int ytile;

	public void putMyItemizedOverlay(GeoPoint p)
	{
		ArrayList<OverlayItem> list = new ArrayList<OverlayItem>();
		myOverlays = new MyOverlay(getActivity(), list);
		OverlayItem overlayItem = new OverlayItem("Home Sweet Home", "This is the place I live", p);
		overlayItem.setMarkerHotspot(OverlayItem.HotspotPlace.BOTTOM_CENTER);
		Drawable marker = getResources().getDrawable(R.drawable.pin_red);
		overlayItem.setMarker(marker);
		myOverlays.addItem(overlayItem);
		mapView.getOverlays().add(myOverlays);
		mapView.invalidate();
	}

	public MapView getMapView()
	{
		return mapView;
	}

	class MyLocationListener implements LocationListener
	{
		private OverlayItem myCurrentLocationOverlayItem;

		public void onLocationChanged(Location location)
		{
			currentLocation = new GeoPoint(location);
			Log.i(MyLocationListener.class.getSimpleName(), "My location: " + currentLocation.getLatitudeE6() + " "
					+ currentLocation.getLongitudeE6());
			displayMyCurrentLocationOverlay();
		}

		public void onProviderDisabled(String provider)
		{
		}

		public void onProviderEnabled(String provider)
		{
		}

		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}

		private void displayMyCurrentLocationOverlay()
		{
			if (currentLocation != null)
			{
				if (myOverlays == null)
				{
					ArrayList<OverlayItem> list = new ArrayList<OverlayItem>();
					myOverlays = new MyOverlay(getActivity(), list);

				}
				if (myCurrentLocationOverlayItem != null)
				{
					myOverlays.removeItem(myCurrentLocationOverlayItem);
				}
				myCurrentLocationOverlayItem = new OverlayItem("My Location", "My Location!", currentLocation);

				Drawable marker = getResources().getDrawable(R.drawable.my_location);
				myCurrentLocationOverlayItem.setMarker(marker);
				myOverlays.addItem(myCurrentLocationOverlayItem);
				mapView.getOverlays().add(myOverlays);
				mapView.getController().setCenter(currentLocation);
			}
		}
	}

	private OnClickListener findMeListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			AsyncTask<Void, Void, List<Cafe>> showCafeTask = new AsyncTask<Void, Void, List<Cafe>>()
			{

				@Override
				protected List<Cafe> doInBackground(Void... params)
				{
					List<Cafe> cafeList = MainApplication.getDatabase().getAllCafes();
					for (Cafe cafe : cafeList)
					{
						GeoPoint point = new GeoPoint(cafe.getLatitude(), cafe.getLongitude());
						OverlayItem cafeItem = new OverlayItem(cafe.getName(), cafe.getDescription(), point);
						Drawable marker = getResources().getDrawable(R.drawable.coffee);
						cafeItem.setMarker(marker);
						myOverlays.addItem(cafeItem);
						mapView.getOverlays().add(myOverlays);
					}
					return cafeList;
				}

			};
			showCafeTask.execute((Void) null);
			Location location = null;
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			{
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
				location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (location != null)
				{
					currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
				}
				else
				{
					location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (location != null)
					{
						currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
					}
				}
			}
			if (location == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
			{
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (location != null)
				{
					currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
				}
				else
				{
					location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (location != null)
					{
						currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
					}
				}
			}
		}
	};

	private GeoPoint getPointByAddress() throws Exception
	{
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(new HttpGet(new String(
				"http://geocode-maps.yandex.ru/1.x/?format=json&geocode=Минск,+Смоленская+улица,+дом+15".getBytes("cp1251"))));
		String responseString = IOUtils.toString(response.getEntity().getContent());
		JSONObject obj = new JSONObject(responseString);
		JSONObject resp = (JSONObject) obj.get("response");
		JSONObject arr = (JSONObject) resp.get("GeoObjectCollection");
		JSONArray arr1 = (JSONArray) arr.get("featureMember");
		arr = (JSONObject) arr1.get(0);
		arr = arr.getJSONObject("GeoObject");
		arr = arr.getJSONObject("Point");
		String point = arr.getString("pos");
		String[] s = point.split(" ");
		GeoPoint pp = new GeoPoint(new Double(s[1]), new Double(s[0]));
		return pp;
	}
}
