package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by tricianemiroff on 11/9/15.
 */
public class LocationUpdate implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    public abstract interface LocationCallback {
        public void newLocation(Location location);
    }

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationCallback locationCallBack;
    private Context context;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    public LocationUpdate(Context context, LocationCallback callback){
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationCallBack = callback;

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);

        this.context = context;
    }

    public void connect(){
        googleApiClient.connect();
    }

    public void disconnect(){
        if (googleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle){

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(location == null){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            locationCallBack.newLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i){}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        if (connectionResult.hasResolution() && context instanceof Activity) {
            try {
                Activity activity = (Activity) context;
                connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Toast.makeText(context, "Sorry, we could not connect with location services", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location){
        locationCallBack.newLocation(location);
    }
}