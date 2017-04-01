package com.song.example.dagger;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

/**
 * Created by le on 3/31/17.
 */
public class ApiService implements Api {

    Retrofit mRetrofit;

    ApiParser mApiParser;

    Api mApi;

    public ApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(interceptor);
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .client(builder.build())
                .build();
        mApi = mRetrofit.create(Api.class);
    }


    @Override
    public Call<List<User>> groupList(@Path("id") int groupId) {
        return mApi.groupList(groupId);
    }

    @Override
    public Call<User> getUser(@Path("username") String username) {
        return mApi.getUser(username);
    }
}
