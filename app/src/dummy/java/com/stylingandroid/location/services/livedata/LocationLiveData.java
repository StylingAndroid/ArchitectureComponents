package com.stylingandroid.location.services.livedata;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.stylingandroid.location.services.CommonLocation;

public class LocationLiveData extends LiveData<CommonLocation> {
    private static final double LATITUDE = 51.649836;
    private static final double LONGITUDE = -0.401486;
    private static final float ACCURACY = 5f;
    private static final CommonLocation LOCATION = new CommonLocation(LATITUDE, LONGITUDE, ACCURACY);

    public LocationLiveData(Context context) {
        //NO-OP
    }

    @Override
    protected void onActive() {
        super.onActive();
        setValue(LOCATION);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
