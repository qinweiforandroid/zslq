package cn.wei.zslq.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.umeng.analytics.MobclickAgent;

import cn.wei.library.utils.Trace;
import cn.wei.library.widget.EmptyView;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.main.HomeActivity;
import cn.wei.zslq.utils.Constants;
import http.RequestManager;

/**
 * 结构化activity的代码
 * 方法调用顺序为setContentView()->initializeView()->recoveryState(saveInstance)-> initializeData();
 */
public abstract class BaseActivity extends AppCompatActivity implements EmptyView.OnRetryListener {
    protected String TAG = this.getClass().getSimpleName();
    protected ViewSwitcher mViewSwitcher;
    protected EmptyView mEmptyView;
    protected TextView mToolBarTitleLabel;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        Trace.v(TAG + ":onCreate app state:" + MyApplication.getInstance().getAppState());
        if (!MyApplication.getInstance().isAppKilled()) {
            setContentView();
            initializeView();
            if (saveInstance != null) {
                recoveryState(saveInstance);
            } else {
                initializeData();
            }
        } else {
            protectApp();
        }
    }

    @Override
    protected void onStart() {
        Trace.v(TAG + ":onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Trace.v(TAG + ":onResume");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Trace.v(TAG + ":onPause");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Trace.v(TAG + ":onStop");
    }

    @Override
    protected void onDestroy() {
        Trace.v(TAG + ":onDestroy");
        RequestManager.getInstance().cancelRequests();
        super.onDestroy();
    }

    public void protectApp() {
        Log.e(TAG, "protectApp:class=" + TAG);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Constants.KEY_PROTECT_APP, true);
        startActivity(intent);
        finish();
    }

    /**
     * 1. 设置布局
     */
    protected abstract void setContentView();

    /**
     * 2. 初始化布局
     */
    protected void initializeView() {
        if (findViewById(R.id.toolbar) != null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (isCanBack()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if (isCenter()) {
                mToolBarTitleLabel = (TextView) findViewById(R.id.mToolBarTitleLabel);
            }
        }

        if (findViewById(cn.wei.library.R.id.mEmptyView) != null) {
            mEmptyView = (EmptyView) findViewById(cn.wei.library.R.id.mEmptyView);
            mEmptyView.setOnRetryListener(this);
            mEmptyView.notifyDataChanged(EmptyView.State.ing);
            if (findViewById(cn.wei.library.R.id.mViewSwitcher) != null) {
                mViewSwitcher = (ViewSwitcher) findViewById(cn.wei.library.R.id.mViewSwitcher);
                mViewSwitcher.setDisplayedChild(0);
            }
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.KEY_TITLE)))
            setTitle(getIntent().getStringExtra(Constants.KEY_TITLE));
    }

    public void load() {
        mViewSwitcher.setDisplayedChild(0);
    }

    public void showContent() {
        mEmptyView.notifyDataChanged(EmptyView.State.done);
        mViewSwitcher.setDisplayedChild(1);
    }

    public void showEmpty() {
        mEmptyView.notifyDataChanged(EmptyView.State.empty);
        mViewSwitcher.setDisplayedChild(1);
    }

    /**
     * 界面被系统强杀 数据状态恢复
     *
     * @param saveInstance 状态数据
     */
    protected void recoveryState(Bundle saveInstance) {
    }

    /**
     * 3. 初始化ui数据
     */
    protected abstract void initializeData();

    @Override
    public void setTitle(CharSequence title) {
        if (mToolBarTitleLabel != null && isCenter()) {
            mToolBarTitleLabel.setText(title);
            super.setTitle("");
        } else {
            super.setTitle(title);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && isCanFinish()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 是否有回退功能
     *
     * @return
     */
    protected boolean isCanBack() {
        return true;
    }

    /**
     * 是否可以关闭当前界面
     *
     * @return
     */
    protected boolean isCanFinish() {
        return true;
    }

    /**
     * 标题是否居中
     *
     * @return
     */
    protected boolean isCenter() {
        return false;
    }

    @Override
    public void onRetry() {

    }
}

