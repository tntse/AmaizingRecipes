package com.taeyeona.amaizingunicornrecipes.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;
import com.taeyeona.amaizingunicornrecipes.LocationUpdate;
import com.taeyeona.amaizingunicornrecipes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements LocationUpdate.LocationCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationUpdate mLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try{
            setUpMapIfNeeded();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mLocationProvider = new LocationUpdate(this, this);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            try{
                buildAlertMessageNoGPS();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_maps, menu);
        return true;
    }

    private void buildAlertMessageNoGPS() throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException{
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface
                                                dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider
                                .Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            setUpMapIfNeeded();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mLocationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() throws JSONException {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setUpMap() throws JSONException {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(37.76, -122.44), 8.0f));
    }

    public void newLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        Double lat = currentLatitude;
        Double lng = currentLongitude;

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .title("You Are Here");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        final JSONRequest jsonRequest = new JSONRequest();

        jsonRequest.createResponse("https://maps.googleapis.com/maps/api/place/nearbysearch/json",
                "key", "AIzaSyC9ZRlRXJVAxq8GjSHT2lMgVGwgcHPtmx4", "", "", "", "1500", "", "", "", "",
                "grocery_or_supermarket", "true", "location", lat, lng, "", null, null);

        jsonRequest.sendResponse(getApplicationContext(), new JSONRequest.VolleyCallBack() {
            @Override
            public void onSuccess() {
                JSONObject response = jsonRequest.getResponse();

                try {
                    for (int i = 0; i < 15; i++) {

                        JSONArray arr = response.getJSONArray("results");
                        JSONObject jsonObject = arr.getJSONObject(i);
                        JSONObject jsonLocation = jsonObject.getJSONObject("geometry").getJSONObject("location");

                        mMap.addMarker(new MarkerOptions().title(jsonObject.getString("name"))
                                .snippet(jsonObject.getString("vicinity"))
                                .position(new LatLng(
                                        jsonLocation.getDouble("lat"),
                                        jsonLocation.getDouble("lng"))));

                    }
                } catch (JSONException e) {

                }
            }
        });
    }
}