package com.toly1994.ivideo.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.toly1994.ivideo.R
import com.toly1994.ivideo.app.utils.EventUtils
import com.toly1994.ivideo.app.utils.ScreenUtils
import com.toly1994.ivideo.model.CtrlPanel
import com.toly1994.ivideo.presenter.CtrlPresenter
import com.toly1994.ivideo.presenter.ICtrlPresenter
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.in_player_loading.*
import kotlinx.android.synthetic.main.in_player_panel_bottom.*
import kotlinx.android.synthetic.main.in_player_panel_top.*



/**
 * 作者：张风捷特烈<br></br>
 * 时间：2019/4/1/001:13:37<br></br>
 * 邮箱：1981462002@qq.com<br></br>
 * 说明：
 */
class CtrlView : AppCompatActivity(), ICtrlView {

    private lateinit var presenter: ICtrlPresenter;
    private val mHandler = Handler(Looper.getMainLooper())
    private var speeds = floatArrayOf(0.5f, 0.75f, 1f, 1.25f, 1.5f, 1.75f, 2.0f)
    private var curSpeedIdx = 2
    private var isLocked = false
    private var isShow = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ScreenUtils.hideStatusBar(this)
        setContentView(R.layout.activity_player)
        ScreenUtils.hideNavigationBar(this)

        val path = intent.getStringExtra("video-path")
        presenter = CtrlPresenter(this, id_vv)
        presenter.initVideo(path)
        doActions()
    }

    private fun doActions() {
        //点击屏幕显示面板 : 双击暂停/播放
        EventUtils.nClick(root, 2, 500, {
            if (!isLocked) {
                id_vv.togglePlay(id_iv_play, R.drawable.icon_start_3, R.drawable.icon_stop_3)
            }
        }, {
            if (!isLocked) {
                showPanel()
            }
            id_iv_lock.visibility = View.VISIBLE

        })

        //播放
        id_iv_play.setOnAlphaListener {
            id_vv.togglePlay(id_iv_play, R.drawable.icon_start_3, R.drawable.icon_stop_3)
        }

        //变速
        id_tv_speed.setOnClickListener {
            curSpeedIdx++
            if (curSpeedIdx == speeds.size) {
                curSpeedIdx = 0
            }
            val speed = speeds[curSpeedIdx]
            id_vv.changeSpeed(speed)
            id_tv_speed.text = "$speed X"
        }

        //返回
        id_iv_back.setOnAlphaListener {
            finish()
        }

        //进度条拖动监听
        id_sb_progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                id_vv.seekTo(id_sb_progress.progress)
            }
        })

        id_iv_lock.initTwoButton(
            R.drawable.icon_unlock, { v ->
                showPanel()
                isLocked = false
            }, R.drawable.icon_lock, { v ->
                hidePanel()
                isLocked = true
            })
    }

    override fun render(ctrlPanel: CtrlPanel) {
        id_tv_name.text = ctrlPanel.name
        id_tv_time.text = ctrlPanel.time
        id_tv_all_time.text = ctrlPanel.playedTime + "/" + ctrlPanel.allTime
        id_sb_progress.progress = ctrlPanel.rate
    }


    override fun hideLoading() {
        ld_rl_loading.visibility = View.GONE
    }

    override fun showLoading() {
        ld_rl_loading.visibility = View.VISIBLE
    }


    override fun showPanel() {
        Log.e("showPanel", "showPanel: " + isShow);
        if (isShow) {
            hidePanel()
            isShow = !isShow
            return
        }

        id_ll_top.visibility = View.VISIBLE
        id_ll_bottom.visibility = View.VISIBLE
        id_iv_lock.visibility = View.VISIBLE
        id_sb_progress.visibility = View.VISIBLE

        presenter.render(false)
        isShow = !isShow
        //使用Handler五秒钟之后隐藏面板
        mHandler.removeMessages(100)
        val msg = Message.obtain(mHandler) {
            hidePanel()
            hideLock()
        }
        msg.what = 100
        mHandler.sendMessageDelayed(msg, 5000)
    }

    override fun hidePanel() {
        id_ll_top.visibility = View.GONE
        id_ll_bottom.visibility = View.GONE
        id_sb_progress.visibility = View.GONE
    }

    fun hideLock() {
        id_iv_lock.visibility = View.GONE
    }

}
