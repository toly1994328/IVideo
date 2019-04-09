package com.toly1994.ivideo.view.home;

import com.toly1994.ivideo.model.me.VideoResult;
import com.toly1994.ivideo.presenter.online.OnlinePresenter;
import com.toly1994.ivideo.view.ILoadingView;
import com.toly1994.ivideo.view.IView;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:13:33<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface IOnlineView extends ILoadingView, IView {

    void renderVideo(List<VideoResult.DataBean> videos);

    OnlinePresenter getPresenter();
}
