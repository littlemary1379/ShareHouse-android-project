package com.mary.sharehouseproject.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mary.sharehouseproject.activity.FAQActivity;
import com.mary.sharehouseproject.activity.LendhouseActivity;
import com.mary.sharehouseproject.activity.LoginActivity;
import com.mary.sharehouseproject.activity.MainActivity;
import com.mary.sharehouseproject.activity.MapActivity;
import com.mary.sharehouseproject.activity.MoveInActivity;
import com.mary.sharehouseproject.R;

import org.w3c.dom.Text;

public class ToolbarNavigationHelper {
    private static final String TAG = "ToolbarNavigationHelper";
    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    private static Menu menu;


    public static void enableNavigationHelper(final Context context,
                                              NavigationView view,
                                              final DrawerLayout drawerLayout,
                                              TextView logoText,
                                              final ImageView hamburgerButton,
                                              final ImageView searchButton,
                                              final ImageView logoutButton){

        mAuth = FirebaseAuth.getInstance();
        menu = view.getMenu();
        firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser!=null){
            logoutButton.setVisibility(View.VISIBLE);
            View HeaderView=view.getHeaderView(0);
            TextView tvHeader=HeaderView.findViewById(R.id.tv_header);
            tvHeader.setText(firebaseUser.getEmail()+"님, \n반갑습니다!");


        }else{
            logoutButton.setVisibility(View.GONE);
        }

        logoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0=new Intent(context, MainActivity.class);
                intent0.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent0);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Logout")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent0=new Intent(context, MainActivity.class);
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "로그아웃을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.findItem(R.id.menu_mypage).setVisible(false);
                Log.d(TAG, "onClick: 햄버거 버튼 클릭됨");
                if(firebaseUser!=null){
                    menu.findItem(R.id.menu_login_and_join)
                            .setVisible(false);
                    menu.findItem(R.id.menu_mypage)
                            .setVisible(true);
                }

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
                drawerLayout.closeDrawer(Gravity.LEFT);
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

                    case R.id.menu_brand:
                        FirebaseAuth.getInstance().signOut();
            }

            return false;
            }
        });
    }

}
