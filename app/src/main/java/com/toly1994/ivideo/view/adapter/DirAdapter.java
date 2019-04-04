package com.toly1994.ivideo.view.adapter;

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
import com.toly1994.ivideo.presenter.IHomePresenter;

import java.util.List;

public class DirAdapter extends RecyclerView.Adapter<DirAdapter.MyViewHolder> {

    private Context mContext;
    private List<DirBean> mDirBeans;
    private static final String TAG = "HomeAdapter";
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


        holder.mIvTvTitle.setText(Filer.getParent(dirBean.getFirstImgPath(),3));

        holder.mIvCover.setOnClickListener(v -> {

        });

//        holder.mIvCover.setOnLongClickListener(v -> {
//            DialogUtils.showNormalDialog(
//                    (Activity) mContext, R.mipmap.icon_video, "是否从磁盘删除视频?", () -> {
//                        doDeleteFile(videoInfo, true);
//                    }, () -> {
//
//                    }, "温馨提示");
//            return true;
//        });
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
        public View mIvCover;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIvTvTitle = itemView.findViewById(R.id.id_tv_dir);
            mIvCover = itemView.findViewById(R.id.id_cover);
        }
    }
}