package com.mary.sharehouseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mary.sharehouseproject.Adapter.HouseManualFragAdapter;
import com.mary.sharehouseproject.fragment.HouseManualViewPager1;
import com.mary.sharehouseproject.fragment.HouseManualViewPager2;
import com.mary.sharehouseproject.fragment.HouseManualViewPager3;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

public class MoveInActivity extends AppCompatActivity {
    private static final String TAG = "MoveInActivity";

    private HouseManualFragAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //툴바용 전역변수 설정
    private ImageView ivHamburgerButton, ivToolbarSearchButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext=MoveInActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_in);

        init();
        initToolbar();
        setSupportActionBar(toolbar);
        setupToolbarNavigationView();

        adapter.addFragment(new HouseManualViewPager1());
        adapter.addFragment(new HouseManualViewPager2());
        adapter.addFragment(new HouseManualViewPager3());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("입주 절차");
        tabLayout.getTabAt(1).setText("우주 문화");
        tabLayout.getTabAt(2).setText("제휴 서비스");


    }

    private void init(){
        adapter=new HouseManualFragAdapter(getSupportFragmentManager(),1);
        tabLayout = findViewById(R.id.layout_housemenual_tab);
        viewPager = findViewById(R.id.viewpager_housemenual);
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar(){
        ivHamburgerButton=findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton=findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout=findViewById(R.id.layout_move_in_Drawer);
        toolbar=findViewById(R.id.toolbar_main);
        mainNavigationView=findViewById(R.id.navigation);
    }

    //툴바 리스너
    private void setupToolbarNavigationView(){
        ToolbarNavigationHelper.enableNavigationHelper(mContext,mainNavigationView,mainDrawerLayout,ivHamburgerButton,ivToolbarSearchButton);
    }

}