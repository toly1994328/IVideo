package com.toly1994.ivideo.app.download.view;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.download.DownloadCons;
import com.toly1994.ivideo.app.download.model.FileBean;
import com.toly1994.ivideo.app.download.presenter.DownloadPresenter;
import com.toly1994.ivideo.app.download.receiver.UpdateReceiver;
import com.toly1994.ivideo.app.permission.Permission;
import com.toly1994.ivideo.app.permission.PermissionActivity;
import com.toly1994.ivideo.app.utils.ScreenUtils;

import java.util.List;

public class DownLoadView extends PermissionActivity implements IDownloadView<List<FileBean>> {
    RecyclerView mIdRvPage;

    private UpdateReceiver mUpdateReceiver;
    private DownloadAdapter mAdapter;
    private DownloadPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(this);
        setContentView(R.layout.activity_download);
        applyPermissions(Permission.WRITE_EXTERNAL_STORAGE);



    }

    @Override
    protected void permissionOk(boolean isFirst) {
        mIdRvPage = findViewById(R.id.id_rv_page);
        mAdapter = new DownloadAdapter();
        mIdRvPage.setLayoutManager(new LinearLayoutManager(this));
        mIdRvPage.setAdapter(mAdapter);

        mPresenter = new DownloadPresenter(this);
        mPresenter.render(true);

        register();//注册广播接收者
    }

    /**
     * 注册广播接收者
     */
    private void register() {
        //注册广播接收者
        mUpdateReceiver = new UpdateReceiver(mPresenter);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadCons.ACTION_UPDATE);
        filter.addAction(DownloadCons.ACTION_FINISH);
        filter.addAction(DownloadCons.ACTION_START_NF);
        registerReceiver(mUpdateReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUpdateReceiver != null) {//注销广播
            unregisterReceiver(mUpdateReceiver);
        }
    }

    @Override
    public void render(List<FileBean> data) {
        mAdapter.setData(data);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
