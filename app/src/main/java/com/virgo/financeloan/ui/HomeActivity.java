package com.virgo.financeloan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.fragment.LoansFragment;
import com.virgo.financeloan.ui.fragment.MineFragment;
import com.virgo.financeloan.ui.fragment.HomeFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 主Activity
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-23 上午12:01
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class HomeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    //底部导航控件
    @BindView(R.id.tabLayout)
    TabLayout mBottomView;
    //按两次返回退出应用
    private static boolean isExit;

    private Fragment oldFragment;

    //底部导航资源id
    private TextView[] mTxtArray = new TextView[3];
    private ImageView[] mImageArray = new ImageView[3];//R.string.title_home,    R.drawable.nav_home,
    private int[] mStringId = {R.string.tab_home, R.string.tab_loans, R.string.tab_mine};
    private int[] mDrawableId = {R.drawable.nav_jishi, R.drawable.nav_current, R.drawable.nav_mine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (AppApplication.isLogin()) {
            setCurrentTab(2);
        } else {
//            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(2));
//            if (currentFragment instanceof MineFragment) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.remove(currentFragment);
//            }
            selectTab(0);
        }
    }

    /**
     * 初始操作
     */
    private void init() {
        for (int i = 0; i < 3; i++) {
            mBottomView.addTab(mBottomView.newTab().setCustomView(getCustomTabView(i)));
        }
        mBottomView.addOnTabSelectedListener(this);
        setCurrentTab(0);
//        setTabTextIcon(0);
    }

    private View getCustomTabView(int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_item, null);
        mImageArray[position] = (ImageView) view.findViewById(R.id.tab_image);
        mTxtArray[position] = (TextView) view.findViewById(R.id.tab_text);

        mImageArray[position].setImageResource(mDrawableId[position]);
        mTxtArray[position].setText(mStringId[position]);

        return view;
    }

    /**
     * fragment的切换
     */
    private void setCurrentTab(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(position));
        if (oldFragment != null) {
            ft.hide(oldFragment);
        }
        if (position == 2 && !AppApplication.isLogin()) {
            currentFragment = null;
        }
        if (currentFragment == null) {
            switch (position) {
                case 0:
                    currentFragment = new HomeFragment();
                    break;
                case 1:
                    currentFragment = new LoansFragment();
                    break;
                case 2:
                    if (!AppApplication.isLogin()) {
                        LoginActivity.newIntent(this, LoginActivity.TAG_LOGIN);
                        return;
                    }
                    currentFragment = new MineFragment();
                    break;
                default:
                    break;
            }
            ft.add(R.id.content, currentFragment, String.valueOf(position));
        } else {
            ft.show(currentFragment);
        }
        oldFragment = currentFragment;
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LoginActivity.TAG_LOGIN) {
            if (resultCode == Activity.RESULT_CANCELED) {
                selectTab(0);
                setTabTextIcon(0);
            } else {
                setTabTextIcon(2);
                selectTab(2);
            }
            return;
        }
        if (oldFragment != null) {
            oldFragment.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        setTabTextIcon(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    private void setTabTextIcon(int position) {
        setCurrentTab(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }


    private void selectTab(int position) {
        try {
            Method selectTab = mBottomView.getClass().getDeclaredMethod("selectTab", TabLayout.Tab.class);
            selectTab.setAccessible(true);
            try {
                selectTab.invoke(mBottomView, mBottomView.getTabAt(position));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, getString(R.string.double_click_exit), Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

}
