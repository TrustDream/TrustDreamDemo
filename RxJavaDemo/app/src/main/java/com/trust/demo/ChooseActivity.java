package com.trust.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trust.demo.code.bluetooth.BlueToothActivity;
import com.trust.demo.ui.cardrotation.RotateActivity;
import com.trust.demo.ui.glide.glide.GlideActivity;
import com.trust.demo.code.gsonparsing.GsonParsingActivity;
import com.trust.demo.code.ndk.JniActivity;
import com.trust.demo.code.rxseries.RxActivity;
import com.trust.demo.code.tinker.TinkerActivity;
import com.trust.demo.ui.qr.QRActivity;
import com.trust.demo.ui.cardrotation.ViewPagerActivity;

public class ChooseActivity extends BaseActivity implements View.OnClickListener {
    private Button blueBtn,rxBtn,QrBtn,ndkBtn,glidBtn,mBtnPager,mBtnXZ,tinkerBtn,gsonParsingBtn;
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
        glidBtn = (Button) findViewById(R.id.home_glide);
        mBtnPager = (Button) findViewById(R.id.btn_pager);
        mBtnXZ = (Button) findViewById(R.id.btn_xuanzhuan);
        tinkerBtn = (Button) findViewById(R.id.home_tinker);
        gsonParsingBtn = (Button) findViewById(R.id.home_gson_parsong);


        blueBtn.setOnClickListener(this);
        ndkBtn.setOnClickListener(this);
        QrBtn.setOnClickListener(this);
        rxBtn.setOnClickListener(this);
        glidBtn.setOnClickListener(this);
        mBtnPager.setOnClickListener(this);
        mBtnXZ.setOnClickListener(this);
        tinkerBtn.setOnClickListener(this);
        gsonParsingBtn.setOnClickListener(this);

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
            case R.id.home_glide:
                intent.setClass(context,GlideActivity.class);
                break;
            case R.id.btn_pager:
                intent.setClass(context, ViewPagerActivity.class);
                break;
            case R.id.btn_xuanzhuan:
                intent.setClass(context, RotateActivity.class);
                break;
            case R.id.home_tinker:
                intent.setClass(context, TinkerActivity.class);
                break;

            case R.id.home_gson_parsong:
                intent.setClass(context, GsonParsingActivity.class);
                break;
        }
        startActivity(intent);
    }
}
