package com.toly1994.ivideo.view.login;

import android.content.Context;
import com.toly1994.ivideo.view.ILoadingView;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/7/007:17:18<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface ILoginView extends ILoadingView {
    void showError(String message);


    Context getCtx();

    void finish();

}
