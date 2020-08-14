package com.mary.sharehouseproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.adapter.FaqRecyclerAdapter;
import com.mary.sharehouseproject.model.Faq;

import java.util.ArrayList;
import java.util.List;


public class FaqViewPager1 extends Fragment {
    private static final String TAG = "FaqViewPager1";

    private FaqRecyclerAdapter recyclerAdapter;
    private RecyclerView rcFaq;
    private List<Faq> faqList=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.f_a_q_viewpage1,container,false);

        init(v);
        initData();
        Log.d(TAG, "onCreateView: 아니 여긴 오긴 해?");
        return v;
    }

    private void initData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("/FAQ/moveIn/list/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                faqList.add(document.toObject(Faq.class));
                            }
                            Log.d(TAG, "onComplete: 데이터 반영 완료"+faqList);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
                            recyclerAdapter.addFaqList(faqList);
                            rcFaq.setLayoutManager(layoutManager);
                            recyclerAdapter.notifyDataSetChanged();
                            rcFaq.setAdapter(recyclerAdapter);
                        }else{
                            Log.d(TAG, "onComplete: 실패");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }
        });
    }

    private void init(View v){
        rcFaq=v.findViewById(R.id.rc_faq);
        recyclerAdapter=new FaqRecyclerAdapter();

    }
}
