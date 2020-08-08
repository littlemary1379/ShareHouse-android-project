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

import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.adapter.FaqRecyclerAdapter;
import com.mary.sharehouseproject.model.Faq;


public class FaqViewPager1 extends Fragment {
    private static final String TAG = "FaqViewPager1";

    private FaqRecyclerAdapter recyclerAdapter;
    private RecyclerView rcFaq;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.f_a_q_viewpage1,container,false);

        init(v);
        initData();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        rcFaq.setLayoutManager(layoutManager);
        rcFaq.setAdapter(recyclerAdapter);

        return v;
    }

    private void initData(){
        recyclerAdapter.addFaqList(new Faq("입주신청서를 제출했습니다. 이후 과정은 어떻게 되나요?","--"));
        recyclerAdapter.addFaqList(new Faq("보증금과 월세 외에 다른 비용에는 무엇이 있나요?","--"));
    }

    private void init(View v){
        rcFaq=v.findViewById(R.id.rc_faq);
        recyclerAdapter=new FaqRecyclerAdapter();

    }
}
