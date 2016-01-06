package cn.wei.zslq.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.update.UmengUpdateAgent;

import cn.wei.zslq.R;
import cn.wei.zslq.fragment.IndexFragment;
import cn.wei.zslq.fragment.MenuFragment;
import cn.wei.zslq.support.BaseActivity;

/**
 * Created by qinwei on 2015/11/3 22:44
 * email:qinwei_it@163.com
 */
public class HomeActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        UmengUpdateAgent.update(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void initializeData() {
        setTitle("首页");
        getSupportFragmentManager().beginTransaction().replace(R.id.mContentContainer, new IndexFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_menu, new MenuFragment(), "menu").commit();
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void protectApp() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
