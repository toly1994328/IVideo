package com.toly1994.ivideo.presenter;

import com.toly1994.ivideo.app.utils.Formater;
import com.toly1994.ivideo.model.CtrlPanel;
import com.toly1994.ivideo.view.ICtrlView;
import com.toly1994.ivideo.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:13:35<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class CtrlPresenter implements ICtrlPresenter {

    private ICtrlView mICtrlView;
    private VideoView mVideoView;
    private final CtrlPanel mPanel;

    public CtrlPresenter(ICtrlView ICtrlView, VideoView videoView) {
        mICtrlView = ICtrlView;
        mICtrlView.showLoading();
        mVideoView = videoView;
        mPanel = new CtrlPanel();
    }

    @Override
    public void initVideo(String path) {
        mVideoView.setVideoPath(path);
        mPanel.name = mVideoView.getName();

        mVideoView.setOnPreparedListener(
                mp -> {
                    mICtrlView.hideLoading();
                    mPanel.allTime = Formater.formatTime(mVideoView.getDuration());
                    render(false);
                }
        );

        mVideoView.setOnProgressChanged(
                per_100 -> {
                    mPanel.rate = per_100;
                    render(false);
                });


        mICtrlView.showPanel();
    }

    @Override
    public void render(boolean force) {
        updateCtrlData();
        mICtrlView.render(mPanel);
    }

    private void updateCtrlData() {
        mPanel.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(System.currentTimeMillis());
        mPanel.playedTime = Formater.formatTime((long) (mPanel.rate / 100.f * mVideoView.getDuration()));
    }
}
