package com.toly1994.ivideo.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.permission.Permission;
import com.toly1994.ivideo.app.permission.PermissionActivity;
import com.toly1994.ivideo.app.utils.ScreenUtils;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.presenter.HomePresenter;
import com.toly1994.ivideo.presenter.IHomePresenter;
import com.toly1994.ivideo.view.adapter.HomeAdapter;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:19:34<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class HomeView extends PermissionActivity implements IView {


    private RecyclerView mRv;
    private SwipeRefreshLayout mSrl;
    private HomeAdapter mAdapter;
    private IHomePresenter mPresenter;


    private Handler mHandler = new Handler(msg -> {
        if (mSrl.isRefreshing()) {
            mSrl.setRefreshing(false);//关闭刷新动画
            mPresenter.render(true);
        }
        return false;
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(this);
        setContentView(R.layout.activity_home);

        applyPermissions(Permission.WRITE_EXTERNAL_STORAGE);
        initLoading();

    }


    @Override
    protected void permissionOk(boolean isFirst) {
        mRv = findViewById(R.id.id_rv_home);

        mPresenter = new HomePresenter(this);
        mAdapter = new HomeAdapter(mPresenter);
        mPresenter.render(false);

        mRv.setLayoutManager(new GridLayoutManager(this, 2));
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void render(List<VideoInfo> videos) {
        mAdapter.setdata(videos);
    }

    public void initLoading() {
        mSrl = findViewById(R.id.id_srl);

        //每转一圈，换一种颜色
        mSrl.setColorSchemeColors(
                0xffF60C0C,//红
                0xffF3B913,//橙
                0xffE7F716,//黄
                0xff3DF30B,//绿
                0xff0DF6EF,//青
                0xff0829FB,//蓝
                0xffB709F4//紫
        );

        mSrl.setOnRefreshListener(() -> {
            mHandler.sendEmptyMessage(0);
        });
    }
}
