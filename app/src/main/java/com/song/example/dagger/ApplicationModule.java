package com.song.example.dagger;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by le on 3/31/17.
 */
@Module
public class ApplicationModule {

    Application mApplication;

    public ApplicationModule(Context context) {
        mApplication = (Application) context.getApplicationContext();
    }

    @Singleton
    @Provides
    public ApiService provideApiService() {
        return new ApiService();
    }

    @Singleton
    @Provides
    public LocationManager provideLocationManager() {
        return (LocationManager) mApplication.getSystemService(Context.LOCATION_SERVICE);
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return mApplication;
    }

}
