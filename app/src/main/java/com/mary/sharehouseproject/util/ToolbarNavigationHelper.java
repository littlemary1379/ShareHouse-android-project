package com.mary.sharehouseproject.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.mary.sharehouseproject.FAQActivity;
import com.mary.sharehouseproject.LendhouseActivity;
import com.mary.sharehouseproject.LoginActivity;
import com.mary.sharehouseproject.MainActivity;
import com.mary.sharehouseproject.MapActivity;
import com.mary.sharehouseproject.MoveInActivity;
import com.mary.sharehouseproject.MypageActivity;
import com.mary.sharehouseproject.R;

public class ToolbarNavigationHelper {
    private static final String TAG = "ToolbarNavigationHelper";


    public static void enableNavigationHelper(final Context context, NavigationView view, final DrawerLayout drawerLayout, TextView logoText, final ImageView hamburgerButton, final ImageView searchButton){

        logoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0=new Intent(context, MainActivity.class);
                intent0.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent0);
            }
        });

        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 햄버거 버튼 클릭됨");
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MapActivity.class);
                context.startActivity(intent);


            }
        });

        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_housemenual:
                        Intent intent1=new Intent(context, MoveInActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent1);

                        break;
                    case R.id.menu_login_and_join:
                        Intent intent2=new Intent(context,LoginActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent2);
                        break;

                    case R.id.menu_faq:
                        Intent intent3=new Intent(context, FAQActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent3);
                        break;

                    case R.id.menu_lendHouse:
                        Intent intent4=new Intent(context, LendhouseActivity.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent4);
                        break;
            }

            return false;
            }
        });
    }

}
