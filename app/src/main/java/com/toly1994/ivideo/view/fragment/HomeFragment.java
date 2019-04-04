package com.toly1994.ivideo.view.fragment;

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
import com.toly1994.ivideo.app.utils.RvDivider;
import com.toly1994.ivideo.model.VideoInfo;
import com.toly1994.ivideo.model.dir.DirBean;
import com.toly1994.ivideo.presenter.HomePresenter;
import com.toly1994.ivideo.view.adapter.DirAdapter;
import com.toly1994.ivideo.view.adapter.HomeAdapter;
import com.toly1994.ivideo.view.itf.IHomeView;

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

    private DirAdapter mDirAdapter;
    private RvDivider mRvDivider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home, container, false);
        mContext = view.getContext();
        mRvDivider = new RvDivider(getCtx(), R.drawable.shap_divider);
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
    public Context getCtx() {
        return mContext;
    }

    @Override
    public void renderVideo(List<VideoInfo> videos) {
        mRv.removeItemDecoration(mRvDivider);
        mRv.setLayoutManager(new GridLayoutManager(getCtx(), 2));
        mRv.setAdapter(mAdapter);
        mAdapter.setdata(videos);
    }

    @Override
    public void renderDir(List<DirBean> dirBeans) {
        mRv.setLayoutManager(new LinearLayoutManager(getCtx()));
        mRv.setAdapter(mDirAdapter);
        mRv.addItemDecoration(mRvDivider);
        mDirAdapter.setdata(dirBeans);
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
