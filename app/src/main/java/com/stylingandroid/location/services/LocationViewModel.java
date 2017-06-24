package com.stylingandroid.location.services;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.stylingandroid.location.services.livedata.LocationLiveData;

import hugo.weaving.DebugLog;

@DebugLog
class LocationViewModel extends ViewModel {
    private LiveData<CommonLocation> locationLiveData = null;

    LiveData<CommonLocation> getLocation(Context context) {
        if (locationLiveData == null) {
            locationLiveData = new LocationLiveData(context);
        }
        return locationLiveData;
    }
}
