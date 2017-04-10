package com.trust.rxjavademo.ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.trust.rxjavademo.R;
import com.trust.rxjavademo.tool.L;

public class JniActivity extends AppCompatActivity {
    private TextView jniTv;
    JniTest jniTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        jniTest = new JniTest();
        jniTv = (TextView) findViewById(R.id.jni_tv);
        jniTv.setText(jniTest.getString());
//        new JniTest().updateFile("/mnt/sdcard/trust.txt");
        int [] data = {1,2,3,4,5};
        int [] newsData = jniTest.updateIntArray(data);
        for (int i = 0; i < newsData.length; i++) {
            L.d("Jni Activtiy int newsData[]:"+newsData[i]);
        }
    }
}
