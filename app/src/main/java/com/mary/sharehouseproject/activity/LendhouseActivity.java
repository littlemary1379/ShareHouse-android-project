package com.mary.sharehouseproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.adapter.LendhouseFragAdapter;
import com.mary.sharehouseproject.fragment.LendhouseViewPager1;
import com.mary.sharehouseproject.fragment.LendhouseViewPager2;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

public class LendhouseActivity extends AppCompatActivity {

    private LendhouseFragAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext=LendhouseActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lendhouse);
        init();
        initToolbar();
        setupToolbarNavigationView();

        adapter.addFragment(new LendhouseViewPager1());
        adapter.addFragment(new LendhouseViewPager2());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("직영임대");
        tabLayout.getTabAt(1).setText("위탁 맡기기");
    }

    private void init(){
        adapter=new LendhouseFragAdapter(getSupportFragmentManager(),1);
        tabLayout = findViewById(R.id.layout_lendhouse_tab);
        viewPager = findViewById(R.id.viewpager_lendhouse);
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar(){
        logoText=findViewById(R.id.tv_logoText);
        ivHamburgerButton=findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton=findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout=findViewById(R.id.layout_lendhouse_Drawer);
        toolbar=findViewById(R.id.toolbar_main);
        mainNavigationView=findViewById(R.id.navigation);
    }

    //툴바 리스너
    private void setupToolbarNavigationView(){
        ToolbarNavigationHelper.enableNavigationHelper(mContext,mainNavigationView,mainDrawerLayout,logoText,ivHamburgerButton,ivToolbarSearchButton);
    }
}