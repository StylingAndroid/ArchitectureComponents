package com.stylingandroid.location.services.lifecycle;

public interface LocationListener {
    void updateLocation(double latitude, double longitude, float accuracy);
}
