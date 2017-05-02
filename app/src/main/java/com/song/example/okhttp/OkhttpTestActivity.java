package com.song.example.okhttp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.song.example.LogTag;
import com.song.example.R;
import com.song.example.dagger.User;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpTestActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_test);
        ButterKnife.bind(this);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url("https://api.github.com/users/songtao542").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d(LogTag.TAG, "OkhttpTestActivity onResponse() thread:" + Thread.currentThread().getName());
                Log.d(LogTag.TAG, "onResponse()" + response.body());

                GsonBuilder builder = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                Gson gson = builder.create();
                final User user = gson.fromJson(response.body().string(), User.class);
                Log.d(LogTag.TAG, "onResponse() user:" + user);

                //The Callback run on child thread not the ui thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("Response:" + user);
                    }
                });
            }
        });
    }
}
