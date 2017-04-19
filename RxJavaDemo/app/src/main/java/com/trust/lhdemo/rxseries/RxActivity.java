package com.trust.lhdemo.rxseries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trust.lhdemo.BaseActivity;
import com.trust.lhdemo.R;

public class RxActivity extends BaseActivity {
    private Button rxJavaBtn,rxRetrofitBtn,rxBindingBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        init();
    }

    private void init() {
        rxJavaBtn = (Button) findViewById(R.id.activity_rx_java);
        rxRetrofitBtn = (Button) findViewById(R.id.activity_rx_retrofit);
        rxBindingBtn = (Button) findViewById(R.id.activity_rx_rxbinding);

        rxJavaBtn.setOnClickListener(this);
        rxRetrofitBtn.setOnClickListener(this);
        rxBindingBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_rx_java:
                intent.setClass(RxActivity.this,RxJavaActivity.class);
                break;

            case R.id.activity_rx_retrofit:
                intent.setClass(RxActivity.this,RetrofitActivity.class);
                break;

            case R.id.activity_rx_rxbinding:
                intent.setClass(RxActivity.this,RxBindingActivity.class);
                break;

        }

        startActivity(intent);
    }
}
