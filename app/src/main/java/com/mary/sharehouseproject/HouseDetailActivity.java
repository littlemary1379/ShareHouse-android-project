package com.mary.sharehouseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.mary.sharehouseproject.model.House;
import com.mary.sharehouseproject.model.HouseDetail;
import com.mary.sharehouseproject.util.ToolbarNavigationHelper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HouseDetailActivity extends AppCompatActivity {
    private static final String TAG = "HouseDetailActivity";

    private ImageView mainImageView;
    private FirebaseFirestore db;
    private String documentId;
    private HouseDetail houseDetail;
    private House house;
    private TextView tvContent, tvHashTag,tvAddress,tvGender,tvContractEndDate,tvMaxPerson,tvHouseForm,tvConstruction;

    //툴바용 전역변수 설정
    private TextView logoText;
    private ImageView ivHamburgerButton, ivToolbarSearchButton, ivLogoutButton;
    private DrawerLayout mainDrawerLayout;
    private NavigationView mainNavigationView;
    private Toolbar toolbar;
    private Context mContext= HouseDetailActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);


        db = FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        Integer intentId= intent.getExtras().getInt("id");

        Log.d(TAG, "onCreate: "+intent.getExtras().getInt("id"));

        init();
        initToolbar();
        firebaseId(intentId);
        setupToolbarNavigationView();


    }

    private void init(){
        mainImageView=findViewById(R.id.iv_houseImg);
        tvContent=findViewById(R.id.tv_content);
        tvHashTag=findViewById(R.id.tv_hashTag);
        tvAddress=findViewById(R.id.tv_address);
        tvGender=findViewById(R.id.tv_gender);
        tvContractEndDate=findViewById(R.id.tv_contractEndDate);
        tvMaxPerson=findViewById(R.id.tv_maxPerson);
        tvHouseForm=findViewById(R.id.tv_houseForm);
        tvConstruction=findViewById(R.id.tv_construction);
    }

    //툴바용 전역변수에 값 부여
    private void initToolbar(){
        logoText=findViewById(R.id.tv_logoText);
        ivHamburgerButton=findViewById(R.id.iv_hamburgerButton);
        ivToolbarSearchButton=findViewById(R.id.iv_toolbarSearchButton);
        mainDrawerLayout=findViewById(R.id.layout_houseDetail_Drawer);
        toolbar=findViewById(R.id.toolbar_main);
        mainNavigationView=findViewById(R.id.navigation);
        ivLogoutButton=findViewById(R.id.iv_logoutButton);

    }

    //툴바 리스너
    private void setupToolbarNavigationView(){
        ToolbarNavigationHelper.enableNavigationHelper(mContext,mainNavigationView,mainDrawerLayout,logoText,ivHamburgerButton,ivToolbarSearchButton,ivLogoutButton);
    }

    private void firebaseId(int houseId){
        db.collection("house").whereEqualTo("id",houseId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "onComplete: firebaseId : "+document.getId());
                                house=document.toObject(House.class);
                                documentId=document.getId();
                                firebaseData(documentId,house);
                            }
                        }else{
                            Log.d(TAG, "onComplete: 실패");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
    }
    
    private void firebaseData(final String documentId, final House house){
        db.collection("house/"+documentId+"/detail")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "onComplete: firebaseData :" + document.getData());
                                houseDetail=document.toObject(HouseDetail.class);
                                UpdateUI(houseDetail,house);
                            }
                        }else{
                            Log.d(TAG, "onComplete: 실패");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
    }

    private void UpdateUI(HouseDetail houseDetail,House house){
        Glide.with(this).load(houseDetail.getImageUri()).into(mainImageView);
        tvContent.setText(houseDetail.getContent());
        tvHashTag.setText(houseDetail.getHashTag());
        tvAddress.setText(houseDetail.getAddress());
        tvGender.setText(house.getGender());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        tvContractEndDate.setText(dateFormat.format(houseDetail.getContractEndDate().toDate()));
        tvMaxPerson.setText(houseDetail.getMaxPerson()+"명");
        tvHouseForm.setText(house.getHouseForm());
        tvConstruction.setText(houseDetail.getConstruction());
    }

}