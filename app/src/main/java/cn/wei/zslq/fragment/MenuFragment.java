package cn.wei.zslq.fragment;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import cn.wei.library.widget.row.OnRowClickListener;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseFragment;

/**
 * Created by qinwei on 2015/11/3 23:30
 * email:qinwei_it@163.com
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener, OnRowClickListener {

    private NavigationView mDrawerMenuNavigationView;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_drawer_menu;
    }

    @Override
    protected void initializeView(View v) {
        v.findViewById(R.id.mDrawerMenuRootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDrawerMenuNavigationView = (NavigationView) v.findViewById(R.id.mDrawerMenuNavigationView);
        mDrawerMenuNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        menuItem.setChecked(true);
//                        ((HomeActivity) getActivity()).closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onClick(View v) {
    }

    public void initializeView() {

    }

    @Override
    public void onRowClick(View rowView, RowActionEnum action) {

    }
}
