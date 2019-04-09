package com.toly1994.ivideo.app.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import com.toly1994.ivideo.app.download.DownloadCons;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/12 0012:14:19<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：下载的数据库帮助类
 */
public class DownLoadDBHelper extends SQLiteOpenHelper {

    private static DownLoadDBHelper sDownLoadDBHelper;

    public static DownLoadDBHelper newInstance(Context context) {
        if (sDownLoadDBHelper == null) {
            synchronized (DownLoadDBHelper.class) {
                if (sDownLoadDBHelper == null) {
                    sDownLoadDBHelper = new DownLoadDBHelper(context);
                }
            }
        }
        return sDownLoadDBHelper;
    }


    private DownLoadDBHelper(@Nullable Context context) {
        super(context, DownloadCons.DB_NAME, null, DownloadCons.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DownloadCons.DB_SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DownloadCons.DB_SQL_DROP);
        db.execSQL(DownloadCons.DB_SQL_CREATE);
    }
}
