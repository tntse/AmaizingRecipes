package com.taeyeona.amaizingunicornrecipes.Activity;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;
import com.taeyeona.amaizingunicornrecipes.LocationUpdate;
import com.taeyeona.amaizingunicornrecipes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by tricianemiroff on 11/5/15.
 */
public class MissingIngredients extends AppCompatActivity implements LocationUpdate.LocationCallback, View.OnClickListener{
    private Button maps;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationUpdate mLocationProvider;
    private LocationManager locationManager;
    private Fragment maps_frag;
    private boolean open = true;
    private boolean firstOpen = true;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_ingredients);


        mLocationProvider = new LocationUpdate(this, this);

        Log.d(MissingIngredients.class.getSimpleName(), "I'm here");

        String ingredients = this.getIntent().getStringExtra("Ingredients");
        SharedPreferences sharedPreferences = this.getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);

        String[] rawIngredients = ingredients.split("\n");
        Log.d(MissingIngredients.class.getSimpleName(), "raw Ingredients are: " + rawIngredients.toString());

        Set<String> manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        Log.d(MissingIngredients.class.getSimpleName(), "manager instance of IngredientsManager: " + (manager instanceof IngredientsManager));
        if(!(manager instanceof IngredientsManager)){

            manager = new IngredientsManager(manager);
        }

        Log.d(MissingIngredients.class.getSimpleName(), "manager " + manager);
        String[] missingIngredients = ((IngredientsManager)manager).findMissingIngredients(rawIngredients);

        ListView theListView = (ListView) findViewById(R.id.missing_ingre_list);

        theListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, missingIngredients));

        maps = (Button) findViewById(R.id.find_store_button);
        maps.setOnClickListener(this);

        ImageButton imgButton = (ImageButton)findViewById(R.id.missing_ingre_back);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void onClick(View v) {

        if (firstOpen) {
            addFragment();
            firstOpen = false;
        }

        if (open) {
            open = false;
            maps.setText(getString(R.string.go_back));
            showFragment();
        } else {
            open = true;
            maps.setText(getString(R.string.find_stores));
            hideFragment();

        }
    }

    /**
     * @author HaoXian
     *
     *
     * Shows the fragment when the user previously chose to hide the player fragment
     * now reveals the player fragment to continue to video
     *
     */
    public void showFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
//        transaction.add(R.id.overlay_fragment_container, pFragment);
        transaction.show(maps_frag);
        // Commit the transaction
        transaction.commit();
    }

    /**
     * @author HaoXian
     *
     *
     * The fragment containing YouTube player , works with a slide in animation when
     * the fragment is to be hidden be the user, and reveals the main layout, instructions
     */
    public void hideFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //the underfragment enters,exit
        transaction.setCustomAnimations(R.anim.slide_top_in, R.anim.slide_top_out);//works
        transaction.hide(maps_frag);
        // Commit the transaction
        transaction.commit();
    }


    /**
     * @author HaoXian
     *
     *
     * The fragment containing YouTube player , works with a slide in animation when
     * the fragment is added (overlays) the current layout
     */
    public void addFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //the underfragment enters,exit
//        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);

        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);


        Fragment fragment = SupportMapFragment.newInstance();
        maps_frag = fragment;

        try{
            setUpMapIfNeeded();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        locationManager = (LocationManager)
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

        transaction.add(R.id.maps_fragment_holder, maps_frag, "newMap");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
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
            mMap = ((SupportMapFragment)maps_frag).getMap();
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

    @Override
    protected void onResume() {
        super.onResume();
        if(mLocationProvider != null && maps_frag != null){
            try{
                setUpMapIfNeeded();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mLocationProvider.connect();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationProvider != null)
            mLocationProvider.disconnect();
    }

    public void newLocation(Location location) {
        Log.d(MissingIngredients.class.getSimpleName(), location.toString());

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
/*

        Handler hand = new Handler();
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {

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
        }, 3000);
*/

    }

}
