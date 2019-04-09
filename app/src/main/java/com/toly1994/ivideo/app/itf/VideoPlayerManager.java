package com.toly1994.ivideo.app.itf;

import android.net.Uri;
import com.toly1994.ivideo.app.api.VideoSourceApi;
import com.toly1994.ivideo.db.VideoBean;
import com.toly1994.ivideo.db.VideoDao;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.presenter.home.HomePresenter;
import com.toly1994.ivideo.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:7:15<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class VideoPlayerManager {

    private int mCurrentPos;
    private VideoSourceApi mVideoApi;
    private VideoView mVideoView;

    public VideoPlayerManager(VideoView videoView) {
        mVideoView = videoView;
        mVideoApi = HomePresenter.Companion.getVideoSourceApi();
    }

    public void setVideoPath(int position) {
        mCurrentPos = position;
        Uri url = Uri.parse(getCurrentVideoPath(mCurrentPos));

        VideoInfo info = mVideoApi.geTempVideos().get(position);
        VideoBean videoBean = new VideoBean();
        videoBean.setPath(info.getDataUrl());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        videoBean.setLast_play_time(format.format(System.currentTimeMillis()));
        VideoDao.newInstance().insert(videoBean);
//        VideoDao.newInstance().getRecent(5);
        VideoDao.newInstance().getMost(3);

        mVideoView.setVideoURI(url);

    }

    private String getCurrentVideoPath(int position) {
        return mVideoApi.geTempVideos().get(position).getDataUrl();
    }


    public String getName() {
        return new File(getCurrentVideoPath(mCurrentPos)).getName();
    }

    public void next() {
        mVideoView.pause();
        mCurrentPos++;
        judgePos();
        setVideoPath(mCurrentPos);
    }


    public void prev() {
        mVideoView.pause();
        mCurrentPos--;
        judgePos();
        setVideoPath(mCurrentPos);
    }

    /**
     * 越界处理
     */
    private void judgePos() {
        int songSize = mVideoApi.getVideos().size();
        if (mCurrentPos >= songSize) {
            mCurrentPos = 0;
        }
        if (mCurrentPos < 0) {
            mCurrentPos = songSize - 1;
        }
    }
}
