package com.song.example.dagger;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Inject;

/**
 * Created by le on 3/31/17.
 */

public class TestObject {

    Context mContext;
    LocationManager mLocationManager;
    ApiService mApiService;

    @Inject
    public TestObject(Context context, LocationManager locationManager, ApiService apiService) {
        mContext = context;
        mLocationManager = locationManager;
        mApiService = apiService;
    }
}
