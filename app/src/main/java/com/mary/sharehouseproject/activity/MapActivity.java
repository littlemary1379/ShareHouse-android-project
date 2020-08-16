package com.mary.sharehouseproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;

import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.HouseTest;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import ted.gun0912.clustering.naver.TedNaverClustering;


public class MapActivity extends BaseDemoActivity {
    private NaverMap naverMap;

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        naverMap.moveCamera(
                CameraUpdate.toCameraPosition(
                        new CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, NaverMap.DEFAULT_CAMERA_POSITION.zoom))
        );

        TedNaverClustering.with(this, naverMap)
                .items(getItems())
                .make();

    }


    private List<HouseTest> getItems() {
        LatLngBounds bounds = naverMap.getContentBounds();
        ArrayList<HouseTest> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            HouseTest temp = new HouseTest((bounds.getNorthLatitude() - bounds.getSouthLatitude()) * Math.random() + bounds.getSouthLatitude(),
                    (bounds.getEastLongitude() - bounds.getWestLongitude()) * Math.random() + bounds.getWestLongitude()
            );
            items.add(temp);
        }
        return items;

    }
}