package com.mary.sharehouseproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

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
    private TextView tvMap,tvUniversity,tvSubway;
    private ImageView ivMap, ivUniversity, ivSubway, ivEmptyText, ivSearch;
    private EditText etSearchKeyword;
    private String searchKeyword="지역";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        init();
        listener();
        setSupportActionBar(toolbar);
        setupToolbarNavigationView();
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
                if(searchKeyword.equals("지역")){
                    Log.d(TAG, "onClick: "+searchKeyword);

                }else if(searchKeyword.equals("대학교")) {
                    Log.d(TAG, "onClick: "+searchKeyword);


                }else if(searchKeyword.equals("지하철")){
                    Log.d(TAG, "onClick: "+searchKeyword);

                }
            }
        });
    }
}