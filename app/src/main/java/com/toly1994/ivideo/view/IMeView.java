package com.toly1994.ivideo.view;

import com.toly1994.ivideo.model.me.User;
import com.toly1994.ivideo.presenter.IMePresenter;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/8/008:7:30<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface IMeView extends ILoadingView,IView {
    void render(User user);

    IMePresenter getPresenter();
}
