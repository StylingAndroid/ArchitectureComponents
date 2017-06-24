package com.stylingandroid.location.services;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylingandroid.location.services.lifecycle.LocationListener;

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
        LocationViewModel locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        LiveData<CommonLocation> liveData = locationViewModel.getLocation(context);
        liveData.observe(this, new Observer<CommonLocation>() {
            @Override
            public void onChanged(@Nullable CommonLocation commonLocation) {
                updateLocation(commonLocation);
            }
        });
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
    public void updateLocation(CommonLocation location) {
        String latitudeString = createFractionString(location.getLatitude());
        String longitudeString = createFractionString(location.getLongitude());
        String accuracyString = createAccuracyString(location.getAccuracy());
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
