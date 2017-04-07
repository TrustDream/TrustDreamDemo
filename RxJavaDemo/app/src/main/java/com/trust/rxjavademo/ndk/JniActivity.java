package com.trust.rxjavademo.ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sy.mylibrary.Test;
import com.trust.rxjavademo.R;

public class JniActivity extends AppCompatActivity {
    private TextView jniTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        jniTv = (TextView) findViewById(R.id.jni_tv);
        jniTv.setText(new JniTest().getString());

        Test test = new Test();

        Log.d("lhh", "onCreate: "+test.getTest());
    }
}
