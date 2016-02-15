package cn.wei.zslq.fragment;

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
