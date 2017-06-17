package com.stylingandroid.location.services;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylingandroid.location.services.lifecycle.LocationListener;
import com.stylingandroid.location.services.lifecycle.LocationProvider;

import java.util.Locale;

public class LocationFragment extends LifecycleFragment implements LocationListener {
    private static final String FRACTIONAL_FORMAT = "%.4f";
    private static final String ACCURACY_FORMAT = "%.1fm";

    private TextView latitudeValue;
    private TextView longitudeValue;
    private TextView accuracyValue;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new LocationProvider(getContext(), getLifecycle(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        latitudeValue = (TextView) view.findViewById(R.id.latitude_value);
        longitudeValue = (TextView) view.findViewById(R.id.longitude_value);
        accuracyValue = (TextView) view.findViewById(R.id.accuracy_value);
        return view;
    }

    @Override
    public void updateLocation(double latitude, double longitude, float accuracy) {
        String latitudeString = createFractionString(latitude);
        String longitudeString = createFractionString(longitude);
        String accuracyString = createAccuracyString(accuracy);
        latitudeValue.setText(latitudeString);
        longitudeValue.setText(longitudeString);
        accuracyValue.setText(accuracyString);
    }

    private String createFractionString(double fraction) {
        return String.format(Locale.getDefault(), FRACTIONAL_FORMAT, fraction);
    }

    private String createAccuracyString(float accuracy) {
        return String.format(Locale.getDefault(), ACCURACY_FORMAT, accuracy);
    }
}
