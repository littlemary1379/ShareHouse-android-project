package com.mary.sharehouseproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.User;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

import java.util.concurrent.CountDownLatch;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Button btnJoin, btnLogin;
    private EditText etEmail, etPassword, etAccount;
    private String email, password = null;
    private FirebaseFirestore db;
    private User user;
    private TextView alertText;
    private ConstraintLayout accountLayout;


    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton,ivLogoutButton;
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

    //툴바용 전역변수에 값 부여
    private void initToolbar() {
        logoText = findViewById(R.id.tv_logoText);
        ivHamburgerButton = findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton = findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout = findViewById(R.id.layout_login_drawer);
        toolbar = findViewById(R.id.toolbar_main);
        mainNavigationView = findViewById(R.id.navigation);
        ivLogoutButton=findViewById(R.id.iv_logoutButton);

    }

    //툴바 리스너
    private void setupToolbarNavigationView() {
        ToolbarNavigationHelper.enableNavigationHelper(mContext, mainNavigationView, mainDrawerLayout, logoText, ivHamburgerButton, ivToolbarSearchButton,ivLogoutButton);
    }

    private void init() {
        alertText = findViewById(R.id.tv_alert_email);
        btnJoin = findViewById(R.id.btn_join);
        btnLogin = findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.et_login_id);
        etPassword = findViewById(R.id.et_login_pw);
        etAccount = findViewById(R.id.et_login_account);
        accountLayout = findViewById(R.id.layout_login_account);
    }

    private void Listener() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString();

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    SelectUser();
                }
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 회원가입 버튼 눌리나?");
                createEmail(etEmail.getText().toString().trim(), etPassword.getText().toString());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 로그인 버튼 클릭되니?");
                loginEmail(etEmail.getText().toString().trim(), etPassword.getText().toString());
            }
        });

    }

    private void createEmail(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: 가입됨");
                            firebaseUser = mAuth.getCurrentUser();
                            RegistUser();
                        } else {
                            Log.d(TAG, "onComplete: 가입 안됨.." + task.getException());
                            Toast.makeText(mContext, "가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void loginEmail(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: 로그인 성공");
                            Intent mainIntent = new Intent(mContext, MainActivity.class);
                            startActivity(mainIntent);

                        }else{
                            Log.d(TAG, "onComplete: 로그인 실패");
                        }
                    }
                });
    }

    private void RegistUser() {
        if (etAccount.getText().toString() == null || etAccount.getText().toString().equals("")) {
            user = User.builder()
                    .email(etEmail.getText().toString().trim())
                    .role("세입자")
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
        } else {
            user = User.builder()
                    .email(etEmail.getText().toString().trim())
                    .role("임대인")
                    .account(etAccount.getText().toString())
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

    private void SelectUser() {
        Log.d(TAG, "onFocusChange: 포커스 사라짐");
        final CountDownLatch latch = new CountDownLatch(1);
        Task<QuerySnapshot> getTask = db.collection("user").whereEqualTo("email", etEmail.getText().toString().trim()).get();
        getTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult() == null) {
                    Log.d(TAG, "onComplete: 여기서 처리해야해?");
                }
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, "onComplete: " + document.getData());
                        UpdateUiHasEmail();
                        latch.countDown();
                        return;
                    }
                } else {
                    Log.d(TAG, "onComplete: 비어서 안되는건가?");
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: SelectUser" + e.getMessage());
                    }
                });

        UpdateUiNoEmail();

    }

    private synchronized void UpdateUiHasEmail() {
        alertText.setVisibility(View.VISIBLE);
        alertText.setText("이메일이 있습니다.  로그인을 진행합니다.");
        btnJoin.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        accountLayout.setVisibility(View.GONE);
    }

    private synchronized void UpdateUiNoEmail() {
        alertText.setVisibility(View.VISIBLE);
        if (etEmail.getText().toString() == null || etEmail.getText().toString().equals("")) {
            alertText.setText("이메일을 입력해주세요,");
        } else {
            alertText.setText("이메일이 없습니다. 회원가입을 진행합니다.");
            accountLayout.setVisibility(View.VISIBLE);


        }
    }

}

