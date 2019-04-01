package com.toly1994.ivideo.app.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/2/002:1:21<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class ScreenUtils {

    public static void hideNavigationBar(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void hideStatusBar(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
