package com.toly1994.ivideo.app.utils.filer;

import java.io.File;

/**
 * 作者：张风捷特烈
 * 时间：2019/2/13/013:14:31
 * 邮箱：1981462002@qq.com
 * 说明：文件过滤接口
 */
public interface FileFilter {
    /**
     * 根据路径判断是否过滤出
     * @param path 路径
     * @return 是否可以执行filter
     */
    boolean iCanGo(File path);

    /**
     * 过滤的逻辑操作
     * @param file 文件
     * @param deep 该文件深度
     */
    void filter(File file, int deep);
}
