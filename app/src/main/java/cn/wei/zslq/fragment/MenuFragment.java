package cn.wei.zslq.fragment;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.View;

import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseFragment;

/**
 * Created by qinwei on 2015/11/3 23:30
 * email:qinwei_it@163.com
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private NavigationView mDrawerMenuNavigationView;
    private NavigationView.OnNavigationItemSelectedListener listener;

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
        mDrawerMenuNavigationView.setNavigationItemSelectedListener(listener);
    }

    @Override
    public void onClick(View v) {
    }

    public void initializeView() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationView.OnNavigationItemSelectedListener) {
            listener = (NavigationView.OnNavigationItemSelectedListener) context;
        }
    }
}
