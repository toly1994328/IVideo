package com.toly1994.ivideo.app.itf;

import android.net.Uri;
import android.widget.MediaController;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:7:07<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：视频播放行为
 */
public interface IVideoPlayer extends MediaController.MediaPlayerControl {
    void fitVideoSize(int videoW, int videoH, int surfaceW, int surfaceH);

    void changeSpeed(float speed);

    void setVideoPath(int position);

    void setVideoURI(Uri uri);

    void stopPlay();

    void next();

    void perv();
}
