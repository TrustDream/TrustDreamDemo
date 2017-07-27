package com.trust.demo.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Trust on 2017/7/27.
 */

public abstract class  BaseRecyclerAdapter <T> extends RecyclerView.Adapter <BaseRecyclerAdapter.ViewHolder> {
    protected List<T> ml;//数据源
    protected Context mContext;

    public void setMl(List<T> ml) {
        this.ml = ml;
    }

    public BaseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder = initItemView(parent,viewType);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewHolder.getAdapterPosition();
                mItemOnClickListener.itemOnClickListener(view,pos,ml.get(pos));
            }
        });
        return viewHolder;
    }



    protected ViewHolder initItemView(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public int getItemCount() {
        return ml!= null ?ml.size():0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    protected void setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        mItemOnClickListener = itemOnClickListener;
    }

    interface ItemOnClickListener{
        void itemOnClickListener(View v,int pos,Object msg);
    }

    protected ItemOnClickListener mItemOnClickListener;
}
