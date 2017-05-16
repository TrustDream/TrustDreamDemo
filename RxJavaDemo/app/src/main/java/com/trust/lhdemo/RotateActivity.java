package com.trust.lhdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;
import com.trust.lhdemo.swipebacklayout.app.SwipeBackActivity;
import com.trust.lhdemo.tool.Rotatable;

public class RotateActivity extends SwipeBackActivity implements View.OnClickListener {

    private RelativeLayout rlCardRoot;
    private ImageView imageViewBack;
    private ImageView imageViewFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        initView();
        initData();
    }
    private void initView() {
        rlCardRoot = (RelativeLayout) findViewById(R.id.rl_card_root);
        imageViewBack = (ImageView) findViewById(R.id.imageView_back);
        imageViewFront = (ImageView) findViewById(R.id.imageView_front);
        imageViewBack.setOnClickListener(this);
        imageViewFront.setOnClickListener(this);
        setCameraDistance();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
            case R.id.imageView_front:
                cardTurnover();
                break;
        }

    }

    /**
     * 设置数据
     */
    public void initData() {
        imageViewBack.setImageResource(R.mipmap.cj_jb);
        imageViewFront.setImageResource(R.mipmap.cj_xxcy);
        imageViewBack.setVisibility(View.VISIBLE);
        imageViewFront.setVisibility(View.INVISIBLE);
    }


    /**
     * 翻牌
     */
    public void cardTurnover() {
        if (View.VISIBLE == imageViewBack.getVisibility()) {
            ViewHelper.setRotationY(imageViewFront, 180f);//先翻转180，转回来时就不是反转的了
            Rotatable rotatable = new Rotatable.Builder(rlCardRoot)
                    .sides(R.id.imageView_back, R.id.imageView_front)
                    .direction(Rotatable.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatable.setTouchEnable(false);
            rotatable.rotate(Rotatable.ROTATE_Y, -180, 1500);
        } else if (View.VISIBLE == imageViewFront.getVisibility()) {
            Rotatable rotatable = new Rotatable.Builder(rlCardRoot)
                    .sides(R.id.imageView_back, R.id.imageView_front)
                    .direction(Rotatable.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatable.setTouchEnable(false);
            rotatable.rotate(Rotatable.ROTATE_Y, 0, 1500);
        }
    }

    /**
     * 改变视角距离, 贴近屏幕
     */
    private void setCameraDistance() {
        int distance = 10000;
        float scale = getResources().getDisplayMetrics().density * distance;
        rlCardRoot.setCameraDistance(scale);
    }

}
