package com.toly1994.ivideo.app;

import android.os.Environment;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.model.IconItem;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:17:49<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class Cons {
    //视频的缓存图片目录
    public static String CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + "/Ivideo/cache/";


    public static final IconItem[] BNB_ITEM = new IconItem[]{
            new IconItem("本地视频", R.drawable.icon_video, R.color.icon_video),
            new IconItem("热点", R.drawable.icon_hot, R.color.icon_hot),
            new IconItem("设置", R.drawable.icon_setting, R.color.icon_setting),
            new IconItem("我的", R.drawable.icon_me, R.color.icon_me),
    };


    //配置 ： 开始时目录列表/文件列表
    public static String SP_LIST_DIR = "sp_list_dir";
}
