package com.trust.demo.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        coderViewHolder.title.setText(coderBeans.get(position).getMsg());
        coderViewHolder.time.setText(coderBeans.get(position).getTime());
        coderViewHolder.logo.setImageResource(coderBeans.get(position).getImgId());
    }

    class CoderViewHolder extends ViewHolder{
        TextView title,time;
        ImageView logo;
        public CoderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_codetext);
            time = (TextView) itemView.findViewById(R.id.item_codetime);
            logo = (ImageView) itemView.findViewById(R.id.item_codelogo);
        }
    }
}
