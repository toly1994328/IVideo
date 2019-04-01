package com.toly1994.ivideo.presenter;

import android.content.Context;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.model.VideoScanner;
import com.toly1994.ivideo.view.IView;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:19:33<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class HomePresenter implements IHomePresenter {
    private IView mIView;
    private final VideoScanner mVideoScanner;
    private List<VideoInfo> videos;

    public HomePresenter(IView view) {
        mIView = view;
        mVideoScanner = new VideoScanner();
    }

    @Override
    public void render(boolean force) {
        if (!force && videos != null) {//非强制加载,且视频已加载
            mIView.render(videos);//使用加载过的视频
            return;
        }

        mVideoScanner.loadVideo((Context) mIView, videos -> {
            this.videos = videos;
            mIView.render(videos);
        }, () -> {
                mIView.render(this.videos);
        });
    }

    @Override
    public void delete(Context context, int videoId) {
        mVideoScanner.delete(context, videoId);
        render(true);
    }
}
