package com.mary.sharehouseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.daum.android.map.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private ImageView ivHamburgerButton;
    private DrawerLayout mainDrawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setSupportActionBar(toolbar);
        initListener();
    }

    private void init(){
        ivHamburgerButton=findViewById(R.id.iv_hamburgerButton);
        mainDrawerLayout=findViewById(R.id.layout_mainDrawer);
        toolbar=findViewById(R.id.toolbar_main);
    }

    private void initListener(){
        ivHamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 햄버거 버튼 클릭됨");
                mainDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
