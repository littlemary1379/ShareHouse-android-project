package com.mary.sharehouseproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mary.sharehouseproject.R;


public class LendhouseViewPager2 extends Fragment {
    private static final String TAG = "LendhouseViewPager2";

    private ImageView ivAdventage1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lendhouse_viewpage2,container,false);


        return v;
    }

}
