package com.toly1994.ivideo.app.download.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.toly1994.ivideo.app.download.DownloadCons;
import com.toly1994.ivideo.app.download.model.FileBean;
import com.toly1994.ivideo.app.download.presenter.DownloadPresenter;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/12 0012:16:05<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：更新ui的广播接收者
 */
public class UpdateReceiver extends BroadcastReceiver {

    private DownloadPresenter mPresenter;

    public UpdateReceiver( DownloadPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadCons.ACTION_UPDATE.equals(intent.getAction())) {//进度更新
            int loadedProgress = intent.getIntExtra(DownloadCons.SEND_LOADED_PROGRESS, 0);
            int id = intent.getIntExtra(DownloadCons.SEND_FILE_ID, 0);
            mPresenter.updateProgress(id, loadedProgress);

        } else if (DownloadCons.ACTION_FINISH.equals(intent.getAction())) {//下载结束
            FileBean fileBean = (FileBean) intent.getSerializableExtra(DownloadCons.SEND_FILE_BEAN);
            mPresenter.updateProgress(fileBean.getId(), 0);
            Toast.makeText(context, "文佳下载完成", Toast.LENGTH_SHORT).show();
        } else if (DownloadCons.ACTION_START_NF.equals(intent.getAction())) {
            FileBean fileBean = (FileBean) intent.getSerializableExtra(DownloadCons.SEND_FILE_BEAN);
        }
    }
}
