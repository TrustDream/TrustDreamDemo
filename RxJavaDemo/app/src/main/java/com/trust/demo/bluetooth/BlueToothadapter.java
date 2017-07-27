package com.trust.demo.bluetooth;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trust.demo.R;
import com.trust.demo.bean.BlueDeviceBean;

import java.util.List;

/**
 * Created by TrustTinker on 2017/4/17.
 */
public class BlueToothadapter extends RecyclerView.Adapter<BlueToothadapter.ViewHolder>{
    private List<BlueDeviceBean> ml;
    BlueRecyclerOnclik blueRecyclerOnclik;

    public void setMl(List<BlueDeviceBean> ml) {
        this.ml = ml;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blue,
                parent,false);
        final ViewHolder viewHolder =  new ViewHolder(view);
        viewHolder.bleuDvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewHolder.getAdapterPosition();
                blueRecyclerOnclik.callBack(view,ml.get(pos));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bleuDvice.setText(ml.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return ml!=null?ml.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bleuDvice;
        public ViewHolder(View itemView) {
            super(itemView);
            bleuDvice = (TextView) itemView.findViewById(R.id.item_blue_dvice);
        }
    }

    interface BlueRecyclerOnclik {
        void callBack(View v,BlueDeviceBean blueDeviceBean);
    }
}
