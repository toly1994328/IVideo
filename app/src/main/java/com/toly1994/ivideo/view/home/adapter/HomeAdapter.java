package com.toly1994.ivideo.view.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.utils.DialogUtils;
import com.toly1994.ivideo.app.utils.Formater;
import com.toly1994.ivideo.app.utils.filer.Filer;
import com.toly1994.ivideo.db.VideoDao;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.presenter.home.IHomePresenter;
import com.toly1994.ivideo.view.player.PlayerView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<VideoInfo> mVideos;
    private static final String TAG = "HomeAdapter";
    private IHomePresenter mPresenter;

    public HomeAdapter(IHomePresenter presenter) {

        mPresenter = presenter;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_a_card, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        VideoInfo videoInfo = mVideos.get(position);

        String dataUrl = videoInfo.getDataUrl();
        if (dataUrl.equals(mVideos.get(0).getDataUrl())) {
            holder.mIdNewTag.setVisibility(View.VISIBLE);
        } else {
            holder.mIdNewTag.setVisibility(View.GONE);

        }

        String filePath = videoInfo.getCaverPath();
        if (new File(filePath).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            holder.mIvCover.setImageBitmap(bitmap);
        }

        holder.mIvCover.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PlayerView.class);
            intent.putExtra("video-position", position);
            mContext.startActivity(intent);
        });

        holder.mIvCover.setOnLongClickListener(v -> {
            DialogUtils.showNormalDialog(
                    (Activity) mContext, R.mipmap.icon_video, "是否从磁盘删除视频?", () -> {
                        doDeleteFile(videoInfo, true);
                    }, () -> {

                    }, "温馨提示");
            return true;
        });

        holder.mIvTvTitle.setText(videoInfo.getTitle());
        holder.mIdTvTime.setText("时长 " + Formater.formatTime(videoInfo.getDuration()));
        float size = videoInfo.getSize() * 1.f / 1024 / 1024;
        holder.mIdTvType.setText(new DecimalFormat("#.00").format(size) + "M");
        holder.mIdTvDir.setText("文件夹 " + Filer.getParent(dataUrl, 3));


        holder.mIdTvSee.setText("" + VideoDao.newInstance().getPlayCount(dataUrl));
    }

    private void doDeleteFile(VideoInfo video, boolean stub) {
        if (stub) {
            if (new File(video.getDataUrl()).delete()) {
                mPresenter.delete(mContext, (int) video.getVideoId());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void setdata(List<VideoInfo> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public View mIdNewTag;
        public TextView mIvTvTitle;
        public ImageView mIvCover;
        public TextView mIdTvType;
        public TextView mIdTvTime;
        public TextView mIdTvDir;
        public TextView mIdTvSee;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIvTvTitle = itemView.findViewById(R.id.iv_tv_title);
            mIvCover = itemView.findViewById(R.id.iv_cover);
            mIdTvType = itemView.findViewById(R.id.id_tv_type);
            mIdNewTag = itemView.findViewById(R.id.id_new_tag);
            mIdTvTime = itemView.findViewById(R.id.tv_author);
            mIdTvDir = itemView.findViewById(R.id.id_tv_dir);
            mIdTvSee = itemView.findViewById(R.id.id_tv_see);
        }
    }
}