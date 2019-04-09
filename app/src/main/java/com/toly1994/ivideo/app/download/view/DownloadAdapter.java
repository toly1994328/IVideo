package com.toly1994.ivideo.app.download.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.download.DownloadCons;
import com.toly1994.ivideo.app.download.model.FileBean;
import com.toly1994.ivideo.app.download.service.DownLoadService;
import com.toly1994.ivideo.widget.AlphaImageView;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/13 0013:11:58<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：RecyclerView适配器
 */
public class DownloadAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    private List<FileBean> mData;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_download_pb, parent, false);

        view.setOnClickListener(v -> {
            //TODO 点击条目

        });

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FileBean fileBean = mData.get(position);
        holder.mBtnStart.setOnAlphaListener(v -> {
            Toast.makeText(mContext, "开始下载" + fileBean.getFileName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, DownLoadService.class);
            intent.setAction(DownloadCons.ACTION_START);
            intent.putExtra(DownloadCons.SEND_FILE_BEAN, fileBean);//使用intent携带对象
            mContext.startService(intent);//开启服务--下载标示
        });

        holder.mBtnStop.setOnAlphaListener(v -> {
            Intent intent = new Intent(mContext, DownLoadService.class);
            intent.setAction(DownloadCons.ACTION_STOP);
            intent.putExtra(DownloadCons.SEND_FILE_BEAN, fileBean);//使用intent携带对象
            mContext.startService(intent);//启动服务---停止标示
            Toast.makeText(mContext, "停止下载" + fileBean.getFileName(), Toast.LENGTH_SHORT).show();
        });

        holder.mTVFileName.setText(fileBean.getFileName());

        holder.mPBH.setProgress((int) fileBean.getLoadedLen());
        holder.mPBV.setProgress((int) fileBean.getLoadedLen());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<FileBean> data) {
        mData = data;
        notifyDataSetChanged();
    }
}

/**
 * ViewHolder
 */
class MyViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar mPBH;
    public ProgressBar mPBV;
    public AlphaImageView mBtnStart;
    public AlphaImageView mBtnStop;
    public TextView mTVFileName;


    public MyViewHolder(View itemView) {
        super(itemView);
        mPBH = itemView.findViewById(R.id.id_pb_h);
        mPBV = itemView.findViewById(R.id.id_pb_v);
        mBtnStart = itemView.findViewById(R.id.id_btn_start);
        mBtnStop = itemView.findViewById(R.id.id_btn_stop);
        mTVFileName = itemView.findViewById(R.id.id_tv_file_name);
    }
}