package com.trust.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Trust on 2017/7/26.
 */

public class MainControllViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;

    public void setmList(List<Fragment> mList) {
        this.mList = mList;
    }

    public MainControllViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size():0;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "ui";
        }else{
            return "code";
        }
    }
}
