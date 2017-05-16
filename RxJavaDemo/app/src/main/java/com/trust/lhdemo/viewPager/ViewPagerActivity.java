package com.trust.lhdemo.viewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.trust.lhdemo.BaseActivity;
import com.trust.lhdemo.R;
import com.trust.lhdemo.swipebacklayout.app.SwipeBackActivity;
import com.trust.lhdemo.tool.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends SwipeBackActivity {
    private List<View> views = new ArrayList<>();
    private MsgViewPager msgViewPager;
    private FacePageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        init();
        initData();
    }

    private void initData() {
        for(int i = 0;i<5;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.banner_1);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
            views.add(imageView);
        }
        adapter.setViews(views);
    }

    private void init() {
        setSystemBarColor(true,0x00000000);
        msgViewPager = (MsgViewPager) findViewById(R.id.msgviewpager);
        adapter = new FacePageAdapter(msgViewPager);
        msgViewPager.setAdapter(adapter);
    }

    protected void setSystemBarColor(boolean on, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
        //跟布局 需要设置fitsSystemWindows 为 false
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintResource(color);
        tintManager.setStatusBarTintEnabled(true);
    }
}
