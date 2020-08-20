package com.mary.sharehouseproject.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.House;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.NaverMap;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import lombok.NonNull;
import ted.gun0912.clustering.clustering.TedClusterItem;
import ted.gun0912.clustering.naver.TedNaverClustering;


public class MapActivity extends BaseDemoActivity implements LocationListener{
    private static final String TAG = "MapActivity";
    private NaverMap naverMap;
    private FirebaseFirestore db;
    private Location location;
    private LocationManager locationManager;
    private List<String> listProviders;
    protected CameraPosition cameraPosition;
    private double lat;
    private double lng;


    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mapContext = MapActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 이게 먼저니");
        
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "onMapReady: 이게 먼저니???");
        this.naverMap = naverMap;

        initToolbar();
        setupToolbarNavigationView();
        getItems();

        if (ActivityCompat.checkSelfPermission(mapContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //최초 위치값 확인
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.d(TAG, "onMapReady: GPS: lat :" + lat + " lng : " + lng);
        }
        //로케이션 서비스로 들어오는 모든 시스템 서비스의 프로바이더 확인
        listProviders = locationManager.getAllProviders();
        //GPS 로케이션만 확인 해 장소가 바뀔 때 마다 로케이션을 업데이트 함
        for(int i=0; i<listProviders.size(); i++){
            if(listProviders.get(i).equals(LocationManager.GPS_PROVIDER)){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,0, this);
            }
        }

        cameraPosition = new CameraPosition(
                new LatLng(lat, lng),
                13
        );

        naverMap.setCameraPosition(cameraPosition);
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
    }

    //변경된 좌표값을 얻어오는 매서드
    @Override
    public void onLocationChanged(Location location) {
        lat=0;
        lng=0;

        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            lat=location.getLatitude();
            lng=location.getLongitude();
            Log.d(TAG, "onLocationChanged: GPS_PROVIDER : lat : "+lat+" lng :"+lng);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //권한 확인
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            //권한이 없을 경우 최초 권한 요청 or 사용자에 의한 재요청 확인
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){
                //권한이 없을 경우 권한 재요청
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                    return;
            }
        }
    }

    //생애 주기 : 앱이 멈췄을 때 업데이트 중지
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    //생애 주기 : 앱이 재시작 할 때 업데이트 재개
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listProviders = locationManager.getAllProviders();
        for(int i=0; i<listProviders.size(); i++){
            if(listProviders.get(i).equals(LocationManager.GPS_PROVIDER)){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, this);
            }
        }
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar() {
        logoText = findViewById(R.id.tv_logoText);
        ivHamburgerButton = findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton = findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout = findViewById(R.id.layout_map_drawer);
        toolbar = findViewById(R.id.toolbar_main);
        mainNavigationView = findViewById(R.id.navigation);
        ivLogoutButton = findViewById(R.id.iv_logoutButton);

    }

    //툴바 리스너
    private void setupToolbarNavigationView() {
        ToolbarNavigationHelper.enableNavigationHelper(mapContext, mainNavigationView, mainDrawerLayout, logoText, ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton);
    }

    //데이터베이스의 값을 가져와 마커처리,
    private List<House> getItems() {
        db = FirebaseFirestore.getInstance();
        LatLngBounds bounds = naverMap.getContentBounds();
        final ArrayList<House> items = new ArrayList<>();
        db.collection("house")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                items.add(document.toObject(House.class));

                            }
                            TedNaverClustering.with(mapContext, naverMap)
                                    .items(items)
                                   //마커 클릭 리스너
                                    .markerClickListener(new Function1<TedClusterItem, Unit>() {
                                                             @Override
                                                             public Unit invoke(TedClusterItem item) {
                                                                 House house = (House) item;
                                                                 Log.d(TAG, "invoke: house : " + house.getId());
                                                                 Intent intent = new Intent(mapContext, HouseDetailActivity.class);
                                                                 intent.putExtra("id", house.getId());
                                                                 intent.putExtra("lat", house.getLat());
                                                                 intent.putExtra("lng", house.getLng());
                                                                 startActivity(intent);
                                                                 return null;
                                                             }
                                                         }
                                    )
                                    .make();
                        } else {
                            Log.d(TAG, "onComplete: 실패");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
        return items;
    }


}