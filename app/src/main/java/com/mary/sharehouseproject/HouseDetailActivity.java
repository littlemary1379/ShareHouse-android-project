package com.mary.sharehouseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HouseDetailActivity extends AppCompatActivity {
    private static final String TAG = "HouseDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        Intent intent=getIntent();
        Integer intentId= intent.getExtras().getInt("id");

        Log.d(TAG, "onCreate: "+intent.getExtras().getInt("id"));

        TextView testTextView=findViewById(R.id.testing_textView);
        testTextView.setText(intentId+"");
    }
}