package com.toly1994.ivideo.presenter.player;

import com.toly1994.ivideo.presenter.IPresenter;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:13:34<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface IPlayerPresenter extends IPresenter {


    void initVideo(int position);
    void initVideo(String url);
    void next();
    void prev();

}
