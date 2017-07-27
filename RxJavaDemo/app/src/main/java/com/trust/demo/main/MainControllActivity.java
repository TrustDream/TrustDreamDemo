package com.trust.demo.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.trust.demo.BaseActivity;
import com.trust.demo.MainControllViewPagerAdapter;
import com.trust.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainControllActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.maincontroll_tablayout)
    TabLayout mTablayout;
    private MainControllViewPagerAdapter mainControllViewPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    @BindView(R.id.maincontroll_viewpager)
    ViewPager mControllViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_controll);
        ButterKnife.bind(this);
        initView();
        iniData();
    }

    private void initView() {
        mainControllViewPagerAdapter = new MainControllViewPagerAdapter(getSupportFragmentManager());
        mControllViewpager.setAdapter(mainControllViewPagerAdapter);
        mTablayout.setupWithViewPager(mControllViewpager);
    }

    private void iniData() {
        fragmentList.add(new UIFragment());
        fragmentList.add(new CoderFragment());
        mainControllViewPagerAdapter.setmList(fragmentList);
        mainControllViewPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
