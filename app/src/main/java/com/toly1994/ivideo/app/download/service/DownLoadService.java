package com.toly1994.ivideo.app.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.widget.Toast;
import com.toly1994.ivideo.app.download.DownloadCons;
import com.toly1994.ivideo.app.download.model.FileBean;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/12 0012:12:23<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：下载的服务
 */
public class DownLoadService extends Service {
    //由于多文件，维护一个Task集合：使用SparseArray存储int型的键---的键值对
    private SparseArray<DownLoadTask> mTaskMap = new SparseArray<>();

    /**
     * 处理消息使用的Handler
     */
    private Handler mHandler = new Handler(
            msg -> {
                switch (msg.what) {
                    case DownloadCons.MSG_CREATE_FILE_OK:
                        FileBean fileBean = (FileBean) msg.obj;
                        //已在主线程，可更新UI
                        Toast.makeText(this, "文件长度", Toast.LENGTH_SHORT).show();
                        DownLoadTask task = new DownLoadTask(fileBean, DownLoadService.this, 3);
                        task.download();
                        mTaskMap.put(fileBean.getId(), task);

                        //启动通知栏广播
                        Intent intent = new Intent(DownloadCons.ACTION_START_NF);
                        intent.putExtra(DownloadCons.SEND_FILE_BEAN, fileBean);
                        sendBroadcast(intent);
                        break;
                }

                return false;
            });


    @Override//每次启动服务会走此方法
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case DownloadCons.ACTION_START:
                    FileBean fileBean = (FileBean) intent.getSerializableExtra(DownloadCons.SEND_FILE_BEAN);
                    DownLoadTask start = mTaskMap.get(fileBean.getId());
                    if (start != null) {
                        if (start.isDownLoading) {
                            return super.onStartCommand(intent, flags, startId);
                        }
                    }
                    DownLoadTask.sExe.execute(new LinkURLThread(fileBean, mHandler));
                    break;
                case DownloadCons.ACTION_STOP:
                    FileBean stopFile = (FileBean) intent.getSerializableExtra(DownloadCons.SEND_FILE_BEAN);
                    //获取停止的下载线程
                    DownLoadTask task = mTaskMap.get(stopFile.getId());
                    if (task != null) {
                        task.pause();
                    }
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

