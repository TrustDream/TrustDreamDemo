package com.trust.rxjavademo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trust.rxjavademo.ndk.JniActivity;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener {
    private Button blueBtn,rxBtn,QrBtn,ndkBtn;
    private Context context = ChooseActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        init();
    }

    private void init() {
        initView();

    }

    private void initView() {
        blueBtn = (Button) findViewById(R.id.home_blue);
        ndkBtn = (Button) findViewById(R.id.home_jni);
        QrBtn = (Button) findViewById(R.id.home_qr);
        rxBtn = (Button) findViewById(R.id.home_rx);


        blueBtn.setOnClickListener(this);
        ndkBtn.setOnClickListener(this);
        QrBtn.setOnClickListener(this);
        rxBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.home_rx:
                intent.setClass(context,RxActivity.class);
                break;
            case R.id.home_blue:
                intent.setClass(context,BlueToothActivity.class);
                break;
            case R.id.home_jni:
                intent.setClass(context, JniActivity.class);
                break;
            case R.id.home_qr:
                intent.setClass(context,QRActivity.class);
                break;
        }
    }
}
