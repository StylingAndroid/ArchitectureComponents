package com.stylingandroid.location.services.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;

import com.stylingandroid.location.services.CommonLocation;

public class LocationProvider implements LifecycleObserver {
    private static final double LATITUDE = 51.649836;
    private static final double LONGITUDE = -0.401486;
    private static final float ACCURACY = 5f;
    private static final CommonLocation LOCATION = new CommonLocation(LATITUDE, LONGITUDE, ACCURACY);

    private final Lifecycle lifecycle;
    private final LocationListener locationListener;

    public LocationProvider(@NonNull Context context, Lifecycle lifecycle, @NonNull LocationListener listener) {
        this.lifecycle = lifecycle;
        this.locationListener = listener;
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void unregisterObserver() {
        lifecycle.removeObserver(this);
    }

    void sendDummyLocation() {
        locationListener.updateLocation(LOCATION);
    }
}
