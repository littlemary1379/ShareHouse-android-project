package com.mary.sharehouseproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.adapter.RoomRecyclerAdapter;
import com.mary.sharehouseproject.model.House;
import com.mary.sharehouseproject.model.HouseDetail;
import com.mary.sharehouseproject.model.Room;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ted.gun0912.clustering.naver.TedNaverClustering;


public class HouseDetailActivity extends BaseMapDetailActivity {
    private static final String TAG = "HouseDetailActivity";

    private NaverMap naverMap;
    private ImageView mainImageView, ivConstImg;
    private FirebaseFirestore db;
    private String documentId, detailId;
    private Integer intentId;
    private double intentLat,intentLng;
    private HouseDetail houseDetail;
    private House house;
    private Room room;
    private List<Room> roomList;
    private TextView tvContent, tvHashTag, tvAddress, tvGender, tvContractEndDate, tvMaxPerson, tvHouseForm, tvConstruction, tvAddLink;
    private RecyclerView rcRoom;
    private RoomRecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    protected CameraPosition cameraPosition;


    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext = HouseDetailActivity.this;

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        intentId = intent.getExtras().getInt("id");
        intentLat = intent.getExtras().getDouble("lat");
        intentLng = intent.getExtras().getDouble("lng");

        Log.d(TAG, "onCreate: " + intent.getExtras().getInt("id"));

        init();
        initToolbar();
        Listener();
        firebaseId(intentId);
        setupToolbarNavigationView();


        cameraPosition = new CameraPosition(
                new LatLng(intentLat, intentLng),
                16
        );

        naverMap.setCameraPosition(cameraPosition);

        TedNaverClustering.with(mContext,naverMap)
                .item(getItem(intentLat,intentLng))
                .make();

    }

    private House getItem(double lat, double lng){
        House intentHouse=House.builder()
                .lat(lat)
                .lng(lng)
                .build();
        return intentHouse;
    }


    private void init() {
        roomList = new ArrayList<>();
        rcRoom = findViewById(R.id.rc_room);
        recyclerAdapter = new RoomRecyclerAdapter();
        layoutManager = new LinearLayoutManager(mContext);
        mainImageView = findViewById(R.id.iv_houseImg);
        tvContent = findViewById(R.id.tv_content);
        tvHashTag = findViewById(R.id.tv_hashTag);
        tvAddress = findViewById(R.id.tv_address);
        tvGender = findViewById(R.id.tv_gender);
        tvContractEndDate = findViewById(R.id.tv_contractEndDate);
        tvMaxPerson = findViewById(R.id.tv_maxPerson);
        tvHouseForm = findViewById(R.id.tv_houseForm);
        tvConstruction = findViewById(R.id.tv_construction);
        ivConstImg = findViewById(R.id.iv_constImg);
        tvAddLink = findViewById(R.id.tv_addLink);
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar() {
        logoText = findViewById(R.id.tv_logoText);
        ivHamburgerButton = findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton = findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout = findViewById(R.id.layout_houseDetail_Drawer);
        toolbar = findViewById(R.id.toolbar_main);
        mainNavigationView = findViewById(R.id.navigation);
        ivLogoutButton = findViewById(R.id.iv_logoutButton);

    }

    private void Listener() {
        tvAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://blog.naver.com/woozoo_ok/221060171989"));
                startActivity(intent);
            }
        });
    }

    //툴바 리스너
    private void setupToolbarNavigationView() {
        ToolbarNavigationHelper.enableNavigationHelper(mContext, mainNavigationView, mainDrawerLayout, logoText, ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton);
    }

    private void firebaseId(int houseId) {
        db.collection("house").whereEqualTo("id", houseId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "onComplete: firebaseId : " + document.getId());
                                house = document.toObject(House.class);
                                documentId = document.getId();
                                firebaseData(documentId, house);
                            }
                        } else {
                            Log.d(TAG, "onComplete: 실패");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
    }

    private void firebaseData(final String documentId, final House house) {
        db.collection("house/" + documentId + "/detail")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "onComplete: firebaseData :" + document.getData());
                                houseDetail = document.toObject(HouseDetail.class);
                                UpdateUI(houseDetail, house);
                                detailId = document.getId();
                                initRoomData(documentId, detailId);
                            }
                        } else {
                            Log.d(TAG, "onComplete: 실패");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
    }

    private void UpdateUI(HouseDetail houseDetail, House house) {
        Glide.with(this).load(houseDetail.getImageUri()).into(mainImageView);
        tvContent.setText(houseDetail.getContent());
        tvHashTag.setText(houseDetail.getHashTag());
        tvAddress.setText(houseDetail.getAddress());
        tvGender.setText(house.getGender());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tvContractEndDate.setText(dateFormat.format(houseDetail.getContractEndDate().toDate()));
        tvMaxPerson.setText(houseDetail.getMaxPerson() + "명");
        tvHouseForm.setText(house.getHouseForm());
        tvConstruction.setText(houseDetail.getConstruction());
        Glide.with(this).load(houseDetail.getConstImg()).into(ivConstImg);
    }

    private void initRoomData(final String documentId, String detailId) {
        db.collection("house/" + documentId + "/detail/" + detailId + "/room")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "onComplete: " + document.getData());
                                room = document.toObject(Room.class);
                                roomList.add(room);
                            }
                            updateRoomUI(roomList);
                        } else {
                            Log.d(TAG, "onComplete : initRoomData : 실패");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: initRoomData : 실패");
            }
        });
    }

    private void updateRoomUI(List<Room> roomList) {
        recyclerAdapter.addRoomList(roomList);
        rcRoom.setLayoutManager(layoutManager);
        rcRoom.setAdapter(recyclerAdapter);
    }

}

