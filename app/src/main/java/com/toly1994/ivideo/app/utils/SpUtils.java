package com.toly1994.ivideo.app.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 作者：张风捷特烈
 * 时间：2018/2/20:17:57
 * 邮箱：1981462002@qq.com
 * 说明：SharedPreferences工具类
 */
public class SpUtils {

    private static SharedPreferences sp;
    private static SpUtils sSpUtils;

    private SpUtils() {
    }

    public void init(Context context) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static SpUtils newInstance() {
        if (sSpUtils == null) {
            synchronized (SpUtils.class) {
                if (sSpUtils == null) {
                    sSpUtils = new SpUtils();

                }
            }
        }
        return sSpUtils;
    }
////////////////////////////////////boolean类型///////////////////////////////////////////////

    /**
     * 写入boolean变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值 boolean
     */
    public void setBoolean(String key, boolean value) {
        //(存储节点文件名称,读写方式)
        sp.edit().putBoolean(key, value).apply();
    }


    /**
     * 2参数：读取boolean标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 默认值
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }


    /**
     * 1参数: 读取boolean标示从sp中
     *
     * @param key 存储节点名称
     * @return
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


////////////////////////////////////String类型///////////////////////////////////////////////

    /**
     * 写入String变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值string
     */
    public void setString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    /**
     * 2参数:从sp中读取String
     *
     * @param key      存储节点名称
     * @param defValue 默认值
     * @return
     */
    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }


    /**
     * 1参数:从sp中读取String
     *
     * @param key 存储节点名称
     * @return
     */
    public String getString(String key) {
        return getString(key, "");
    }

///////////////////////////int类型///////////////////////////

    /**
     * 写入int变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值string
     */
    public void setInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    /**
     * 2参数:从sp中读取int
     *
     * @param key      存储节点名称
     * @param defValue 默认值
     * @return
     */
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }


    /**
     * 1参数:从sp中读取int
     *
     * @param key 存储节点名称
     * @return
     */
    public int getInt(String key) {

        return getInt(key, 0);
    }

    /////////////////////////////////////////////////////////////////

    /**
     * 从sp中移除指定节点
     *
     * @param key 需要移除节点的名称
     */
    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

}
