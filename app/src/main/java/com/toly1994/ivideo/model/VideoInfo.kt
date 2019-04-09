package com.toly1994.ivideo.model

/**
 * 作者：张风捷特烈<br></br>
 * 时间：2019/3/8/008:15:54<br></br>
 * 邮箱：1981462002@qq.com<br></br>
 * 说明：
 */
data class VideoInfo (
    var videoId: Long,
    var title: String,
    var dataUrl: String,
    var duration: Long,
    var size: Long,
    var addDate: Long,
    var caverPath: String
)
