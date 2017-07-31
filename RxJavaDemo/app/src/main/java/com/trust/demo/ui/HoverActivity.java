package com.trust.demo.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.trust.demo.BaseActivity;
import com.trust.demo.R;

import java.util.ArrayList;
import java.util.List;

public class HoverActivity extends BaseActivity {
    private TextView tvTitle;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover);

        fragmentList.add(new QueryOrderFragment());
        fragmentList.add(new QueryOrderFragment());
        fragmentList.add(new QueryOrderFragment());
//this is test

        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        OrderViewPagerAdapter viewPagerAdapter = new OrderViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout= (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("签约订单查询"));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText("签约单完成"));
        mTabLayout.addTab(mTabLayout.newTab().setText("签约单到期"));

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setText("签约订单查询");
        mTabLayout.getTabAt(1).setText("签约单完成");
        mTabLayout.getTabAt(2).setText("签约单到期");
    }
}
