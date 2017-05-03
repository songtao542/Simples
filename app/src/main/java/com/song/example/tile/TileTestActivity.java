package com.song.example.tile;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.song.example.R;

public class TileTestActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_test);

        textView = (TextView) findViewById(R.id.textView);
        printUri("content://com.android.email.provider/account/1234");
        printUri("content://com.android.email.provider/account?name=song");
    }

    private void printUri(String uristr) {
        Uri uri = Uri.parse(uristr);
        StringBuilder text = new StringBuilder();
        text.append("uri=" + uristr + "\n");
        text.append("authority:" + uri.getAuthority() + "\n");
        text.append("authority encoded:" + uri.getEncodedAuthority() + "\n");
        text.append("host:" + uri.getHost() + "\n");
        text.append("fragment:" + uri.getFragment() + "\n");
        text.append("fragment encoded:" + uri.getEncodedFragment() + "\n");
        text.append("path:" + uri.getPath() + "\n");
        text.append("path encoded:" + uri.getEncodedPath() + "\n");
        text.append("query:" + uri.getQuery() + "\n");
        text.append("query encoded:" + uri.getEncodedQuery() + "\n");
        text.append("scheme:" + uri.getScheme() + "\n");
        text.append("user info encoded:" + uri.getEncodedUserInfo() + "\n");
        text.append("user info:" + uri.getUserInfo() + "\n");
        text.append("LastPathSegment:" + uri.getLastPathSegment() + "\n");
        text.append("QueryParameterNames:" + uri.getQueryParameterNames() + "\n");
        text.append("PathSegments:" + uri.getPathSegments() + "\n");
        text.append("Port:" + uri.getPort() + "\n");
        textView.append(text.toString());
        textView.append("\n\n");
    }
}
