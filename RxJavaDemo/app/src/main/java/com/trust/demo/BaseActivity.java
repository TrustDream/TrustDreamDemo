package com.trust.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.trust.demo.tool.StatusBar;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected StatusBar statusBar;
    protected Context context = BaseActivity.this;
    protected Activity activity = BaseActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        statusBar = new StatusBar();
        initBase();
    }

    protected void initViews(){}
    protected void initBase() {
        statusBar.setColor(this,Color.parseColor("#77aafd"));
    }

    @Override
    public void onClick(View view) {

    }

    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
