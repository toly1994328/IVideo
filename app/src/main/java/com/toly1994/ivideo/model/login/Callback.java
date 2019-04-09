package com.toly1994.ivideo.model.login;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/12/14 0014:13:43<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：模型层数据回调接口
 */
public interface Callback<T> {
    /**
     * 开始加载
     */
    void onStartLoad();

    /**
     * 成功
     * @param dataList 数据
     */
    void onSuccess(T dataList);

    /**
     * 错误
     * @param e 错误
     */
    void onError(Throwable e);
}
