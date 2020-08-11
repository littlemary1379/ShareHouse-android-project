package com.mary.sharehouseproject.fragment;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mary.sharehouseproject.R;

import org.w3c.dom.Text;


public class LendhouseViewPager1 extends Fragment {
    private static final String TAG = "LendhouseViewPager1";

    private ScrollView scrollView;
    private Button consultingButton;
    private TextView tvConsulting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lendhouse_viewpage1, container, false);

        init(v);
        consultingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //scrollView.setScrollY(a[1]-p.y);
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
        consultingButton = v.findViewById(R.id.btn_manageRental_banner1);
        tvConsulting = v.findViewById(R.id.tv_lendHouse_consult);
        scrollView = v.findViewById(R.id.scroll_lendhouse1);
    }
}
