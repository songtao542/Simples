package com.song.example.tile;


import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.song.example.R;
import com.song.example.provider.BookContent;

public class TileTestActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_test);

        textView = (TextView) findViewById(R.id.textView);
        printUri("content://com.android.email.provider/account/1234");
        printUri("content://com.android.email.provider:8080/account?name=song&man=true&age=27&job=reader%20seller");

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content")
                .authority("com.android.email.provider")
                .appendPath("mailbox")
                .encodedFragment("fragment1")
                .encodedFragment("fragment2")
                .encodedOpaquePart("opaquepart")
                .query("query=test")
                .appendQueryParameter("name", "song")
                .appendQueryParameter("age", "27")
                .appendQueryParameter("man", "false")
                .appendQueryParameter("job", "reader writer seller");


        printUri(builder.build().toString());

        Uri u = BookContent.Book.CONTENT_URI.buildUpon().appendEncodedPath("author").appendEncodedPath("song").build();

        int m = sURIMatcher.match(u);

        textView.append("match code:" + m);
        textView.append("\n\n");
    }

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        // All category
        sURIMatcher.addURI(BookContent.AUTHORITY, "category", 1);
        // A specific category
        sURIMatcher.addURI(BookContent.AUTHORITY, "category/#", 2);

        // All book
        sURIMatcher.addURI(BookContent.AUTHORITY, "book", 3);
        // A specific book
        sURIMatcher.addURI(BookContent.AUTHORITY, "book/#", 4);
        sURIMatcher.addURI(BookContent.AUTHORITY, "book/author/*", 5);
    }

    private void printUri(String uristr) {
        Uri uri = Uri.parse(uristr);
        StringBuilder text = new StringBuilder();
        text.append("uri=" + uristr + "\n");
        text.append("getAuthority:" + uri.getAuthority() + "\n");
        text.append("getEncodedAuthority:" + uri.getEncodedAuthority() + "\n");
        text.append("getHost:" + uri.getHost() + "\n");
        text.append("getFragment:" + uri.getFragment() + "\n");
        text.append("getEncodedFragment:" + uri.getEncodedFragment() + "\n");
        text.append("getPath:" + uri.getPath() + "\n");
        text.append("getEncodedPath:" + uri.getEncodedPath() + "\n");
        text.append("getScheme:" + uri.getScheme() + "\n");
        text.append("getSchemeSpecificPart:" + uri.getSchemeSpecificPart() + "\n");
        text.append("getEncodedSchemeSpecificPart:" + uri.getEncodedSchemeSpecificPart() + "\n");
        text.append("getUserInfo:" + uri.getUserInfo() + "\n");
        text.append("getEncodedUserInfo:" + uri.getEncodedUserInfo() + "\n");
        text.append("getQuery:" + uri.getQuery() + "\n");
        text.append("getEncodedQuery:" + uri.getEncodedQuery() + "\n");
        text.append("getQueryParameterNames:" + uri.getQueryParameterNames() + "\n");
        text.append("getQueryParameter(\"name\"):" + uri.getQueryParameter("name") + "\n");
        text.append("getQueryParameters(\"job\"):" + uri.getQueryParameters("job") + "\n");
        text.append("getBooleanQueryParameter(\"man\",true):" + uri.getBooleanQueryParameter("man", true) + "\n");
        text.append("getPathSegments:" + uri.getPathSegments() + "\n");
        text.append("getLastPathSegment:" + uri.getLastPathSegment() + "\n");
        text.append("getPort:" + uri.getPort() + "\n");
        textView.append(text.toString());
        textView.append("\n\n");
    }
}
