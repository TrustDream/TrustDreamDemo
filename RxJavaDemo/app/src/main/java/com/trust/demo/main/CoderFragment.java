package com.trust.demo.main;

import android.content.Context;
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
        ml.add(new CoderBean(1,"1","2017/7/27 23:03"));
        ml.add(new CoderBean(2,"2","2017/7/27"));
        ml.add(new CoderBean(3,"3","2017/7/27"));

        coderAdapter.setMl(ml);
        coderAdapter.notifyDataSetChanged();
    }

    private void iniView() {
        coderAdapter = new CoderAdapter(mContext);
        coderAdapter.setItemOnClickListener(new BaseRecyclerAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClickListener(View v, int pos, Object msg) {
                Toast.makeText(mContext,"你点击了"+pos,Toast.LENGTH_LONG).show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mFragmentCodeRecyclerview.setLayoutManager(layoutManager);
        mFragmentCodeRecyclerview.setAdapter(coderAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
