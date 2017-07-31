package com.trust.demo.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trust.demo.R;

/**
 * Created by zheng on 2017/7/26.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Myholder> {

    private  FragmentActivity mContext;
    private String[] strs = new String[100];

    public RecyclerAdapter(FragmentActivity activity) {
        this.mContext=activity;
        //为测试给Recycler添加数据
        for (int i = 0; i < 100; i++) {
            strs[i] = i + "";
        }
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, null);
        Myholder myholder = new Myholder(view);
        return myholder;

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        holder.textView.setText(strs[position]);
    }

    @Override
    public int getItemCount() {
        return strs.length;
    }

    static class Myholder extends RecyclerView.ViewHolder {

        private TextView textView;

        public Myholder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.tv);
        }
    }
}


