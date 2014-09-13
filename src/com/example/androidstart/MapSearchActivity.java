package com.example.androidstart;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MapSearchActivity extends Activity implements
		OnMarkerClickListener {

	EditText editText;
	TextView mResult;

	Geocoder mCoder;
	GoogleMap mMap;

	AddressDB addressDB;
	SQLiteDatabase db;

	ArrayList<Marker> myMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		editText = (EditText) findViewById(R.id.mapedittext);
		mResult = (TextView) findViewById(R.id.maptextview);
		mCoder = new Geocoder(this);
		addressDB = new AddressDB(this);
		db = addressDB.getWritableDatabase();
		myMarker = new ArrayList<Marker>();

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		//����ġ �޾ƿ������� �� �⺻����

		/** ����ġ �޾ƿ��� */
		Criteria criteria = new Criteria();
		LocationManager locationManager;
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		String provider = locationManager.getBestProvider(criteria, true);

		if (provider == null) {
			provider = "network";
		}
		Location location = locationManager.getLastKnownLocation(provider);
		LatLng my_location;
		my_location = new LatLng(location.getLatitude(),
				location.getLongitude());
		/** ����ġ �޾ƿ��ⳡ */

		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_location, 10));
		// ����ġ �̵�

		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM address;", null);
		// DBĿ�� �� ������ �ۼ�. �ҷ��� ��� �������� ��ǥ������ ��Ŀ ����
		while (cursor.moveToNext()) {
			Double a = cursor.getDouble(0);
			Double b = cursor.getDouble(1);

			myMarker.add(mMap.addMarker(new MarkerOptions()
					.position(new LatLng(a, b))));// ǥ�õ��ϰ� �迭���� ����. �迭�� Ŭ���� �����

		}
		
		cursor.close();

		mMap.setOnMarkerClickListener(this);// ��ĿŬ�� �̺�Ʈ

	}

	public void mOnClick(View v) {

		switch (v.getId()) {
		case R.id.button1:// ��ưŬ����

			String address = ((EditText) findViewById(R.id.mapedittext))
					.getText().toString();
			if (address == null) {
				mResult.setText("no result");
				return;
			}
			try {
				List<Address> geoCodeResults = mCoder.getFromLocationName(
						address, 1);
				if (geoCodeResults.size() > 0) {
					Address bestResult = (Address) geoCodeResults.get(0);
					mResult.setText(String.format("(%s,%s)\n[%s][%s][%s][%s]",
							bestResult.getLatitude(),
							bestResult.getLongitude(),
							bestResult.getLocality(),
							bestResult.getFeatureName(),
							bestResult.getThoroughfare(),
							bestResult.getCountryName()));
					// �ؽ�Ʈ�� ��ǥ�� ���

					LatLng selectLocation = new LatLng(
							bestResult.getLatitude(), bestResult.getLongitude());
					mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
							selectLocation, 10));
					// ���������������̵�

					db.execSQL("INSERT INTO address VALUES('"
							+ bestResult.getLatitude() + "','"
							+ bestResult.getLongitude() + "');");
					// ��� ����

					myMarker.add(mMap.addMarker(new MarkerOptions()
							.position(new LatLng(bestResult.getLatitude(),
									bestResult.getLongitude()))));// ��Ŀ��� �� ����

				}
			} catch (Exception e) {
				Log.e("GPS", "Failed to get address", e);
				return;
			}

		}

	}

	// ��Ŀ Ŭ�� �̺�Ʈ ����
	@Override
	public boolean onMarkerClick(Marker m) {
		for (int i = 0; i < myMarker.size(); i++) {
			if (m.equals(myMarker.get(i))) {

				double latitude = myMarker.get(i).getPosition().latitude;
				double longitude = myMarker.get(i).getPosition().longitude;

				try {
					List<Address> geoCodeResults = mCoder.getFromLocation(
							latitude, longitude, 1);
					if (geoCodeResults.size() > 0) {
						Address bestResult = (Address) geoCodeResults.get(0);

						/** ���̾�α� ���� */
						View view = this.getLayoutInflater().inflate(
								R.layout.dialog_view, null);
						TextView txtTitle = (TextView) view
								.findViewById(R.id.title);
						txtTitle.setTextSize(20);
						txtTitle.setTextColor(Color.WHITE);
						txtTitle.setText(Integer.toString(i + 1));

						TextView message = (TextView) view
								.findViewById(R.id.message);
						message.setTextSize(14);
						message.setTextColor(Color.WHITE);
						message.setText("(" + Double.toString(latitude) + "),("
								+ Double.toString(longitude) + ")\n["
								+ bestResult.getFeatureName() + "]["
								+ bestResult.getThoroughfare() + "]["
								+ bestResult.getLocality() + "]["
								+ bestResult.getCountryName() + "]");
						/** ���ó� */

						AlertDialog.Builder builder = new AlertDialog.Builder(
								this);
						builder.setView(view);// ���̾�α׿� �ֱ�

						AlertDialog alert = builder.create();
						alert.show();//���̾�α� ���

						break;
					}
				} catch (Exception e) {
					Log.e(getClass().toString(), "Failed in using Geocoder.", e);
					break;
				}

			}
		}

		return false;
	}

}

/** DB�� Ŭ���� */
class AddressDB extends SQLiteOpenHelper {

	public AddressDB(Context context) {
		super(context, "Map.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE address(latitude DOUBLE, longitude DOUBLE);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS address");
		onCreate(db);

	}

}
