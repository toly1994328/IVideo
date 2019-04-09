package com.toly1994.ivideo.app.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.toly1994.ivideo.app.download.DownloadCons;
import com.toly1994.ivideo.app.download.model.ThreadBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/12 0012:14:43<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：数据访问接口实现类
 */
public class DownLoadDaoImpl implements DownLoadDao {

    private DownLoadDBHelper mDBHelper;
    private Context mContext;

    public DownLoadDaoImpl(Context context) {
        mContext = context;
        mDBHelper = DownLoadDBHelper.newInstance(mContext);
    }

    @Override
    public synchronized void insertThread(ThreadBean threadBean) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL(DownloadCons.DB_SQL_INSERT,
                new Object[]{threadBean.getId(), threadBean.getUrl(),
                        threadBean.getStart(), threadBean.getEnd(), threadBean.getLoadedLen()});
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL(DownloadCons.DB_SQL_DELETE,
                new Object[]{url});
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int threadId, long loadedLen) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL(DownloadCons.DB_SQL_UPDATE,
                new Object[]{loadedLen, url, threadId});
        db.close();
    }

    @Override
    public List<ThreadBean> getThreads(String url) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(DownloadCons.DB_SQL_FIND, new String[]{url});

        List<ThreadBean> threadBeans = new ArrayList<>();

        while (cursor.moveToNext()) {
            ThreadBean threadBean = new ThreadBean();
            threadBean.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadBean.setStart(cursor.getLong(cursor.getColumnIndex("start")));
            threadBean.setEnd(cursor.getLong(cursor.getColumnIndex("end")));
            threadBean.setLoadedLen(cursor.getLong(cursor.getColumnIndex("loadedLen")));
            threadBeans.add(threadBean);
        }
        cursor.close();
        db.close();
        return threadBeans;
    }
}
