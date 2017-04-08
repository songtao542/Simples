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


public class DaggerTestActivity extends AppCompatActivity {

    @ActivityScope
    @Component(dependencies = ApplicationComponent.class)
    interface ActivityComponent {
        void inject(DaggerTestActivity activity);
    }

    @Inject
    protected ApiService mApiService;

    @Inject
    LocationManager mLocationManager;

    @Inject
    Context mApplicationContext;

    @Inject
    TestObject mTestObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_test);

        try {
            ApplicationComponent applicationComponent = ((SimpleApplication) getApplication()).getComponent();
            ApiService apiServiceFromApp = applicationComponent.apiService();

            DaggerDaggerTestActivity_ActivityComponent.builder().applicationComponent(applicationComponent).build().inject(this);
            Call<User> userCall = mApiService.getUser("songtao542");

            Log.d("DaggerTestActivity", "apiServiceFromApp=" + apiServiceFromApp);
            Log.d("DaggerTestActivity", "mApiService=======" + mApiService);
            Log.d("DaggerTestActivity", "mApiService==apiServiceFromApp : " + (mApiService == apiServiceFromApp));
            Log.d("DaggerTestActivity", "mApiService.mApiParser=" + mApiService.mApiParser);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d("DaggerTestActivity", "onResponse" + response.body());

                    GsonBuilder builder = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                    Gson gson = builder.create();
                    Log.d("DaggerTestActivity", "user:" + gson.toJson(response.body()));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

            List<String> providers = mLocationManager.getAllProviders();
            for (String s : providers) {
                Log.d("DaggerTestActivity", "provider:" + s);
            }
            Log.d("DaggerTestActivity", "mTestObject:" + mTestObject);

            Log.d("DaggerTestActivity", "mTestObject.ApiService == mApiService " + (mTestObject.mApiService == mApiService));
            Log.d("DaggerTestActivity", "mTestObject.mContext == mApplicationContext " + (mTestObject.mContext == mApplicationContext));
            Log.d("DaggerTestActivity", "mTestObject.mLocationManager == mLocationManager " + (mTestObject.mLocationManager == mLocationManager));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
