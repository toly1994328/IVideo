package com.toly1994.ivideo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:13:26<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：数据库操作层
 */
public class VideoDao {
    private static VideoDao sVideoDao;
    private SQLiteOpenHelper mHelper;

    public void setHelper(SQLiteOpenHelper helper) {
        mHelper = helper;
    }

    private VideoDao() {
    }

    public static VideoDao newInstance() {
        if (sVideoDao == null) {
            synchronized (VideoDao.class) {
                if (sVideoDao == null) {
                    sVideoDao = new VideoDao();
                }
            }
        }
        return sVideoDao;
    }

    /**
     * 插入
     *
     * @param video
     */
    public void insert(VideoBean video) {
        if (contains(video.getPath())) {
            addPlayCount(video.getPath());
        } else {
            mHelper.getWritableDatabase().execSQL(
                    "INSERT INTO video_player(path,current_pos,last_play_time,play_count) VALUES(?,?,?,?)",
                    new String[]{
                            video.getPath(),
                            video.getCurrent_pos() + "",
                            video.getLast_play_time(),
                            video.getPlay_count() + ""});
        }

    }

    /**
     * 将某视频播放量+1,并更新时间
     *
     * @param path 视频路径
     */
    private void addPlayCount(String path) {
        int count = getPlayCount(path);
        count++;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = format.format(System.currentTimeMillis());
        mHelper.getWritableDatabase().execSQL(
                "UPDATE video_player SET play_count=? , last_play_time=? WHERE path=?",
                new String[]{count + "", now, path});

    }

    /**
     * 根据路径获取播放量
     *
     * @param path 视频路径
     * @return 播放量
     */
    public int getPlayCount(String path) {
        int result = 0;
        Cursor cursor = mHelper.getReadableDatabase().
                rawQuery("SELECT play_count FROM video_player WHERE path=?", new String[]{path});
        if (cursor.moveToNext()) {
            result = cursor.getInt(cursor.getColumnIndex("play_count"));
        }
        cursor.close();
        return result;
    }

    /**
     * 检测是否包含某视频
     *
     * @param path 视频路径
     * @return 否包含某视频
     */
    public boolean contains(String path) {
        Cursor cursor = mHelper.getReadableDatabase().
                rawQuery("SELECT path FROM video_player WHERE path=?", new String[]{path});
        boolean has = cursor.moveToNext();
        cursor.close();
        return has;
    }

    /**
     * 保存播放进度
     */
    public void saveProgress(String path, int per) {
        if (contains(path)) {
            mHelper.getWritableDatabase().execSQL(
                    "UPDATE video_player SET current_pos=? WHERE path =?",
                    new String[]{per + "", path});
        }
    }

    /**
     * 根据路径获取播放进度
     *
     * @param path 视频路径
     * @return 播放进度
     */
    public int getProgress(String path) {
        int result = 0;
        Cursor cursor = mHelper.getReadableDatabase().
                rawQuery("SELECT current_pos FROM video_player WHERE path=?", new String[]{path});
        if (cursor.moveToNext()) {
            result = cursor.getInt(cursor.getColumnIndex("current_pos"));
        }
        cursor.close();
        return result;
    }

    /**
     * 获取最近播放的记录
     *
     * @param count 条数
     * @return
     */
    public String[] getRecent(int count) {
        String[] strings = new String[count];
        Cursor cursor = mHelper.getReadableDatabase().
                rawQuery("SELECT path FROM video_player ORDER BY last_play_time DESC LIMIT ?", new String[]{count + ""});
        int i = 0;
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex("path"));
            strings[i] = path;
            i++;
        }

        cursor.close();
        return strings;

    }


}
