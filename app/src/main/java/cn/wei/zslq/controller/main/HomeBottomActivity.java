package cn.wei.zslq.controller.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import cn.wei.library.widget.Tab;
import cn.wei.library.widget.TabIndicator;
import cn.wei.zslq.R;
import cn.wei.zslq.fragment.FindFragment;
import cn.wei.zslq.fragment.InformationListFragment;
import cn.wei.zslq.fragment.InformationServiceFragment;
import cn.wei.zslq.fragment.ProfileFragment;
import cn.wei.zslq.core.BaseActivity;
import cn.wei.zslq.utils.Constants;

/**
 * Created by qinwei on 2016/3/18 12:38
 * email:qinwei_it@163.com
 */
public class HomeBottomActivity extends BaseActivity implements TabIndicator.OnTabClickListener {
    private TabIndicator mHomeIndicator;
    private ArrayList<Tab> tabs;
    private int currentIndex;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_home_tab);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mHomeIndicator = (TabIndicator) findViewById(R.id.mHomeIndicator);
        mHomeIndicator.setOnTabClickListener(this);
    }

    @Override
    protected void initializeData() {
        setTitle(getIntent().getStringExtra(Constants.KEY_TITLE));
        tabs = new ArrayList<Tab>();
        tabs.add(new Tab("首页", R.drawable.tab_inquiry_btn, InformationServiceFragment.class));
        tabs.add(new Tab("资讯", R.drawable.tab_casehistory_btn, InformationListFragment.class));
        tabs.add(new Tab("发现", R.drawable.tab_community_btn, FindFragment.class));
        tabs.add(new Tab("个人", R.drawable.tab_mine_btn, ProfileFragment.class));
        mHomeIndicator.initializeData(tabs);
        switchTab(0);
    }

    public void switchTab(int index) {
        mHomeIndicator.setCurrentTab(index);
    }

    @Override
    public boolean onTabClick(int index) {
        try {
//            switch (index) {
//                case 1:
//                    Toast.makeText(getApplicationContext(), "登录", Toast.LENGTH_SHORT).show();
//                    return false;
//            }
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("" + index);
            Fragment oldFragment = getSupportFragmentManager().findFragmentByTag("" + currentIndex);
            if (fragment == null) {
                if (oldFragment != null)
                    getSupportFragmentManager().beginTransaction().hide(oldFragment).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().add(R.id.mHomeFrame, tabs.get(index).getFragmentClass().newInstance(), "" + index).commitAllowingStateLoss();
            } else {
                getSupportFragmentManager().beginTransaction().hide(oldFragment).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
            }
            mHomeIndicator.onDataChanged(index, 0);
            currentIndex = index;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setTitle(tabs.get(index).getLabel());
        return true;
    }

    @Override
    protected void recoveryState(Bundle saveInstance) {
        tabs = (ArrayList<Tab>) saveInstance.getSerializable(Constants.KEY_TAB_ENTITIES);
        mHomeIndicator.initializeData(tabs);
        currentIndex = saveInstance.getInt(Constants.KEY_CURRENT_TAB_INDEX);
        for (int i = 0; i < tabs.size(); i++) {
            if (getSupportFragmentManager().findFragmentByTag("" + i) != null)
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("" + i)).commitAllowingStateLoss();
        }
        mHomeIndicator.setCurrentTab(currentIndex);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.KEY_CURRENT_TAB_INDEX, currentIndex);
        outState.putSerializable(Constants.KEY_TAB_ENTITIES, tabs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isCanBack() {
        return false;
    }

    @Override
    public void protectApp() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
