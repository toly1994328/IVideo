package com.toly1994.ivideo.app.api;

import android.content.Context;
import com.toly1994.ivideo.app.itf.CommonCBK;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.model.VideoScanner;
import com.toly1994.ivideo.model.dir.DirBean;

import java.io.File;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:7:22<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface VideoSourceApi {

    void getVideos(Context ctx, boolean force, VideoScanner.OnLoadSuccess callback, CommonCBK cbk);

    /**
     * 获取视频
     *
     * @return
     */

    List<VideoInfo> getVideos();
    List<VideoInfo> geTempVideos();

    /**
     * 通过名字过滤视频
     *
     * @param name
     * @return
     */
    List<VideoInfo> filterName(String name);

    List<DirBean> getVideoDirs();

    void delete(Context context, int id);

    List<VideoInfo> filter( File[] sons);


    List<VideoInfo> sortBy(SortType sortType);
}
