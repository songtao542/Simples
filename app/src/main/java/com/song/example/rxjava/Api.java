package com.song.example.rxjava;

import com.song.example.dagger.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by le on 3/31/17.
 */

public interface Api {

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("users/{username}")
    Observable<User> getUser(@Path("username") String username);

}
