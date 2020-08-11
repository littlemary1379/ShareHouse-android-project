package com.mary.sharehouseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.naver.maps.map.NaverMapSdk;


public class MapActivity extends AppCompatActivity {

    private Context mapContext=MapActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("x"));

    }
}