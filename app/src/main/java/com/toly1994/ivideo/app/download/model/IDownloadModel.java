package com.toly1994.ivideo.app.download.model;

import android.content.Context;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/9/009:8:44<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface IDownloadModel<T> {
    T getData();

    T getDownloadingData(Context context);
}
