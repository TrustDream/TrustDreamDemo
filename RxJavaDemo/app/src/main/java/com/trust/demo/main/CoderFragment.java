package com.trust.demo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trust.demo.R;
import com.trust.demo.code.bluetooth.BlueToothActivity;
import com.trust.demo.code.ndk.JniActivity;
import com.trust.demo.code.perssion.PerssionActivity;
import com.trust.demo.code.rxseries.RxActivity;
import com.trust.demo.code.tinker.TrustTinker;
import com.trust.demo.ui.qr.QRActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Trust on 2017/7/27.
 */

public class CoderFragment extends Fragment {

    @BindView(R.id.fragment_code_recyclerview)
    RecyclerView mFragmentCodeRecyclerview;
    Unbinder unbinder;
    private int [] imgs = {R.drawable.ic_one,R.drawable.ic_two,
    R.drawable.ic_there,R.drawable.ic_four,R.drawable.ic_fiver,
    R.drawable.ic_six,R.drawable.ic_seven,R.drawable.ic_eight,
    R.drawable.ic_nine,R.drawable.ic_tens};
    private String [] nams = {"Tinker","BlueTooth","Ndk","RxSeries","Perssion",
    "预留","预留","预留","预留","预留"};
    private String [] times = {"2017/8/6 11:15","2017/8/6 13:35","2017/8/6 15:04",
            "2017/8/6 15:22","2017/8/6 15:22","time","time","time","time","time"};
    private Context mContext;
    private CoderAdapter coderAdapter;
    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_code, null);
        unbinder = ButterKnife.bind(this, view);
        iniView();
        initData();
        return view;

    }

    private void initData() {
        List<CoderBean> ml = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ml.add(new CoderBean(imgs[i],nams[i],times[i]));
        }
        coderAdapter.setMl(ml);
        coderAdapter.notifyDataSetChanged();
    }

    private void iniView() {
        coderAdapter = new CoderAdapter(mContext);
        coderAdapter.setItemOnClickListener(new BaseRecyclerAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClickListener(View v, int pos, Object msg) {
                intentActivity(v,pos,msg);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mFragmentCodeRecyclerview.setLayoutManager(layoutManager);
        mFragmentCodeRecyclerview.setAdapter(coderAdapter);
    }

    private void intentActivity(View v, int pos, Object msg) {
        Intent intent = new Intent();
        switch (pos){
            case 0:
                intent.setClass(mContext, TrustTinker.class);
                break;
            case 1:
                intent.setClass(mContext, BlueToothActivity.class);
                break;
            case 2:
                intent.setClass(mContext, JniActivity.class);
                break;
            case 3:
                intent.setClass(mContext, RxActivity.class);
                break;
            case 4:
                intent.setClass(mContext, PerssionActivity.class);
                break;
            case 5:
                intent.setClass(mContext, BlueToothActivity.class);
                break;
            case 6:
                intent.setClass(mContext, BlueToothActivity.class);
                break;
            case 7:
                intent.setClass(mContext, BlueToothActivity.class);
                break;
            case 8:
                intent.setClass(mContext, BlueToothActivity.class);
                break;
            case 9:
                intent.setClass(mContext, BlueToothActivity.class);
                break;

        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
