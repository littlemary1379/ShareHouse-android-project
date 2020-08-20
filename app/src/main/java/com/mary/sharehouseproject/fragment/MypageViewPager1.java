package com.mary.sharehouseproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.adapter.LendingHouseRecyclerAdapter;
import com.mary.sharehouseproject.model.LendHouse;

import java.util.ArrayList;
import java.util.List;


public class MypageViewPager1 extends Fragment {
    private static final String TAG = "MypageViewPager1";
    
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String userId;
    private LendHouse lendHouse;
    private List<LendHouse> lendHouseList=new ArrayList<>();

    private LendingHouseRecyclerAdapter adapter;
    private RecyclerView rcLendingHouse;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.mypage_viewpage1,container,false);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseUser=mAuth.getCurrentUser();

        init(v);
        initData();

        return v;
    }

    private void init(View v){
        adapter=new LendingHouseRecyclerAdapter();
        rcLendingHouse=v.findViewById(R.id.rc_lending_house);
    }

    private void initData(){
        db.collection("user").whereEqualTo("email",firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                userId=documentSnapshot.getId();
                                getHouseList(userId);
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

    private void getHouseList(String userId){
        db.collection("user/"+userId+"/lendHouse")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                lendHouse=documentSnapshot.toObject(LendHouse.class);
                                lendHouse=LendHouse.builder()
                                        .houseNumber(lendHouse.getHouseNumber())
                                        .imageUri(lendHouse.getImageUri())
                                        .address(lendHouse.getAddress())
                                        .roomId(documentSnapshot.getReference().getPath())
                                        .build();
                                Log.d(TAG, "onComplete: lendHouse : "+lendHouse);
                                lendHouseList.add(lendHouse);
                            }
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
                            adapter.addLendHouseList(lendHouseList);
                            rcLendingHouse.setLayoutManager(layoutManager);
                            rcLendingHouse.setAdapter(adapter);
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
}
