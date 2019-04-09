package com.toly1994.ivideo.view.home.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.model.dir.DirBean;
import com.toly1994.ivideo.presenter.home.HomePresenter;
import com.toly1994.ivideo.view.home.adapter.DirAdapter;
import com.toly1994.ivideo.view.home.adapter.HomeAdapter;
import com.toly1994.ivideo.view.home.IHomeView;

import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/2/002:10:28<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class HomeFragment extends Fragment implements IHomeView {
    private RecyclerView mRv;
    private HomePresenter mPresenter;
    private HomeAdapter mAdapter;
    private SwipeRefreshLayout mSrl;
    private Context mContext;
    public static boolean IS_IN_DIR = false;
    private DirAdapter mDirAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home, container, false);
        mContext = view.getContext();
        init(view);

        return view;
    }

    private void init(View view) {
        mSrl = view.findViewById(R.id.id_srl);
        initLoading();
        mRv = view.findViewById(R.id.id_rv_home);
        mPresenter = new HomePresenter(this);
        mAdapter = new HomeAdapter(mPresenter);
        mDirAdapter = new DirAdapter(mPresenter);


        mDirAdapter.setOnItemClickListener((view1, Position, sons) -> {
            IS_IN_DIR = true;
            List<VideoInfo> videos = HomePresenter.Companion.getVideoSourceApi().filter(sons);
            renderVideo(videos);
        });

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
    public void renderVideo(List<VideoInfo> videos) {
        mRv.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        mRv.setAdapter(mAdapter);
        mAdapter.setdata(videos);
    }

    @Override
    public void renderDir(List<DirBean> dirBeans) {
        mRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRv.setAdapter(mDirAdapter);
        mDirAdapter.setdata(dirBeans);
    }

    @Override
    public HomePresenter getPresenter() {
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
