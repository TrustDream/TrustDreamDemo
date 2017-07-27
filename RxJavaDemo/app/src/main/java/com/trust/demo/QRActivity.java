package com.trust.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.trust.demo.swipebacklayout.app.SwipeBackActivity;
import com.trust.demo.tool.T;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

public class QRActivity extends SwipeBackActivity {
    private Button scanningBtn,generateQrBtn,foundQrBtn;
    private final int Scann = 0;

    private ImageView generateQrImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        initView();
    }

    private void initView() {
        scanningBtn = (Button) findViewById(R.id.activity_qr_scanning);
        generateQrBtn = (Button) findViewById(R.id.activity_qr_generate);
        foundQrBtn = (Button) findViewById(R.id.activity_qr_found_qr);
        generateQrImg = (ImageView) findViewById(R.id.activity_qr_generate_img);

        scanningBtn.setOnClickListener(this);
        generateQrBtn.setOnClickListener(this);
        foundQrBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_qr_scanning:
                intent.setClass(QRActivity.this, CaptureActivity.class);
                startActivityForResult(intent,Scann);
                break;

            case R.id.activity_qr_generate:
                try {
                    Bitmap bitmap = EncodingHandler.createQRCode("tetst",600);
                    generateQrImg.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.activity_qr_found_qr:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case Scann:
                    String resultq = data.getExtras().getString("result");
                    T.showToast(QRActivity.this,resultq);
                    break;
            }
        }
    }
}
