package com.mary.sharehouseproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.adapter.SearchRecyclerAdaper;
import com.mary.sharehouseproject.model.House;
import com.mary.sharehouseproject.model.HouseDetail;
import com.mary.sharehouseproject.model.dto.SearchHouseDto;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext=SearchActivity.this;

    private ConstraintLayout constMap, constUniversity, constSubway;
    private RecyclerView rcSearch;
    private SearchRecyclerAdaper searchRecyclerAdaper=new SearchRecyclerAdaper();
    private TextView tvMap,tvUniversity,tvSubway;
    private ImageView ivMap, ivUniversity, ivSubway, ivEmptyText, ivSearch;
    private EditText etSearchKeyword;
    private String searchKeyword="지역";
    private String databasePath;
    private String address;
    private int houseId;
    private SearchHouseDto searchHouseDto, searchHouseDtoByPos;
    private List<SearchHouseDto> searchHouseDtoList=new ArrayList<>();
    private Handler handler=new Handler();

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        init();
        listener();

        setSupportActionBar(toolbar);
        setupToolbarNavigationView();
        db=FirebaseFirestore.getInstance();
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar(){
        logoText=findViewById(R.id.tv_logoText);
        ivHamburgerButton=findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton=findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout=findViewById(R.id.layout_Search_Drawer);
        toolbar=findViewById(R.id.toolbar_main);
        mainNavigationView=findViewById(R.id.navigation);
        ivLogoutButton=findViewById(R.id.iv_logoutButton);
    }

    //툴바 리스너
    private void setupToolbarNavigationView(){
        ToolbarNavigationHelper.enableNavigationHelper(mContext,mainNavigationView,mainDrawerLayout,logoText,ivHamburgerButton,ivToolbarSearchButton,ivLogoutButton);
    }

    private void init(){
        constMap=findViewById(R.id.layout_const_map);
        constUniversity=findViewById(R.id.layout_const_university);
        constSubway=findViewById(R.id.layout_const_subway);

        rcSearch=findViewById(R.id.rc_search);

        tvMap=findViewById(R.id.tv_map);
        tvUniversity=findViewById(R.id.tv_university);
        tvSubway=findViewById(R.id.tv_subway);

        ivMap=findViewById(R.id.iv_map);
        ivUniversity=findViewById(R.id.iv_university);
        ivSubway=findViewById(R.id.iv_subway);
        ivEmptyText=findViewById(R.id.iv_emptyText);
        ivSearch=findViewById(R.id.iv_search);

        etSearchKeyword=findViewById(R.id.et_search_keyword);
    }

    //검색 로직 : 하위 컬렉션 리스트에서 일치하는 값 도출 -> 주소 전역변수에 넣기 + 하위 컬렉션의 경로를 split 해서 상위 경로의 방 호수 도출
    // -> 상위 경로의 id, 방 호수, LatLng를 ArrayList로 만들어서 RecyclerView에 넣기 -> ClickListener을 통해 상세 페이지 inflater 이동
    private  void listener(){
        constMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: constMap 클릭");
                tvMap.setTextColor(Color.parseColor("#FFFFFF"));
                tvUniversity.setTextColor(Color.parseColor("#D14844"));
                tvSubway.setTextColor(Color.parseColor("#D14844"));

                ivMap.setImageResource(R.drawable.ic_map_white);
                ivUniversity.setImageResource(R.drawable.ic_school_darkchorale);
                ivSubway.setImageResource(R.drawable.ic_subway_darkchorale);

                etSearchKeyword.setHint("시,구,동으로 검색하세요 ex) 서울 중구 신당");

                searchKeyword="지역";
            }
        });
        constUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: constUniversity 클릭");
                tvMap.setTextColor(Color.parseColor("#D14844"));
                tvUniversity.setTextColor(Color.parseColor("#FFFFFF"));
                tvSubway.setTextColor(Color.parseColor("#D14844"));

                ivMap.setImageResource(R.drawable.ic_map_darkchorale);
                ivUniversity.setImageResource(R.drawable.ic_school_white);
                ivSubway.setImageResource(R.drawable.ic_subway_darkchorale);

                etSearchKeyword.setHint("대학 이름으로 검색하세요 ex)동국대학교");

                searchKeyword="대학교";
            }
        });
        constSubway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: constSubway 클릭");
                tvMap.setTextColor(Color.parseColor("#D14844"));
                tvUniversity.setTextColor(Color.parseColor("#D14844"));
                tvSubway.setTextColor(Color.parseColor("#FFFFFF"));

                ivMap.setImageResource(R.drawable.ic_map_darkchorale);
                ivUniversity.setImageResource(R.drawable.ic_school_darkchorale);
                ivSubway.setImageResource(R.drawable.ic_subway_white);

                etSearchKeyword.setHint("지하철역으로 검색하세요 ex)청구역");

                searchKeyword="지하철";
            }
        });

        ivEmptyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearchKeyword.setText("");
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initThread();
                if(searchKeyword.equals("지역")){
                    Log.d(TAG, "onClick: "+searchKeyword);

                    db.collectionGroup("detail").whereEqualTo("address",etSearchKeyword.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot document:task.getResult()){
                                            databasePath=document.getReference().getPath();
                                            HouseDetail houseDetail=document.toObject(HouseDetail.class);
                                            address=houseDetail.getAddress();
                                            //Log.d(TAG, "onComplete: "+databasePath);
                                            getHouseId(databasePath);
                                        }
                                    }else{
                                        Log.d(TAG, "onComplete: 실패함");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: 실패");
                            e.printStackTrace();
                        }
                    });
                }else if(searchKeyword.equals("대학교")) {
                    Log.d(TAG, "onClick: "+searchKeyword);

                    db.collectionGroup("detail").whereEqualTo("university",etSearchKeyword.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot document:task.getResult()){
                                            databasePath=document.getReference().getPath();
                                            HouseDetail houseDetail=document.toObject(HouseDetail.class);
                                            address=houseDetail.getAddress();
                                            //Log.d(TAG, "onComplete: "+databasePath);
                                            getHouseId(databasePath);
                                        }
                                    }else{
                                        Log.d(TAG, "onComplete: 실패함");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: 실패");
                            e.printStackTrace();
                        }
                    });

                }else if(searchKeyword.equals("지하철")){
                    Log.d(TAG, "onClick: "+searchKeyword);

                    Log.d(TAG, "onClick: "+searchKeyword);

                    db.collectionGroup("detail").whereEqualTo("subway",etSearchKeyword.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot document:task.getResult()){
                                            databasePath=document.getReference().getPath();
                                            HouseDetail houseDetail=document.toObject(HouseDetail.class);
                                            address=houseDetail.getAddress();
                                            //Log.d(TAG, "onComplete: "+databasePath);
                                            getHouseId(databasePath);
                                        }
                                    }else{
                                        Log.d(TAG, "onComplete: 실패함");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: 실패");
                            e.printStackTrace();
                        }
                    });

                }
            }
        });
    }
    private void getHouseId(final String databasePath){
        String[] pathArray=databasePath.split("/");
        String path1=pathArray[0];
        String path2=pathArray[1];
        //Log.d(TAG, "getHouseId: "+path1+"&"+path2);
        db.collection(path1).document(path2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "onSuccess: 성공"+documentSnapshot.getData());
                        House house=documentSnapshot.toObject(House.class);

                        searchHouseDto=new SearchHouseDto(house.getId(),house.getHouseNumber(), address,house.getLat(),house.getLng());
                        searchHouseDtoList.add(searchHouseDto);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패"+e.getMessage());
            }
        });

    }

    private void initThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    searchRecyclerAdaper.addList(searchHouseDtoList);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
                            rcSearch.setLayoutManager(layoutManager);
                            rcSearch.setAdapter(searchRecyclerAdaper);
                        }
                    });
                    searchRecyclerAdaper.setOnItemClickListener(new SearchRecyclerAdaper.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int pos) {
                            //Log.d(TAG, "onItemClick: "+pos);
                            searchHouseDtoByPos=searchHouseDtoList.get(pos);
                            Log.d(TAG, "onItemClick: "+searchHouseDtoByPos);
                            startHouseDetailActivity(searchHouseDtoByPos);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void startHouseDetailActivity(SearchHouseDto searchHouseDtoByPos){
        Intent intent=new Intent(mContext,HouseDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("id",searchHouseDtoByPos.getId());
        intent.putExtra("lat",searchHouseDtoByPos.getLat());
        intent.putExtra("lng",searchHouseDtoByPos.getLng());
        startActivity(intent);
    }
}