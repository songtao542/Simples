package com.example.archcomponents;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.Permissions;

public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner, BlankFragment.OnFragmentInteractionListener {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    SimpleLiveData simpleLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LifeCycleTest", "Activity onCreate");
        if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) || PackageManager.PERMISSION_GRANTED != checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1232);
            return;
        }
        init();
    }

    private void init() {
        setContentView(R.layout.activity_main);
        simpleLiveData = new SimpleLiveData(this);

        simpleLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {

            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(BlankFragment.newInstance("1", "1"), "fragment");
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                finish();
                break;
            }
        }
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LifeCycleTest", "Activity onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LifeCycleTest", "Activity onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LifeCycleTest", "Activity onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LifeCycleTest", "Activity onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycleTest", "Activity onDestroy");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
