package cn.wei.zslq.controller.main;

import cn.wei.zslq.R;
import cn.wei.zslq.fragment.JokeFragment;
import cn.wei.zslq.core.BaseActivity;

/**
 * Created by qinwei on 2016/4/8 14:52
 * email:qinwei_it@163.com
 */
public class JokeActivity extends BaseActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_joke);
    }

    @Override
    protected void initializeData() {
        setTitle("开心一笑");
        getSupportFragmentManager().beginTransaction().replace(R.id.mJokeContentLayout, JokeFragment.getInstance(false)).commit();
    }
}
