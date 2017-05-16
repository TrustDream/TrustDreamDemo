package com.trust.lhdemo.ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import com.trust.lhdemo.BaseActivity;
import com.trust.lhdemo.R;
import com.trust.lhdemo.swipebacklayout.app.SwipeBackActivity;
import com.trust.lhdemo.tool.L;

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
