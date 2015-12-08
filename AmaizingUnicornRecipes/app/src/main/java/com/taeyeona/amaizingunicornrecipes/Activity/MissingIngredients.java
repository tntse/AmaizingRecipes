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
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taeyeona.amaizingunicornrecipes.Auth;
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
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_ingredients);

        mLocationProvider = new LocationUpdate(this, this);

        String ingredients = this.getIntent().getStringExtra("Ingredients");
        sharedPreferences = this.getSharedPreferences(Auth.SHARED_PREFS_KEY, Context.MODE_PRIVATE);

        String[] rawIngredients = ingredients.split("\n");

        Set<String> manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        if(!(manager instanceof IngredientsManager)){

            manager = new IngredientsManager(manager);
        }

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

    /**
     *
     * @param v
     */
    public void onClick(View v) {

        if (firstOpen) {
            maps_frag = SupportMapFragment.newInstance();
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
        transaction.show(maps_frag);
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

        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);

        transaction.add(R.id.maps_fragment_holder, maps_frag, "newMap");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

        try{
            setUpMapIfNeeded();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Sorry, we could not load your map", Toast.LENGTH_LONG).show();
        }
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            try{
                buildAlertMessageNoGPS();
            } catch (GooglePlayServicesNotAvailableException e) {
                Toast.makeText(getApplicationContext(), "Sorry, we could not load your map", Toast.LENGTH_LONG).show();
            } catch (GooglePlayServicesRepairableException e) {
                Toast.makeText(getApplicationContext(), "Sorry, we could not load your map", Toast.LENGTH_LONG).show();
            }
        }
        mLocationProvider.connect();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_maps, menu);
        return true;
    }

    /**
     *
     * @throws GooglePlayServicesRepairableException
     * @throws GooglePlayServicesNotAvailableException
     */
    private void buildAlertMessageNoGPS() throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException{
        final Context contextGPS = getApplicationContext();
        final CharSequence GPSText = "Grocery stores will not load without GPS enabled. Please enable GPS to find stores.";

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
                        Toast GPSToast = Toast.makeText(contextGPS, GPSText, Toast.LENGTH_LONG);
                        GPSToast.show();
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
            ((SupportMapFragment)maps_frag).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                   mMap = googleMap;
                }
            });
        }
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            setUpMap();
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

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(mLocationProvider != null && maps_frag != null){
            try{
                setUpMapIfNeeded();
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Sorry, we could not load your map", Toast.LENGTH_LONG).show();
            }
            mLocationProvider.connect();
        }

    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationProvider != null)
            mLocationProvider.disconnect();
    }

    /**
     *
     * @param location
     */
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

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);

        final JSONRequest jsonRequest = new JSONRequest();

        String radius = sharedPreferences.getString("Radius", Auth.RADIUS);
        jsonRequest.createResponse(Auth.URL_MAPS,
                Auth.STRING_KEY, Auth.MAPS_KEY, "", "", "", radius, "", "", "", "",
                Auth.TYPES, Auth.SENSOR, Auth.LOCATION, lat, lng, "", null, null);

        jsonRequest.sendResponse(getApplicationContext(), new JSONRequest.VolleyCallBack() {
            @Override
            public void onSuccess() {
                JSONObject response = jsonRequest.getResponse();

                try {
                    JSONArray arr = response.getJSONArray("results");
                    int numResults = (arr.length()<15)?arr.length():15;
                    for (int i = 0; i < numResults; i++) {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        JSONObject jsonLocation = jsonObject.getJSONObject("geometry").getJSONObject("location");

                        mMap.addMarker(new MarkerOptions().title(jsonObject.getString("name"))
                                .snippet(jsonObject.getString("vicinity"))
                                .position(new LatLng(
                                        jsonLocation.getDouble("lat"),
                                        jsonLocation.getDouble("lng"))));

                    }
                } catch (JSONException e) {
                    Log.d("Maps", e.getMessage().toString());
                }
            }
            @Override
            public void onFailure(){
                Toast.makeText(getApplicationContext(), "Sorry, we could not load your map", Toast.LENGTH_LONG).show();
            }
        });
    }
}
