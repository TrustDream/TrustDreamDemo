package com.trust.rxjavademo.QuanXian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.trust.rxjavademo.R;

public class TipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
    }

    public void close (View view){
        finish();
    }
}
