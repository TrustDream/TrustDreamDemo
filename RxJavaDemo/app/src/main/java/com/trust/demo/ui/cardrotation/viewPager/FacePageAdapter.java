package com.trust.demo.ui.cardrotation.viewPager;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * viewpager的adapter
 *
 * @author Created by HJ
 * @date: 2016年6月17日 下午14:04
 * QQ578301862
 * @email:15929721851@163.com
 */
public class FacePageAdapter extends PagerAdapter {
    // 界面列表
    private List<View> views;
    private MsgViewPager viewPager;
    private int mChildCount = 0;

    public FacePageAdapter(MsgViewPager viewPager) {
        super();
        this.viewPager = viewPager;

    }

    public void setViews(List<View> views) {
        this.views = views;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == views ? 0 : views.size();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        if(position < views.size())
            ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void finishUpdate(View container) {
        super.finishUpdate(container);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public int getItemPosition(Object object) {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        viewPager.setObjectForPosition(views.get(position), position);// 这句很重要,没有这句就没有效果
        return views.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount  = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }


    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setPrimaryItem(View container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void startUpdate(View container) {
        super.startUpdate(container);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

}
