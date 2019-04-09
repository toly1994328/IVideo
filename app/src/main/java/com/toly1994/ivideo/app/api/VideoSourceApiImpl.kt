package com.toly1994.ivideo.app.api

import android.content.Context
import android.os.Handler
import android.provider.MediaStore
import com.toly1994.ivideo.app.itf.CommonCBK
import com.toly1994.ivideo.model.CacheCaver
import com.toly1994.ivideo.model.VideoInfo
import com.toly1994.ivideo.model.VideoScanner
import com.toly1994.ivideo.model.dir.DirBean
import java.io.File
import java.util.*
import java.util.concurrent.Executors

/**
 * 作者：张风捷特烈<br></br>
 * 时间：2019/4/4/004:7:48<br></br>
 * 邮箱：1981462002@qq.com<br></br>
 * 说明：
 */
class VideoSourceApiImpl : VideoSourceApi {
    override fun geTempVideos(): List<VideoInfo>? {
        return tempVideos

    }


    private val exe = Executors.newSingleThreadExecutor()

    private var videos: List<VideoInfo>? = null
    private var tempVideos: List<VideoInfo>? = null

    private val mHandler: Handler = Handler()

    private val mFolderBeans = ArrayList<DirBean>()


    override fun getVideos(): List<VideoInfo> {
        if (videos != null) {
            return videos as List<VideoInfo>
        } else {
            throw IllegalArgumentException(
                "videos not init,please call [getVideos(Context ctx, VideoScanner.OnLoadSuccess callback, CommonCBK cbk)]"
            )
        }
    }

    override fun filterName(name: String): List<VideoInfo>? {
        tempVideos = getVideos().filter {
            it.dataUrl.contains(name)
        }
        return tempVideos

    }

    override fun getVideoDirs(): List<DirBean> {
        if (mFolderBeans.size > 0) {
            return mFolderBeans
        }
        val videos = getVideos()
        val mDirPaths = HashSet<String>()
        for ((_, _, dataUrl) in videos) {
            val folderBean: DirBean
            val file = File(dataUrl)
            val parentFile = file.parentFile ?: continue //没有父目录，跳出本次循环

            val dirPath = parentFile.absolutePath//获取父文件夹名称
            if (mDirPaths.contains(dirPath)) {//集合中有这个目录 跳出本次循环
                continue
            } else {//集合中没有这个目录
                mDirPaths.add(dirPath)//加入集合
                folderBean = DirBean()//创建实体对象
                folderBean.dir = dirPath//父文件夹设置到folderBean
                folderBean.firstImgPath = dataUrl//第一张图片路径设置到folderBean
            }
            if (parentFile.list() != null) {
                //根据父文件夹，过滤出文件的数量
                val imgCount = parentFile.list { dir, name ->
                    val b = name.endsWith(".mp4")
                    b
                }.size

                folderBean.count = imgCount//设置文件夹下图片的数量
                mFolderBeans.add(folderBean) //加入集合
            }
        }
        return mFolderBeans
    }

    override fun getVideos(ctx: Context, force: Boolean, callback: VideoScanner.OnLoadSuccess?, cbk: CommonCBK?) {
        if (videos != null && !force) {
            callback?.success(videos)
            return
        }
        exe.execute {
            val scanner = VideoScanner()
            scanner.loadVideo(ctx) { videos ->
                mHandler.post {
                    if (callback != null) {
                        this.videos = videos
                        callback.success(videos)
                    }
                }
                if (cbk != null) {
                    val caver = CacheCaver(videos, mHandler)
                    caver.save(cbk)
                }
            }
        }
    }

    override fun delete(context: Context, id: Int) {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        context.contentResolver.delete(uri, "_id=$id", null)
    }

    override fun filter(sons: Array<File>): List<VideoInfo> {
        tempVideos = getVideos().filter {
            sons.contains(File(it.dataUrl))
        }
        return tempVideos as List<VideoInfo>

    }

    override fun sortBy(sortType: SortType?): List<VideoInfo> {
//        val videos = getVideos()
//        Collections.sort(videos, ())
//
//        when (sortType) {
//            SortType.DIR->
//
//        }
        return getVideos()

    }
}
