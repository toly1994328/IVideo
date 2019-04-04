package com.toly1994.ivideo.app;

import android.app.Application;
import com.toly1994.ivideo.app.utils.SpUtils;
import com.toly1994.ivideo.db.VideoDao;
import com.toly1994.ivideo.db.VideoDatabaseHelper;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/2/002:19:34<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class VideoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtils sp = SpUtils.newInstance();
        sp.init(this);

        VideoDao.newInstance()
                .setHelper(new VideoDatabaseHelper(this));
    }
}
