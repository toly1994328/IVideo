package com.toly1994.ivideo.presenter.player;

import android.net.Uri;
import com.toly1994.ivideo.app.itf.VideoPlayerManager;
import com.toly1994.ivideo.app.utils.Formater;
import com.toly1994.ivideo.db.VideoDao;
import com.toly1994.ivideo.model.CtrlPanel;
import com.toly1994.ivideo.view.player.ICtrlPanelView;
import com.toly1994.ivideo.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:13:35<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class PlayerPresenter implements IPlayerPresenter {
    private ICtrlPanelView mICtrlPanelView;
    private VideoView mVideoView;
    private final CtrlPanel mPanel;
    private final VideoPlayerManager mManager;

    public PlayerPresenter(ICtrlPanelView ICtrlPanelView, VideoView videoView) {
        mICtrlPanelView = ICtrlPanelView;
        mICtrlPanelView.showLoading();
        mVideoView = videoView;
        mManager = new VideoPlayerManager(mVideoView);
        mPanel = new CtrlPanel();
    }

    @Override
    public void initVideo(int position) {
        mManager.setVideoPath(position);
        mPanel.name = mManager.getName();
        init();
    }

    @Override
    public void initVideo(String url) {
        mVideoView.setVideoURI(Uri.parse(url));
        init();
    }


    private void init() {
        mVideoView.setOnPreparedListener(
                mp -> {
                    mICtrlPanelView.hideLoading();
                    mPanel.allTime = Formater.formatTime(mVideoView.getDuration());
                    int progress = VideoDao.newInstance().getProgress(mVideoView.getPath());
                    mVideoView.seekTo(progress);
                    mPanel.rate = progress;
                    render(false);

                    mVideoView.setOnBufferingUpdateListener(pre -> {
                        mPanel.secondRate = pre;
                        render(false);
                    });

                    mVideoView.setOnProgressChanged(
                            (per_100, p) -> {
                                mPanel.rate = per_100;
                                mPanel.playedTime = Formater.formatTime(p);
                                mPanel.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                                        .format(System.currentTimeMillis());
                                render(false);
                            });

                }

        );

        mICtrlPanelView.showPanel();

    }

    @Override
    public void next() {
        mManager.next();
        render(true);//刷新面板
    }

    @Override
    public void prev() {
        mManager.prev();
        render(true);//刷新面板
    }

    @Override
    public void render(boolean force) {
        if (force) {//强制刷新，表示数据全部刷新
            mPanel.name = mManager.getName();
        }
        mICtrlPanelView.render(mPanel);
    }
}
