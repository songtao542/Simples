package com.song.example.dagger;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Inject;

/**
 * Created by le on 3/31/17.
 */

public class MainTest {

    Context mContext;
    LocationManager mLocationManager;
    ApiService mApiService;

    @Inject
    public MainTest(Context context, LocationManager locationManager, ApiService apiService) {
        mContext = context;
        mLocationManager = locationManager;
        mApiService = apiService;
    }
}
