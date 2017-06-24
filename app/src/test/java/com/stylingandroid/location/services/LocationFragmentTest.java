package com.stylingandroid.location.services;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.stylingandroid.location.services.livedata.LocationLiveData;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationFragmentTest {
    private LocationFragment locationFragment;

    @Mock private ViewModelProvider viewModelProvider;
    @Mock private LocationViewModel locationViewModel;
    @Mock private LocationLiveData locationLiveData;
    @Mock private Context context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        locationFragment = new LocationFragment();
        locationFragment.setViewModelProvider(viewModelProvider);

        when(viewModelProvider.get(any(Class.class))).thenReturn(locationViewModel);
        when(locationViewModel.getLocation(any(Context.class))).thenReturn(locationLiveData);
    }

    @Test
    public void givenALocationFragment_whenOnAttachIsCalled_thenItStartsObservingTheLiveDataObject() throws Exception {

        locationFragment.onAttach(context);

        verify(locationLiveData).observe(eq(locationFragment), any(Observer.class));
    }

}
