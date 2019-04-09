package com.toly1994.ivideo.app.download.presenter;


import com.toly1994.ivideo.app.download.model.DownloadModel;
import com.toly1994.ivideo.app.download.model.FileBean;
import com.toly1994.ivideo.app.download.model.IDownloadModel;
import com.toly1994.ivideo.app.download.view.IDownloadView;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/9/009:8:46<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class DownloadPresenter {

    private final IDownloadModel<List<FileBean>> mModel;
    private IDownloadView<List<FileBean>> mView;
    private List<FileBean> mData;

    public DownloadPresenter(IDownloadView<List<FileBean>> view) {
        mView = view;
        mModel = new DownloadModel();

    }


    public void render(boolean force) {
        mData = mModel.getDownloadingData(mView.getContext());
        mView.render(mData);
    }

    /**
     * 更新进度
     * @param id 待更新的文件id
     * @param progress 进度数
     */
    public  void updateProgress(int id, int progress) {
        mData.get(id).setLoadedLen(progress);
        mView.render(mData);
    }
}
