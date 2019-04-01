package com.toly1994.ivideo.model;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import com.toly1994.itf.CommonCBK;
import com.toly1994.ivideo.app.Cons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:15:52<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class CacheCaver {
    private List<VideoInfo> mVideos;
    private Handler mHandler;
    private final MediaMetadataRetriever retriever;

    public CacheCaver(List<VideoInfo> videos, Handler handler) {
        mVideos = videos;
        mHandler = handler;
        retriever = new MediaMetadataRetriever();

    }

    public void save(CommonCBK onLoaded) {
        for (VideoInfo video : mVideos) {
            File file = new File(Cons.CACHE_DIR + video.getTitle() + ".png");
            if (file.exists()) {
                video.setCaverPath(file.getAbsolutePath());
                doCallback(onLoaded);
                continue;
            }
            createNewFile(Cons.CACHE_DIR + video.getTitle() + ".png");
            Bitmap bitmap = decodeFrame(video.getDataUrl(), 15 * 1000);
            String path = saveBitmap(video.getTitle(), imageScale(bitmap, 0.3f));
            video.setCaverPath(path);
            doCallback(onLoaded);
        }
    }

    private void doCallback(CommonCBK onLoaded) {
        mHandler.post(() -> {
            if (onLoaded != null) {
                onLoaded.callback();
            }
        });

    }

    /**
     * 保存bitmap到本地
     *
     * @param mBitmap
     * @return
     */
    public String saveBitmap(String name, Bitmap mBitmap) {
        File filePic = createNewFile(Cons.CACHE_DIR + name + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }


    /**
     * 获取视频某一帧
     *
     * @param timeMs 毫秒
     */
    public Bitmap decodeFrame(String path, long timeMs) {
        retriever.setDataSource(path);
        Bitmap bitmap = retriever.getFrameAtTime(timeMs * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
        if (bitmap == null) {
            return null;
        }
        return bitmap;
    }


    public static Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        return Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
    }


    public static Bitmap imageScale(Bitmap bitmap, float rate) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(rate, rate);
        return Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
    }

    private File createNewFile(String path) {
        File file = new File(path);//1.创建文件
        if (file.exists()) {//2.判断文件是否存在
            return file;
        }
        File parent = file.getParentFile();//3.获取父文件
        if (!parent.exists()) {
            if (!parent.mkdirs()) {//4.创建父文件
                return file;
            }
        }
        try {
            file.createNewFile();//5.创建文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
