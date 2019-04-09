package com.toly1994.ivideo.app.download.service;

import android.content.Context;
import android.content.Intent;
import com.toly1994.ivideo.app.download.DownloadCons;
import com.toly1994.ivideo.app.download.db.DownLoadDao;
import com.toly1994.ivideo.app.download.db.DownLoadDaoImpl;
import com.toly1994.ivideo.app.download.model.FileBean;
import com.toly1994.ivideo.app.download.model.ThreadBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/13 0013:15:21<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：下载一个文件的任务(mDownLoadThreads储存该文件任务的所有线程)
 */
public class DownLoadTask {
    private FileBean mFileBean;//下载文件的信息
    private DownLoadDao mDao;//数据访问接口
    private Context mContext;//上下文
    private int mThreadCount;//线程数量
    public boolean isDownLoading;//是否正在下载
    private Timer mTimer;//定时器


    private List<DownLoadThread> mDownLoadThreads;//该文件所有线程的集合
    //已下载的长度：共享变量----使用volatile和Atomic进行同步
    private volatile AtomicLong mLoadedLen = new AtomicLong();
    //使用线程池
    public static ExecutorService sExe = Executors.newCachedThreadPool();


    public DownLoadTask(FileBean fileBean, Context context, int threadCount) {
        mFileBean = fileBean;
        mContext = context;
        mThreadCount = threadCount;
        mDao = new DownLoadDaoImpl(context);
        mDownLoadThreads = new ArrayList<>();
        mTimer = new Timer();
    }

    /**
     * 下载逻辑
     */
    public void download() {
        //从数据获取线程信息
        List<ThreadBean> threads = mDao.getThreads(mFileBean.getUrl());
        if (threads.size() == 0) {//如果没有线程信息，就新建线程信息
            //------获取每个进程下载长度
            long len = mFileBean.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                //创建threadCount个线程信息
                ThreadBean threadBean = null;
                if (i != mThreadCount - 1) {
                    threadBean = new ThreadBean(
                            i, mFileBean.getUrl(), len * i, (i + 1) * len - 1, 0);
                } else {
                    threadBean = new ThreadBean(
                            i, mFileBean.getUrl(), len * i, mFileBean.getLength(), 0);
                }
                //创建后添加到线程集合中
                threads.add(threadBean);
                //2.如果数据库没有此下载线程的信息，则向数据库插入该线程信息
                mDao.insertThread(threadBean);
            }
        }

        //启动多个线程
        for (ThreadBean info : threads) {
            DownLoadThread thread = new DownLoadThread(info);//创建下载线程
            sExe.execute(thread);//开始线程
            thread.isDownLoading = true;
            isDownLoading = true;
            mDownLoadThreads.add(thread);//开始下载时将该线程加入集合
        }

        mTimer.schedule(new TimerTask() {//启动定时器发送广播
            @Override
            public void run() {
                Intent intent = new Intent(DownloadCons.ACTION_UPDATE);//更新进度的广播intent
                mContext.sendBroadcast(intent);
                intent.putExtra(DownloadCons.SEND_LOADED_PROGRESS,
                        (int) (mLoadedLen.get() * 100 / mFileBean.getLength()));
                intent.putExtra(DownloadCons.SEND_FILE_ID, mFileBean.getId());
                mContext.sendBroadcast(intent);
            }
        }, 100, 1000);
    }

    public void pause() {
        for (DownLoadThread downLoadThread : mDownLoadThreads) {
            downLoadThread.isDownLoading = false;
            isDownLoading = false;

        }
    }

    /**
     * 下载的核心线程类
     */
    public class DownLoadThread extends Thread {
        private ThreadBean mThreadBean;//下载线程的信息
        public boolean isDownLoading;//是否在下载

        public DownLoadThread(ThreadBean threadBean) {
            mThreadBean = threadBean;
        }

        @Override
        public void run() {
            if (mThreadBean == null) {//1.下载线程的信息为空,直接返回
                return;
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            try {
                //3.连接线程的url
                URL url = new URL(mThreadBean.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                //4.设置下载位置
                long start = mThreadBean.getStart() + mThreadBean.getLoadedLen();//开始位置
                //conn设置属性，标记资源的位置(这是给服务器看的)
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadBean.getEnd());
                //5.寻找文件的写入位置
                File file = new File(DownloadCons.DOWNLOAD_DIR, mFileBean.getFileName());
                //创建随机操作的文件流对象,可读、写、删除
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);//设置文件写入位置
                //6.下载的核心逻辑
                mLoadedLen.set(mLoadedLen.get() + mThreadBean.getLoadedLen());
                //206-----部分内容和范围请求  不要200写顺手了...
                if (conn.getResponseCode() == 206) {
                    //读取数据
                    is = conn.getInputStream();
                    byte[] buf = new byte[1024 * 4];
                    int len = 0;
                    while ((len = is.read(buf)) != -1) {
                        //写入文件
                        raf.write(buf, 0, len);
                        //发送广播给Activity,通知进度
                        mLoadedLen.set(mLoadedLen.get() + len);//累加整个文件的完成进度
                        //累加每个线程完成的进度
                        mThreadBean.setLoadedLen(mThreadBean.getLoadedLen() + len);
                        //暂停保存进度到数据库
                        if (!this.isDownLoading) {
                            mDao.updateThread(mThreadBean.getUrl(), mThreadBean.getId(),
                                    mThreadBean.getLoadedLen());
                            return;
                        }
                    }
                }
                //是否所有线程都已经下载完成
                isDownLoading = false;
                checkIsAllOK();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (raf != null) {
                        raf.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 检查是否所有线程都已经完成了
         */
        private synchronized void checkIsAllOK() {
            boolean allFinished = true;
            for (DownLoadThread downLoadThread : mDownLoadThreads) {
                if (downLoadThread.isDownLoading) {
                    allFinished = false;
                    break;
                }
            }
            if (allFinished) {
                mTimer.cancel();//下载完成，取消定时器

                //下载完成，删除线程信息
                mDao.deleteThread(mThreadBean.getUrl());
                //通知下载结束
                Intent intent = new Intent();
                intent.setAction(DownloadCons.ACTION_FINISH);//加完成的Action
                intent.putExtra(DownloadCons.SEND_FILE_BEAN, mFileBean);
                mContext.sendBroadcast(intent);
            }
        }
    }
}
