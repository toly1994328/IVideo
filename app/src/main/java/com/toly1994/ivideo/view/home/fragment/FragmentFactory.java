package com.toly1994.ivideo.view.home.fragment;

import android.app.Fragment;
import android.util.SparseArray;

/**
 * 生产fragment
 * Created by Administrator on 2017/11/19.
 */
public class FragmentFactory {
    /**
     * 根据位置生产Fragment
     *
     * @param pos
     * @return
     */
    //为提高性能，不重复new fragment，采用集合装载，以便复用
    private static SparseArray<Fragment> mFragmentMap = new SparseArray<>();

    public static Fragment getFragment(int pos) {

        Fragment fragment = mFragmentMap.get(pos);////fragment在集合中，则复用

        if (fragment == null) {//fragment为空，则新建
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new TestFragment();
                    break;
                case 2:
                    fragment = new OnLineFragment();
                    break;
                case 3:
                    fragment = new MeFragment();
                    break;
                case 4:
                    fragment = new BarFragment();
                    break;
                default:
                    break;
            }
            mFragmentMap.put(pos, fragment);//将新建的fragment加入集合中
        }
        return fragment;

    }

}