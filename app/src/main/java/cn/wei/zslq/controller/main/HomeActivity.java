package cn.wei.zslq.controller.main;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.update.UmengUpdateAgent;

import cn.wei.zslq.R;
import cn.wei.zslq.fragment.IndexFragment;
import cn.wei.zslq.fragment.InformationListFragment;
import cn.wei.zslq.fragment.JokeFragment;
import cn.wei.zslq.core.BaseActivity;

/**
 * Created by qinwei on 2015/11/3 22:44
 * email:qinwei_it@163.com
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mDrawerMenuNavigationView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        UmengUpdateAgent.update(this);
        mDrawerMenuNavigationView = (NavigationView) findViewById(R.id.mDrawerMenuNavigationView);
        mDrawerMenuNavigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void initializeData() {
        setTitle("首页");
        getSupportFragmentManager().beginTransaction().replace(R.id.mContentContainer, JokeFragment.getInstance(false)).commit();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if(menuItem.getTitle().equals(getTitle())){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return false;
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_joke:
                setTitle(menuItem.getTitle());
                getSupportFragmentManager().beginTransaction().replace(R.id.mContentContainer, JokeFragment.getInstance(false)).commit();
                break;
            case R.id.nav_information:
                setTitle(menuItem.getTitle());
                getSupportFragmentManager().beginTransaction().replace(R.id.mContentContainer, new InformationListFragment()).commit();
                break;
            case R.id.nav_home:
                setTitle(menuItem.getTitle());
                getSupportFragmentManager().beginTransaction().replace(R.id.mContentContainer, new IndexFragment()).commit();
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        return false;
    }
}
