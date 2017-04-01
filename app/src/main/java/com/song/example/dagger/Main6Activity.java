package com.song.example.dagger;

import android.content.Context;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.song.example.R;

import java.util.List;

import javax.inject.Inject;

import dagger.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main6Activity extends AppCompatActivity {

    @ActivityScope
    @Component(dependencies = ApplicationComponent.class)
    interface ActivityComponent {
        void inject(Main6Activity activity);
    }

    @Inject
    protected ApiService mApiService;

    @Inject
    LocationManager mLocationManager;

    @Inject
    Context mApplicationContext;

    @Inject
    MainTest mMainTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        try {
            ApplicationComponent applicationComponent = ((SimpleApplication) getApplication()).getComponent();
            ApiService apiServiceFromApp = applicationComponent.apiService();

            DaggerMain6Activity_ActivityComponent.builder().applicationComponent(applicationComponent).build().inject(this);
            Call<User> userCall = mApiService.getUser("songtao542");

            Log.d("Main6Activity", "apiServiceFromApp=" + apiServiceFromApp);
            Log.d("Main6Activity", "mApiService=======" + mApiService);
            Log.d("Main6Activity", "mApiService==apiServiceFromApp : " + (mApiService == apiServiceFromApp));
            Log.d("Main6Activity", "mApiService.mApiParser=" + mApiService.mApiParser);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d("Main6Activity", "onResponse" + response.body());

                    GsonBuilder builder = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                    Gson gson = builder.create();
                    Log.d("Main6Activity", "user:" + gson.toJson(response.body()));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

            List<String> providers = mLocationManager.getAllProviders();
            for (String s : providers) {
                Log.d("Main6Activity", "provider:" + s);
            }
            Log.d("Main6Activity", "mMainTest:" + mMainTest);

            Log.d("Main6Activity", "mMainTest.ApiService == mApiService " + (mMainTest.mApiService == mApiService));
            Log.d("Main6Activity", "mMainTest.mContext == mApplicationContext " + (mMainTest.mContext == mApplicationContext));
            Log.d("Main6Activity", "mMainTest.mLocationManager == mLocationManager " + (mMainTest.mLocationManager == mLocationManager));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
