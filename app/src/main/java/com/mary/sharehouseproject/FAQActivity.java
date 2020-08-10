package com.mary.sharehouseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mary.sharehouseproject.adapter.FaqFragAdapter;
import com.mary.sharehouseproject.adapter.FaqRecyclerAdapter;
import com.mary.sharehouseproject.adapter.HouseManualFragAdapter;
import com.mary.sharehouseproject.fragment.FaqViewPager1;
import com.mary.sharehouseproject.fragment.FaqViewPager2;
import com.mary.sharehouseproject.fragment.HouseManualViewPager1;
import com.mary.sharehouseproject.fragment.HouseManualViewPager2;
import com.mary.sharehouseproject.fragment.HouseManualViewPager3;
import com.mary.sharehouseproject.model.Faq;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

public class FAQActivity extends AppCompatActivity {

    private FaqFragAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView rvFaq;


    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext=FAQActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        init();
        initToolbar();
        setSupportActionBar(toolbar);
        setupToolbarNavigationView();

        adapter.addFragment(new FaqViewPager1());
        adapter.addFragment(new FaqViewPager2());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("입주 관련");
        tabLayout.getTabAt(1).setText("계약 관련");


    }

    private void init(){
        adapter=new FaqFragAdapter(getSupportFragmentManager(),1);
        tabLayout = findViewById(R.id.layout_Faq_Tab);
        viewPager = findViewById(R.id.viewpager_Faq);
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar(){
        logoText=findViewById(R.id.tv_logoText);
        ivHamburgerButton=findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton=findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout=findViewById(R.id.layout_Faq_Drawer);
        toolbar=findViewById(R.id.toolbar_main);
        mainNavigationView=findViewById(R.id.navigation);
    }

    //툴바 리스너
    private void setupToolbarNavigationView(){
        ToolbarNavigationHelper.enableNavigationHelper(mContext,mainNavigationView,mainDrawerLayout,logoText,ivHamburgerButton,ivToolbarSearchButton);
    }


}