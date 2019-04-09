package com.toly1994.ivideo.view.home.pop;

import android.content.Context;
import com.toly1994.ivideo.R;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/4/004:21:50<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class SortPopWindow extends BasePopWindow {
    public SortPopWindow(Context context) {
        super(context);
    }

    public SortPopWindow(Context context, int animStyleId) {
        super(context, animStyleId);
    }

    @Override
    public int layoutId() {
        return R.layout.pop_sort;
    }
}
