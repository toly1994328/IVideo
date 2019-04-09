package com.toly1994.ivideo.view.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.download.view.DownLoadView;
import com.toly1994.ivideo.model.MeItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MeTopAdapter extends RecyclerView.Adapter<MeTopAdapter.MyViewHolder> {

    private Context mContext;
    private List<MeItem> mMeItems = new ArrayList<>();
    private static final String TAG = "DirAdapter";

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_me_top, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MeItem item = mMeItems.get(position);
        holder.mTvInfo.setText(item.info);
        holder.mIcon.setImageResource(item.icon);
        holder.mTvDetail.setText(item.sub);

        if (position == 3) {
            holder.itemView.setOnClickListener(v->{
                Toast.makeText(mContext, item.info, Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext,DownLoadView.class));
            });
        }

    }


    @Override
    public int getItemCount() {
        return mMeItems.size();
    }

    public void setData(List<MeItem> items) {
        mMeItems = items;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mTvInfo;
        public TextView mTvDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.id_iv_icon);
            mTvInfo = itemView.findViewById(R.id.id_tv_info);
            mTvDetail = itemView.findViewById(R.id.id_tv_detail);
        }
    }

    // 点击回调监听 --------------------------------------
    public interface OnItemClickListener {
        void onClick(View view, int Position, File[] sons);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}