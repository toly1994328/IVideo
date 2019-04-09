package com.toly1994.ivideo.view.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.toly1994.ivideo.R;
import com.toly1994.ivideo.app.Cons;
import com.toly1994.ivideo.app.permission.Permission;
import com.toly1994.ivideo.app.permission.PermissionActivity;
import com.toly1994.ivideo.app.utils.KeyboardUtils;
import com.toly1994.ivideo.app.utils.ScreenUtils;
import com.toly1994.ivideo.app.utils.SpUtils;
import com.toly1994.ivideo.model.IconItem;
import com.toly1994.ivideo.presenter.IMePresenter;
import com.toly1994.ivideo.presenter.home.HomePresenter;
import com.toly1994.ivideo.view.IMeView;
import com.toly1994.ivideo.view.home.fragment.FragmentFactory;
import com.toly1994.ivideo.view.home.fragment.HomeFragment;
import com.toly1994.ivideo.view.home.pop.SortPopWindow;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/4/1/001:19:34<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：主界面
 */
public class HomeView extends PermissionActivity {

    private BottomNavigationBar mIdBnb;
    private HomePresenter mHomePresenter;
    private IMePresenter mMePresenter;
    private SortPopWindow mSortPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(this);

        setContentView(R.layout.drawer_pager);


        applyPermissions(Permission.WRITE_EXTERNAL_STORAGE);
        initSearch();
        initBnb();
        drawerLayoutToggle();
        mSortPopWindow = new SortPopWindow(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mHomePresenter = ((IHomeView) FragmentFactory.getFragment(0)).getPresenter();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        mMePresenter = ((IMeView) FragmentFactory.getFragment(3)).getPresenter();
        super.onNewIntent(intent);
        String login_ok = intent.getStringExtra("login_ok");
        if ("login_ok".equals(login_ok)) {
            Log.e(TAG, "onCreate: " + login_ok);
            mMePresenter.render(true);
            mIdBnb.selectTab(3);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHomePresenter != null) {
            mHomePresenter.render(true);
        }
    }

    private void initBnb() {
        mIdBnb = findViewById(R.id.id_bnb);
        IconItem[] items = Cons.BNB_ITEM;
        for (IconItem item : items) {
            mIdBnb.addItem(
                    new BottomNavigationItem(
                            item.getIconId(),
                            item.getInfo())
                            .setActiveColorResource(item.getColor()));
        }
        mIdBnb.initialise();

        mIdBnb.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                setFragmentAt(position);
                if (position == 3) {
                    mSlideFrameLayout.setVisibility(View.GONE);
                } else {
                    mSlideFrameLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }


    @Override
    protected void permissionOk(boolean isFirst) {
        setFragmentAt(0);
    }

    private void setFragmentAt(int contentId) {
        FragmentManager fm = getFragmentManager();//1.获取FragmentManager
        FragmentTransaction ft = fm.beginTransaction();//2.fm开启事务
        ft.replace(R.id.id_fl_place, FragmentFactory.getFragment(contentId));
        ft.commit();//4.提交事务
    }

    private ActionBarDrawerToggle mABDT;
    DrawerLayout mIdDlRoot;

    private void drawerLayoutToggle() {
        mIdDlRoot = findViewById(R.id.id_dl_root);
        mABDT = new ActionBarDrawerToggle(
                this, mIdDlRoot, mToolbar, R.string.str_open, R.string.str_close);
        mIdDlRoot.addDrawerListener(mABDT);

        mIdDlRoot.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.e(TAG, "onDrawerSlide: " + slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                CheckBox box = drawerView.findViewById(R.id.id_cb_list_dir);
                box.setChecked(SpUtils.newInstance().getBoolean(Cons.SP_LIST_DIR));

                box.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    SpUtils.newInstance().setBoolean(Cons.SP_LIST_DIR, isChecked);

                });
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if (!HomeFragment.IS_IN_DIR) {
                    mHomePresenter.render(false);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mABDT.syncState();//加了这个才有酷炫的按钮变化
    }

    //----------------------搜索处理-----------------------
    private FrameLayout mSlideFrameLayout;
    private EditText mEdSearch;
    private Toolbar mToolbar;
    private static final String TAG = "HomeView";
    private boolean mShowSearchToolbar = false;

    private void initSearch() {
        registerView();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
    }



    private void registerView() {
        mEdSearch = findViewById(R.id.ed_header_search);
        mSlideFrameLayout = findViewById(R.id.rframe_main);
        mToolbar = findViewById(R.id.toolbar_gplay_search);
        ImageView ivBackSearch = findViewById(R.id.id_iv_search_back);

        mEdSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String result = v.getText().toString();
                mHomePresenter.filter(result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                reactionToBackPressed();
            }
            return false;
        });

        ivBackSearch.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_search:
                reactionToClickSearchAction();
                break;
            case R.id.action_sort:
                showSort();
                break;
        }
        return true;
    }

    private void showSort() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mSortPopWindow.showAtLocation(mToolbar,
                Gravity.NO_GRAVITY,
                outMetrics.widthPixels - mSortPopWindow.getWidth(),
                mToolbar.getHeight());
    }

    /**
     * 点击搜索
     */
    private void reactionToClickSearchAction() {
        mShowSearchToolbar = true;

        View childView = mSlideFrameLayout.getChildAt(0);
        childView.setVisibility(View.VISIBLE);
        childView.bringToFront();

        int centerX = childView.getRight();
        int centerY = childView.getBottom() / 2;
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(childView, centerX, centerY, 0, childView.getWidth());
        circularReveal.setDuration(300).setInterpolator(new LinearInterpolator());
        circularReveal.start();

        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mEdSearch.requestFocus();
                KeyboardUtils.showSoftInput(mEdSearch, HomeView.this);
            }
        });
    }

    private boolean reactionToBackPressed() {
        if (mShowSearchToolbar) {
            KeyboardUtils.hideSoftInput(mEdSearch, this);

            View childView = mSlideFrameLayout.getChildAt(0);
            childView.bringToFront();

            int centerX = childView.getLeft();
            int centerY = childView.getBottom() / 2;
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(childView, centerX, centerY, 0, childView.getWidth());
            circularReveal.setDuration(300).setInterpolator(new DecelerateInterpolator());
            circularReveal.start();
            mShowSearchToolbar = false;
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (reactionToBackPressed()) return;
        if (HomeFragment.IS_IN_DIR) {
            mHomePresenter.render(false);
            HomeFragment.IS_IN_DIR = false;
            return;
        }
        super.onBackPressed();
    }

}
