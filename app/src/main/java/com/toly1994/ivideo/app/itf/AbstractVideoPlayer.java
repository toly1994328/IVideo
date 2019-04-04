package com.toly1994.ivideo.app.itf;

import android.net.Uri;
import com.toly1994.ivideo.app.api.VideoSourceApi;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:7:15<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public abstract class AbstractVideoPlayer implements IVideoPlayer {

    private int mCurrentPos;
    private VideoSourceApi mVideoApi;

    @Override
    public void setVideoPath(int position) {
        Uri url = Uri.parse(getCurrentVideoPath(mCurrentPos));
        setVideoURI(url);
    }

    private String getCurrentVideoPath(int position) {
        return mVideoApi.getVideos().get(position).getDataUrl();
    }

    public void setVideoApi(VideoSourceApi videoApi) {
        mVideoApi = videoApi;
    }
}
