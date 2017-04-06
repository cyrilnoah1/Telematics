package com.example.rapid.telematics.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rapid.telematics.R;
import com.example.rapid.telematics.common.Utils;

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = BaseActivity.class.getSimpleName();

    protected Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        utils = new Utils();
    }

    /**
     * Method to initialize views.
     */
    abstract void initViews();

    /**
     * Method to add a Fragment to the required container.
     *
     * @param containerId Resource id of the container.
     * @param fragment    Fragment to be added.
     */
    protected void addFragment(int containerId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }

    /**
     * Method to replace a Fragment in the required container.
     *
     * @param containerId Resource id of the container.
     * @param fragment    Fragment to be injected.
     */
    protected void replaceFragment(int containerId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.commit();
    }
}
