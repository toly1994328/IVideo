package com.toly1994.ivideo.app.utils;

import android.os.SystemClock;
import android.view.View;
import com.toly1994.itf.CommonCBK;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/20:16:04
 * 邮箱：1981462002@qq.com
 * 说明：多次点击事件
 */
public class EventUtils {

    /**
     * n次点击事件
     *
     * @param view    需要点击的view
     * @param i       想要点击的次数
     * @param time_ms 毫秒数
     */
    public static void nClick(View view, int i, final int time_ms, CommonCBK cbk, CommonCBK cbk2) {
        final long[] mHits = new long[i];
        view.setOnClickListener(v -> {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();
            if (mHits[0] >= (SystemClock.uptimeMillis() - time_ms)) {
                cbk.callback();
            } else {
                cbk2.callback();
            }
        });

    }
}
