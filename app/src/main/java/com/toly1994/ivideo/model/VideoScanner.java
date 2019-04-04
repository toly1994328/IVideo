package com.toly1994.ivideo.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/10/30 0030:18:38<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：视频ContentProvide相关操作---生成视频List
 */
public class VideoScanner {
    private static String[] projection = new String[]{
            MediaStore.Video.Media._ID,//歌曲ID
            MediaStore.Video.Media.TITLE,//名称
            MediaStore.Video.Media.DURATION,//时长
            MediaStore.Video.Media.DATA,//歌曲路劲
            MediaStore.Video.Media.SIZE,//音乐的大小
            MediaStore.Video.Media.DATE_ADDED//音乐添加的时间
    };


    /**
     * @param context
     * @return
     */
    public void loadVideo(final Context context, OnLoadSuccess callback) {
        List<VideoInfo> videos = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection, "", null,
                "title", null);

        // 根据字段获取数据库中数据的索引
        int songIdIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int titleIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int durationIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
        int dataUrlIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        int sizeIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
        int addDateIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED);

        while (cursor.moveToNext()) {
            long videoId = cursor.getLong(songIdIdx);//获取歌曲id
            String title = cursor.getString(titleIdx);//获取名字
            String dataUrl = cursor.getString(dataUrlIdx);//获取路径
            long duration = cursor.getLong(durationIdx);//获取时长
            long size = cursor.getLong(sizeIdx);//获取艺术家(即歌手)id
            long addDate = cursor.getLong(addDateIdx);//时间
            videos.add(new VideoInfo(videoId, title, dataUrl, duration, size, addDate, ""));
        }
        if (callback != null) {
            callback.success(videos);
        }

    }

    public interface OnLoadSuccess {
        void success(List<VideoInfo> videos);
    }
}
