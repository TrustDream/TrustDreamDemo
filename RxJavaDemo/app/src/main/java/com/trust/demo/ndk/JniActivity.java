package com.trust.demo.ndk;

import android.os.Bundle;
import android.widget.TextView;


import com.trust.demo.R;
import com.trust.demo.swipebacklayout.app.SwipeBackActivity;

public class JniActivity extends SwipeBackActivity {
    private TextView jniTv;
    JniTest jniTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);


        jniTest = new JniTest();
        jniTv = (TextView) findViewById(R.id.jni_tv);
        jniTv.setText("jni");
//        new JniTest().updateFile("/mnt/sdcard/trust.txt");

    }
}
