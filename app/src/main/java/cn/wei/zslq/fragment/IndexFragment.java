package cn.wei.zslq.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseFragment;


/**
 * Created by qinwei on 2015/10/30 13:35
 * email:qinwei_it@163.com
 */
public class IndexFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private DataAdapter adapter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_drawer_content;
    }

    @Override
    protected void initializeView(View v) {
        mViewPager = (ViewPager) v.findViewById(R.id.mViewPager);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout = (TabLayout) v.findViewById(R.id.mTabLayout);
        adapter = new DataAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    class DataAdapter extends FragmentStatePagerAdapter {
        String[] titles = {"资讯", "会话", "发现", "个人"};

        public DataAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new InformationListFragment();
                case 1:
                    return new SortFragment();
                case 2:
                    return new FindFragment();
                case 3:
                    return new ProfileFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
