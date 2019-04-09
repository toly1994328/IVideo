package com.toly1994.ivideo.presenter.online;

import com.toly1994.ivideo.model.login.Callback;
import com.toly1994.ivideo.model.me.VideoModel;
import com.toly1994.ivideo.model.me.VideoResult;
import com.toly1994.ivideo.presenter.IPresenter;
import com.toly1994.ivideo.view.home.IOnlineView;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/8/008:21:17<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class OnlinePresenter implements IPresenter {

    private final VideoModel mModel;
    private IOnlineView mView;

    public OnlinePresenter(IOnlineView view) {
        mView = view;
        mModel = new VideoModel();
    }

    @Override
    public void render(boolean force) {
        mModel.getVideos(new Callback<List<VideoResult.DataBean>>() {
            @Override
            public void onStartLoad() {

            }

            @Override
            public void onSuccess(List<VideoResult.DataBean> data) {
                mView.renderVideo(data);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
