package com.trust.demo.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trust.demo.R;

import java.util.List;

/**
 * Created by Trust on 2017/7/27.
 */

public class CoderAdapter extends BaseRecyclerAdapter {
    public CoderAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected ViewHolder initItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_code,null);
        return new CoderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CoderViewHolder coderViewHolder = (CoderViewHolder) holder;
        List<CoderBean> coderBeans = ml;
        coderViewHolder.textView.setText(coderBeans.get(position).getMsg());
    }

    class CoderViewHolder extends ViewHolder{
        TextView textView;
        public CoderViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_codetext);
        }
    }
}
