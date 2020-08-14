package com.mary.sharehouseproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Menu menu;

    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext = LoginActivity.this;
    private FirebaseUser firebaseUser;

    //구글 파이어베이스 인증용 전역변수 설정
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initToolbar();
        setSupportActionBar(toolbar);
        setupToolbarNavigationView();
        mAuth = FirebaseAuth.getInstance();
        menu = mainNavigationView.getMenu();


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = mAuth.getCurrentUser();
        Log.d(TAG, "onStart: " + firebaseUser);
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar() {
        logoText = findViewById(R.id.tv_logoText);
        ivHamburgerButton = findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton = findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout = findViewById(R.id.layout_login_drawer);
        toolbar = findViewById(R.id.toolbar_main);
        mainNavigationView = findViewById(R.id.navigation);
    }

    //툴바 리스너
    private void setupToolbarNavigationView() {
        ToolbarNavigationHelper.enableNavigationHelper(mContext, mainNavigationView, mainDrawerLayout, logoText, ivHamburgerButton, ivToolbarSearchButton);
    }
}