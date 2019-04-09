package com.toly1994.ivideo.view.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.model.dir.DirBean;
import com.toly1994.ivideo.presenter.home.IHomePresenter;

import java.io.File;
import java.util.List;

public class MeCenterAdapter extends RecyclerView.Adapter<MeCenterAdapter.MyViewHolder> {

    private Context mContext;
    private List<DirBean> mDirBeans;
    private static final String TAG = "DirAdapter";
    private IHomePresenter mPresenter;
//
//    public MeTopAdapter(IHomePresenter presenter) {
//        mPresenter = presenter;
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_me_center, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//        DirBean dirBean = mDirBeans.get(position);
//
//
//        holder.mIvTvTitle.setText(Filer.getParent(dirBean.getFirstImgPath(), 3));
//        holder.mIvTvCount.setText(dirBean.getCount() + "个视频");
//
//        holder.mIvCover.setOnClickListener(v -> {
//            String dir = dirBean.getDir();
//            File[] files = new File(dir).listFiles((dir1, name) -> name.endsWith(".mp4"));
//            if (mOnItemClickListener != null) {
//                mOnItemClickListener.onClick(holder.mIvCover, position, files);
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return 4;
    }

    public void setdata(List<DirBean> videos) {
        mDirBeans = videos;
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