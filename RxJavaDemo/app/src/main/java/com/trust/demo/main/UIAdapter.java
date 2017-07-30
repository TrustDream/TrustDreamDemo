package com.trust.demo.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trust.demo.R;

import java.util.List;

/**
 * Created by Trust on 2017/7/27.
 */

public class UIAdapter extends BaseRecyclerAdapter {

    public UIAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected ViewHolder initItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_ui,null);
        return new UiViewHodler(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UiViewHodler uiViewHodler = (UiViewHodler) holder;
        List<UIBean> uiBeans = ml;
        uiViewHodler.textView.setText(uiBeans.get(position).getMsg());
        uiViewHodler.logo.setImageResource(uiBeans.get(position).getImgId());
    }

    class UiViewHodler extends ViewHolder{
       TextView textView;ImageView logo;
        public UiViewHodler(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_uitext);
            logo = (ImageView) itemView.findViewById(R.id.item_uilog);
        }
    }
}
