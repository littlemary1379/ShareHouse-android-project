package com.mary.sharehouseproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.activity.LoginActivity;
import com.mary.sharehouseproject.activity.SearchActivity;


public class HouseManualViewPager3 extends Fragment {
    private Button btnSearchRoom, btnGetJoin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.housemanual_viewpage3,container,false);
        init(v);
        listener();
        return v;
    }

    private void init(View v){
        btnGetJoin=v.findViewById(R.id.btn_getJoin);
        btnSearchRoom=v.findViewById(R.id.btn_searchRoom);
    }

    private void listener(){
        btnSearchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        btnGetJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

}
