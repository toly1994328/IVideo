package com.toly1994.ivideo.view.itf;

import android.content.Context;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.model.dir.DirBean;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:13:33<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface IHomeView extends ILoadingView {
    Context getCtx();

    void renderVideo(List<VideoInfo> videos);

    void renderDir(List<DirBean> dirBeans);
}
