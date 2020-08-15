package com.mary.sharehouseproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.User;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Button btnJoin;
    private EditText etEmail, etPassword;
    private String email, password = null;
    private FirebaseFirestore db;
    private User user;


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

        init();
        initToolbar();
        Listener();
        setSupportActionBar(toolbar);
        setupToolbarNavigationView();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = mAuth.getCurrentUser();
        Log.d(TAG, "onStart: " + firebaseUser);
    }

    private void createEmail(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: 가입됨");
                            firebaseUser = mAuth.getCurrentUser();
                            registUser();
                        } else {
                            Log.d(TAG, "onComplete: 가입 안됨.." + task.getException());
                            Toast.makeText(mContext, "가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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

    private void init() {
        btnJoin = findViewById(R.id.btn_join);
        etEmail = findViewById(R.id.et_login_id);
        etPassword = findViewById(R.id.et_login_pw);
    }

    private void Listener() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString();

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 회원가입 버튼 눌리나?");
                createEmail(etEmail.getText().toString().trim(), etPassword.getText().toString());
            }
        });

    }

    private void registUser() {
        user=User.builder()
                .email(etEmail.getText().toString().trim())
                .role("임차인")
                .build();
        db.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: user 성공");
                        Intent mainIntent = new Intent(mContext, MainActivity.class);
                        startActivity(mainIntent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
    }

}