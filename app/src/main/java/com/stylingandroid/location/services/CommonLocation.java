package com.stylingandroid.location.services;

public class CommonLocation {
    private final double longitude;
    private final double latitude;
    private final float accuracy;

    public CommonLocation(double longitude, double latitude, float accuracy) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
    }

    double getLongitude() {
        return longitude;
    }

    double getLatitude() {
        return latitude;
    }

    float getAccuracy() {
        return accuracy;
    }
}
