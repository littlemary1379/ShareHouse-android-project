package com.mary.sharehouseproject.fragment;

import android.os.Bundle;
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


public class FaqViewPager2 extends Fragment {
    private FaqRecyclerAdapter recyclerAdapter;
    private RecyclerView rcFaq;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_a_q_viewpage2, container, false);
        init(v);
        initData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcFaq.setLayoutManager(layoutManager);
        rcFaq.setAdapter(recyclerAdapter);

        return v;
    }

    private void initData() {
        recyclerAdapter.addFaqList(new Faq("계약기간은 어떻게 되나요?", "--"));
        recyclerAdapter.addFaqList(new Faq("입주일은 어떻게 정해지나요?", "--"));
    }

    private void init(View v) {
        rcFaq = v.findViewById(R.id.rc_faq);
        recyclerAdapter = new FaqRecyclerAdapter();

    }
}
