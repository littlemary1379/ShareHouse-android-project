package com.mary.sharehouseproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.model.Interview;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private EditText etSearch;
    private CarouselView carouselView;
    private FirebaseFirestore db;
    private List<Interview> interviewList = new ArrayList<>();
    private Interview interview;
    private ImageView ivCarousal;
    private TextView tvCarousalTitle, tvCarousalInterviewee;


    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        initToolbar();
        init();
        initData();
        listener();
        setSupportActionBar(toolbar);
        setupToolbarNavigationView();
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar() {
        logoText = findViewById(R.id.tv_logoText);
        ivHamburgerButton = findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton = findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout = findViewById(R.id.layout_mainDrawer);
        toolbar = findViewById(R.id.toolbar_main);
        mainNavigationView = findViewById(R.id.navigation);
        ivLogoutButton = findViewById(R.id.iv_logoutButton);
    }

    //툴바 리스너
    private void setupToolbarNavigationView() {
        ToolbarNavigationHelper.enableNavigationHelper(mContext, mainNavigationView, mainDrawerLayout, logoText, ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton);
    }

    private void init() {
        etSearch = findViewById(R.id.et_search);
        carouselView = (CarouselView) findViewById(R.id.customCarouselView);
    }

    private void listener() {
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == true) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        db.collection("interview").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                interview = document.toObject(Interview.class);
                                interviewList.add(interview);

                            }
                            setImageListener();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
    }

    private void setImageListener() {

        carouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(final int position) {

                View v=getLayoutInflater().inflate(R.layout.carousel_item,null);
                ivCarousal=v.findViewById(R.id.iv_carousal);
                tvCarousalTitle=v.findViewById(R.id.tv_carousel_title);
                tvCarousalInterviewee=v.findViewById(R.id.tv_carousel_interviewee);
                Glide.with(mContext).load(interviewList.get(position).getImageUri()).into(ivCarousal);
                tvCarousalTitle.setText(interviewList.get(position).getTitle());
                tvCarousalInterviewee.setText(interviewList.get(position).getInterviewee());
                tvCarousalTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(interviewList.get(position).getBlogUri()));
                        startActivity(intent);
                    }
                });
                return v;
            }
        });
        carouselView.setPageCount(interviewList.size());

    }
}
