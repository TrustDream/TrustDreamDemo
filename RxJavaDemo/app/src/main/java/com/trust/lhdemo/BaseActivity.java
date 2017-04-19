package com.trust.lhdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.trust.lhdemo.view.StatusBar;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initBase();
    }

    public void initBase() {
        StatusBar.setColor(this, Color.parseColor("#303F9F"));
    }

    @Override
    public void onClick(View view) {

    }
}
