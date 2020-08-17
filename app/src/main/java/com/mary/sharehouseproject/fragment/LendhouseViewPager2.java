package com.mary.sharehouseproject.fragment;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mary.sharehouseproject.R;


public class LendhouseViewPager2 extends Fragment {
    private static final String TAG = "LendhouseViewPager2";

    private Button btnTrust;
    private TextView tvConsulting;
    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lendhouse_viewpage2,container,false);

        init(v);
        btnTrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Rect rect = new Rect();
                        Point p = new Point();
                        scrollView.getGlobalVisibleRect(rect, p);

                        int a []=new int[2];
                        tvConsulting.getLocationInWindow(a);
                        scrollView.smoothScrollBy(0,a[1]-p.y);


                    }
                },400);
            }
        });

        return v;
    }

    private void init(View v) {
        btnTrust = v.findViewById(R.id.btn_trust_banner1);
        tvConsulting = v.findViewById(R.id.tv_lendHouse_consult);
        scrollView = v.findViewById(R.id.scroll_trust1);
    }

}
