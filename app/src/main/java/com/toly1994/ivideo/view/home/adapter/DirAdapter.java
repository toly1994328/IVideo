package com.toly1994.ivideo.view.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.utils.filer.Filer;
import com.toly1994.ivideo.model.dir.DirBean;
import com.toly1994.ivideo.presenter.home.IHomePresenter;

import java.io.File;
import java.util.List;

public class DirAdapter extends RecyclerView.Adapter<DirAdapter.MyViewHolder> {

    private Context mContext;
    private List<DirBean> mDirBeans;
    private static final String TAG = "DirAdapter";
    private IHomePresenter mPresenter;

    public DirAdapter(IHomePresenter presenter) {
        mPresenter = presenter;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dir, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DirBean dirBean = mDirBeans.get(position);


        holder.mIvTvTitle.setText(Filer.getParent(dirBean.getFirstImgPath(), 3));
        holder.mIvTvCount.setText(dirBean.getCount() + "个视频");

        holder.mIvCover.setOnClickListener(v -> {
            String dir = dirBean.getDir();
            File[] files = new File(dir).listFiles((dir1, name) -> name.endsWith(".mp4"));
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(holder.mIvCover, position, files);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDirBeans.size();
    }

    public void setdata(List<DirBean> videos) {
        mDirBeans = videos;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mIvTvTitle;
        public TextView mIvTvCount;
        public View mIvCover;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIvTvTitle = itemView.findViewById(R.id.id_tv_dir);
            mIvCover = itemView.findViewById(R.id.id_cover);
            mIvTvCount = itemView.findViewById(R.id.id_tv_count);
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