package com.example.minirecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.minirecorder.Utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitiateLandindFragment();
    }

    public void InitiateLandindFragment(){
        FragmentUtils.addFragmentToMainContainer(this, new LandingFragment(),LandingFragment.TAG, false);
    }
}
