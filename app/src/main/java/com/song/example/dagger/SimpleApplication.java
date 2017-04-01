package com.song.example.dagger;

import android.app.Application;


/**
 * Created by le on 3/31/17.
 */

public class SimpleApplication extends Application {


    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
