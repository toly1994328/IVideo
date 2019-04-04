package com.toly1994.ivideo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:13:19<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：数据库辅助类
 */
public class VideoDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "i_video.db";//数据库名
    private static int DATABASE_VERSION = 1;//数据库版本

    public VideoDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSwordTable(db);
    }

    private void createSwordTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE video_player (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "path VARCHAR(120) UNIQUE NOT NULL,\n" +
                "current_pos TINYINT NOT NULL DEFAULT 0,\n" +
                "last_play_time CHAR(24) NOT NULL,\n" +
                "play_count INT NOT NULL DEFAULT 0\n" +
                "); ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
