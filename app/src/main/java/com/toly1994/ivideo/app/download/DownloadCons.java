package com.toly1994.ivideo.app.download;

import android.os.Environment;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/12 0012:12:29<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：http://www.192.168.43.60:8089/file/Xml的Pull解析（上）.mp4
 */
public class DownloadCons {
    /**
     * intent相关常量
     */

    //intent传递数据----开始下载时，传递FileBean到Service 标示
    public static final String SEND_FILE_BEAN = "send_file_bean";
    //广播更新进度
    public static final String SEND_LOADED_PROGRESS = "send_loaded_progress";
    //广播更新进度---对应文件id
    public static final String SEND_FILE_ID = "send_file_id";

    //打开服务----开始下载
    public static final String ACTION_STOP = "action_stop";
    //打开服务----停止下载
    public static final String ACTION_START = "action_start";

    public static final String ACTION_UPDATE = "action_update";//更新进度
    public static final String ACTION_FINISH = "action_finish";//下载完成

    //广播---显示通知栏
    public static final String ACTION_START_NF = "action_start_nf";

    /**
     * 基础的url
     */
//    public static final String BASE_URL = "http://192.168.43.60:8089/";
    public static final String BASE_URL = "http://192.168.10.101:8089/";
    /**
     * 下载文件的url
     */
    public static final String BASE_FILE_URL = BASE_URL + "file/";

    //掘金下载地址
    public static final String URL_JUEJIN = "https://imtt.dd.qq.com/16891/4611E43165D203CB6A52E65759FE7641.apk?fsname=com.daimajia.gold_5.6.2_196.apk&csr=1bbd";
    //qq下载地址
    public static final String URL_QQ = "https://qd.myapp.com/myapp/qqteam/Androidlite/qqlite_3.7.1.704_android_r110206_GuanWang_537057973_release_10000484.apk";
    //有道云笔记下载地址
    public static final String URL_YOUDAO = "http://codown.youdao.com/note/youdaonote_android_6.3.5_youdaoweb.apk";
    //微信下载地址
    public static final String URL_WEIXIN = "http://gdown.baidu.com/data/wisegame/3d4de3ae1d2dc7d5/weixin_1360.apk";
    //有道词典下载地址
    public static final String URL_YOUDAO_CIDIAN = "http://codown.youdao.com/dictmobile/youdaodict_android_youdaoweb.apk";


    //文件下载路径
    public static final String DOWNLOAD_DIR =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/b_download/";

    //Handler的Message处理的常量
    public static final int MSG_CREATE_FILE_OK = 0x00;


    /**
     * 数据库相关常量
     */
    public static final String DB_NAME = "download.db";//数据库名
    public static final int VERSION = 1;//版本

    public static final String DB_TABLE_NAME = "thread_info";//数据库名

    public static final String DB_SQL_CREATE = //创建表
            "CREATE TABLE " + DB_TABLE_NAME + "(\n" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "thread_id INTEGER,\n" +
                    "url TEXT,\n" +
                    "start INTEGER,\n" +
                    "end INTEGER,\n" +
                    "loadedLen INTEGER\n" +
                    ")";

    public static final String DB_SQL_DROP =//删除表表
            "DROP TABLE IF EXISTS " + DB_TABLE_NAME;

    public static final String DB_SQL_INSERT =//插入
            "INSERT INTO " + DB_TABLE_NAME + " (thread_id,url,start,end,loadedLen) values(?,?,?,?,?)";

    public static final String DB_SQL_DELETE =//删除
            "DELETE FROM " + DB_TABLE_NAME + " WHERE url = ?";

    public static final String DB_SQL_UPDATE =//更新
            "UPDATE " + DB_TABLE_NAME + " SET loadedLen = ? WHERE url = ? AND thread_id = ?";

    public static final String DB_SQL_FIND =//查询
            "SELECT * FROM " + DB_TABLE_NAME + " WHERE url = ?";
}
