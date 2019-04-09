package com.toly1994.ivideo.presenter.home

import android.content.Context
import com.toly1994.ivideo.app.Cons
import com.toly1994.ivideo.app.api.VideoSourceApiImpl
import com.toly1994.ivideo.app.utils.SpUtils
import com.toly1994.ivideo.view.home.IHomeView

/**
 * 作者：张风捷特烈<br></br>
 * 时间：2019/4/1/001:19:33<br></br>
 * 邮箱：1981462002@qq.com<br></br>
 * 说明：
 */
class HomePresenter(private val mIHomeView: IHomeView) : IHomePresenter {

    companion object {
        var videoSourceApi: VideoSourceApiImpl = VideoSourceApiImpl()
    }

    override fun render(force: Boolean) {
        val isListDir = SpUtils.newInstance().getBoolean(Cons.SP_LIST_DIR)//是否列表文件夹

        mIHomeView.showLoading()

        videoSourceApi.getVideos(mIHomeView.context, force, { videos ->
            show(isListDir)
        }, { show(isListDir) })
    }

    private fun show(isListDir: Boolean) {
        if (isListDir) {
            mIHomeView.renderDir(videoSourceApi.videoDirs)
        } else {
            mIHomeView.renderVideo(videoSourceApi.videos)
        }
        mIHomeView.hideLoading()
    }

    override fun delete(context: Context, videoId: Int) {
        videoSourceApi.delete(context, videoId)
        render(true)
    }

    override fun filter(name: String) {
        mIHomeView.renderVideo(videoSourceApi.filterName(name))
    }

    fun isLogin() {

    }

}
