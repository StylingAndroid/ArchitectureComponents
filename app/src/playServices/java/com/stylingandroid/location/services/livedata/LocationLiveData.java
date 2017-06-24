package com.stylingandroid.location.services.livedata;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.stylingandroid.location.services.CommonLocation;

import hugo.weaving.DebugLog;

@DebugLog
public class LocationLiveData extends LiveData<CommonLocation> {
    private final Context context;

    private FusedLocationProviderClient fusedLocationProviderClient = null;

    public LocationLiveData(Context context) {
        this.context = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        FusedLocationProviderClient locationProviderClient = getFusedLocationProviderClient();
        LocationRequest locationRequest = LocationRequest.create();
        Looper looper = Looper.myLooper();
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, looper);
    }

    @NonNull
    private FusedLocationProviderClient getFusedLocationProviderClient() {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }
        return fusedLocationProviderClient;
    }

    @Override
    protected void onInactive() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
        super.onInactive();
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location newLocation = locationResult.getLastLocation();
            double latitude = newLocation.getLatitude();
            double longitude = newLocation.getLongitude();
            float accuracy = newLocation.getAccuracy();
            CommonLocation location = new CommonLocation(latitude, longitude, accuracy);
            setValue(location);
        }
    };
}
