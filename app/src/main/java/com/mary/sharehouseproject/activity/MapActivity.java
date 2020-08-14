package com.mary.sharehouseproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.mary.sharehouseproject.R;
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