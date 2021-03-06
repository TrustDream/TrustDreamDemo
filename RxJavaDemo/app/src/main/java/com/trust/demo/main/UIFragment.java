package com.trust.demo.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trust.demo.ui.cardrotation.RotateActivity;
import com.trust.demo.ui.Coordinator.HoverActivity;
import com.trust.demo.ui.qr.QRActivity;
import com.trust.demo.R;
import com.trust.demo.ui.glide.glide.GlideActivity;
import com.trust.demo.tool.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Trust on 2017/7/27.
 */

public class UIFragment extends Fragment {

    @BindView(R.id.fragment_ui_recyclerview)
    RecyclerView mFragmentUiRecyclerview;
    Unbinder unbinder;
    private Context mContext;
    private UIAdapter uiAdapter;
    private int [] imgArgs = {R.drawable.ic_qr,R.drawable.ic_glide,R.drawable.ic_card,R.drawable.ic_collapingtoolbarlayout};
    private String [] msgArgs = {"二维码","Glide","卡片","CoordinatorLayout"};
    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_ui, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;

    }

    private void initData() {
        List<UIBean> ml = new ArrayList<>();
        for (int i = 0; i < imgArgs.length; i++) {
            ml.add(new UIBean(imgArgs[i],msgArgs[i]));
        }

        uiAdapter.setMl(ml);
        uiAdapter.notifyDataSetChanged();
    }

    private void initView() {
        uiAdapter = new UIAdapter(mContext);
        uiAdapter.setItemOnClickListener(new BaseRecyclerAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClickListener(View v, int pos, Object msg) {
                intentActivtiy(v,pos,msg);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        mFragmentUiRecyclerview.setLayoutManager(gridLayoutManager);
        mFragmentUiRecyclerview.addItemDecoration(new RecyclerViewDivider(1,Color.RED,1));

        mFragmentUiRecyclerview.setAdapter(uiAdapter);
    }

    private void intentActivtiy(View v, int pos, Object msg) {
        Intent intent = new Intent();
        switch (pos){
            case 0:
                intent.setClass(mContext, QRActivity.class);
                break;
            case 1:
                intent.setClass(mContext, GlideActivity.class);
                break;
            case 2:
                intent.setClass(mContext, RotateActivity.class);
                break;
            case 3:
                intent.setClass(mContext, HoverActivity.class);
                break;


        }
        mContext.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
