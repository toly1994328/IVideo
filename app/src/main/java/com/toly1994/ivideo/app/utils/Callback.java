package com.toly1994.ivideo.app.utils;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/3/17/017:7:05<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface Callback {
    void callback();

    Callback DEFAULT = new Callback() {
        @Override
        public void callback() {

        }
    };
}

