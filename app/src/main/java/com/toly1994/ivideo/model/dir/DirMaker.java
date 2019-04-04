package com.toly1994.ivideo.model.dir;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/3/17/017:12:56<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class DirMaker {
    List<String> mPaths;

    Set<DirBean> mFileBeans;


    /**
     * 最大图片数量的文件夹图片数
     */
    public static int mMaxCount = 0;

    /**
     * 最大图片数量的文件夹
     */
    public static File mMaxCountDir = null;

    public DirMaker(List<String> paths) {
        mPaths = paths;
    }

    public void build() {
        List<DirBean> mFolderBeans = new ArrayList<>();
        Set<String> mDirPaths = new HashSet<>();
        for (String path : mPaths) {

            File parentFile = new File(path).getParentFile();

            if (parentFile == null) continue;
            //声明实体对象
            DirBean folderBean;
            //父目录的绝对路径：/storage/emulated/0/DCIM/Camera
            String dirPath = parentFile.getAbsolutePath();
            if (mDirPaths.contains(dirPath)) {
                continue;//集合中有这个目录 跳出本次循环
            } else {//集合中没有这个目录
                //加入集合
                mDirPaths.add(dirPath);
                //创建实体对象
                folderBean = new DirBean();
                //父文件夹设置到folderBean
                folderBean.setDir(dirPath);
                //第一张图片路径设置到folderBean
                folderBean.setFirstImgPath(path);
            }


            if (parentFile.list() != null) {
                //根据父文件夹，过滤出所有以jpg,png,jpeg结尾的文件的数量
                int imgCount = parentFile.list().length;

                if (mMaxCount <= imgCount) {
                    mMaxCount = imgCount;
                    mMaxCountDir = parentFile;
                }

                //设置文件夹下图片的数量
                folderBean.setCount(imgCount);
                //加入集合
                mFolderBeans.add(folderBean);
            }
        }

        Log.e("", "build: ");
    }
}
