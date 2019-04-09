package com.toly1994.ivideo.app.download.model;

import android.content.Context;
import android.support.annotation.NonNull;
import com.toly1994.ivideo.app.download.DownloadCons;
import com.toly1994.ivideo.app.download.db.DownLoadDao;
import com.toly1994.ivideo.app.download.db.DownLoadDaoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/9/009:8:43<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class DownloadModel implements IDownloadModel<List<FileBean>> {

    /**
     * 初始化数据
     *
     * @return
     */
    @NonNull
    @Override
    public List<FileBean> getData() {
        FileBean juejin = new FileBean(0, DownloadCons.URL_JUEJIN, "掘金.apk", 0, 0);
        FileBean yunbiji = new FileBean(1, DownloadCons.URL_YOUDAO, "有道云笔记.apk", 0, 0);
        FileBean qq = new FileBean(2, DownloadCons.URL_QQ, "QQ.apk", 0, 0);
        FileBean weiChat = new FileBean(3, DownloadCons.URL_WEIXIN, "微信.apk", 0, 0);
        FileBean cidian = new FileBean(4, DownloadCons.URL_YOUDAO_CIDIAN, "有道词典.apk", 0, 0);

        ArrayList<FileBean> fileBeans = new ArrayList<>();
        fileBeans.add(juejin);
        fileBeans.add(yunbiji);
        fileBeans.add(qq);
        fileBeans.add(weiChat);
        fileBeans.add(cidian);
        return fileBeans;
    }

    @Override
    public List<FileBean> getDownloadingData(Context context) {
        List<FileBean> fileBeans = getData();
        DownLoadDao dao = new DownLoadDaoImpl(context);

        for (FileBean fileBean : fileBeans) {//数据回显，哈哈，终于完成了，多线程下载完结散花!
            long loadedLen = 0;
            long allLen = 0;
            List<ThreadBean> threads = dao.getThreads(fileBean.getUrl());
            if (threads.size() > 0) {
                for (ThreadBean thread : threads) {
                    loadedLen += thread.getLoadedLen();
                    allLen += thread.getEnd() - thread.getStart();
                }
                fileBean.setLoadedLen((int) (loadedLen * 100 / allLen));
            }
        }

        return fileBeans;
    }


}
