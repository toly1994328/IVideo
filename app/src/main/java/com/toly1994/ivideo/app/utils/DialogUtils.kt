package com.toly1994.ivideo.app.utils

import android.app.Activity
import android.support.v7.app.AlertDialog

/**
 * 作者：张风捷特烈<br></br>
 * 时间：2019/3/17/017:6:52<br></br>
 * 邮箱：1981462002@qq.com<br></br>
 * 说明：
 */
object DialogUtils {
    @JvmStatic
    fun showNormalDialog(
        activity: Activity,
        resID: Int,
        info: String,
        cbkOK: Callback,
        cbkNo: Callback = Callback.DEFAULT,
        msg: String = "温馨提示"
    ) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        val normalDialog = AlertDialog.Builder(activity)
        normalDialog.setIcon(resID)
        normalDialog.setTitle(msg)
        normalDialog.setMessage(info)
        normalDialog.setPositiveButton("确定") { dialog, which ->
            cbkOK.callback()
        }
        normalDialog.setNegativeButton(
            "关闭"
        ) { dialog, which ->
            cbkNo.callback()
        }
        // 显示
        normalDialog.show()
    }
}
