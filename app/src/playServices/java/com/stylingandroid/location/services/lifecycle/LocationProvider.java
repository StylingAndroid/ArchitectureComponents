package com.stylingandroid.location.services.lifecycle;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.stylingandroid.location.services.CommonLocation;

public class LocationProvider implements LifecycleObserver {
    private final Context context;
    private final Lifecycle lifecycle;
    private final LocationListener locationListener;

    private FusedLocationProviderClient fusedLocationProviderClient = null;

    public LocationProvider(@NonNull Context context, Lifecycle lifecycle, @NonNull LocationListener listener) {
        this.context = context;
        this.lifecycle = lifecycle;
        this.locationListener = listener;
        lifecycle.addObserver(this);
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void registerForLocationUpdates() {
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

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void unregisterForLocationUpdates() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void unregisterObserver() {
        lifecycle.removeObserver(this);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location lastLocation = locationResult.getLastLocation();
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            float accuracy = lastLocation.getAccuracy();
            CommonLocation location = new CommonLocation(latitude, longitude, accuracy);
            locationListener.updateLocation(location);
        }
    };

}
