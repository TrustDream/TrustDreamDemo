package com.trust.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trust.demo.R;

/**
 * Created by zheng on 2017/7/26.
 */
public class QueryOrderFragment extends Fragment{

    private View view;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.fragment_item,null);

        initView();

        return view;
    }

    private void initView() {
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview);
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
