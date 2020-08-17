package com.mary.sharehouseproject.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.InfoWindow;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import lombok.NonNull;
import ted.gun0912.clustering.clustering.TedClusterItem;
import ted.gun0912.clustering.naver.TedNaverClustering;


public class MapActivity extends BaseDemoActivity {
    private static final String TAG = "MapActivity";
    private NaverMap naverMap;
    private FirebaseFirestore db;

    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mapContext=MapActivity.this;

    @Override
    public  void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        naverMap.moveCamera(
                CameraUpdate.toCameraPosition(
                        new CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, NaverMap.DEFAULT_CAMERA_POSITION.zoom))
        );
        initToolbar();
        setupToolbarNavigationView();
        getItems();

    }

    //툴바용 전역변수에 값 부여
    private void initToolbar(){
        logoText=findViewById(R.id.tv_logoText);
        ivHamburgerButton=findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton=findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout=findViewById(R.id.layout_mypage_Drawer);
        toolbar=findViewById(R.id.toolbar_main);
        mainNavigationView=findViewById(R.id.navigation);
        ivLogoutButton=findViewById(R.id.iv_logoutButton);

    }

    //툴바 리스너
    private void setupToolbarNavigationView(){
        ToolbarNavigationHelper.enableNavigationHelper(mapContext,mainNavigationView,mainDrawerLayout,logoText,ivHamburgerButton,ivToolbarSearchButton,ivLogoutButton);
    }




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
                            Log.d(TAG, "onComplete: ");
                            TedNaverClustering.with(mapContext, naverMap)
                                    .items(items)
                                    .markerClickListener(new Function1<TedClusterItem, Unit>() {
                                                             @Override
                                                             public Unit invoke(TedClusterItem item) {
                                                                 House house=(House)item;
                                                                 Log.d(TAG, "invoke: house : "+house.getId());
                                                                 Intent intent=new Intent(mapContext, HouseDetailActivity.class);
                                                                 intent.putExtra("id",house.getId());
                                                                 intent.putExtra("lat",house.getLat());
                                                                 intent.putExtra("lng",house.getLng());
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