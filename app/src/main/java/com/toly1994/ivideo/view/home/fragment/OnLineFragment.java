package com.toly1994.ivideo.view.home.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.model.me.VideoResult;
import com.toly1994.ivideo.presenter.online.OnlinePresenter;
import com.toly1994.ivideo.view.home.IOnlineView;
import com.toly1994.ivideo.view.home.adapter.OnlineAdapter;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/2/002:10:28<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class OnLineFragment extends Fragment implements IOnlineView {
    private RecyclerView mRv;
    private OnlinePresenter mPresenter;
    private OnlineAdapter mAdapter;
    private SwipeRefreshLayout mSrl;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_online, container, false);
        mContext = view.getContext();
        init(view);

        return view;
    }

    private void init(View view) {
        mSrl = view.findViewById(R.id.id_srl);
        initLoading();
        mRv = view.findViewById(R.id.id_rv_online);
        mPresenter = new OnlinePresenter(this);
        mAdapter = new OnlineAdapter();

        mPresenter.render(false);
    }


    public void initLoading() {

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
            if (mSrl.isRefreshing()) {
                mPresenter.render(true);
                hideLoading();//关闭刷新动画
            }
        });
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void renderVideo(List<VideoResult.DataBean> videos) {
        mRv.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        mRv.setAdapter(mAdapter);
        mAdapter.setdata(videos);
    }

    @Override
    public OnlinePresenter getPresenter() {
        return mPresenter;
    }


    @Override
    public void showLoading() {
        mSrl.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSrl.setRefreshing(false);
    }
}
