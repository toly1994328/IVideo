package com.toly1994.ivideo.app.utils;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/3/9/009:13:15<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class Formater {
    public static String formatTime(long duration) {
        long time = duration / 1000;
        String result = "";
        long minus = time / 60;
        int hour = 0;
        if (minus > 60) {
            hour = (int) (minus / 60);
            minus = minus % 60;
        }
        long second = time % 60;
        if (hour < 60) {
            result = handleNum(hour) + ":" + handleNum(minus)+":"+handleNum(second);
        }
        return result;
    }


    private static String handleNum(long num) {
        return num < 10 ? ("0" + num) : (num + "");
    }
}
