package com.toly1994.ivideo.view.home.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/10/31 0031:12:50<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("TestFragment");
        return textView;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        TextView viewById = mRootView.findViewById(R.id.tv_content);
//        String name = getArguments().getString("name_data");
//        viewById.setText(name);
//
//    }
}