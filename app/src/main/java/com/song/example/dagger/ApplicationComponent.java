package com.song.example.dagger;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by le on 3/31/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    ApiService apiService();

    LocationManager locationManager();

    Context applicationContext();
}
