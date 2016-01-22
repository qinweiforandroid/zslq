package cn.wei.zslq.fragment;

import android.view.View;

import java.util.ArrayList;

import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.ContainerDescriptor;
import cn.wei.library.widget.row.ContainerView;
import cn.wei.library.widget.row.GeneralRowDescriptor;
import cn.wei.library.widget.row.GroupDescriptor;
import cn.wei.library.widget.row.OnRowClickListener;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseFragment;
import cn.wei.zslq.utils.BmobManager;

/**
 * Created by qinwei on 2015/11/3 23:30
 * email:qinwei_it@163.com
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener, OnRowClickListener {
    private ContainerView mWidgetContainerView;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initializeView(View v) {
//        RowViewFactory.LINE_IS_MATCH_PARENT = false;
        mWidgetContainerView = (ContainerView) v.findViewById(R.id.mWidgetContainerView);
        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<GroupDescriptor>();
        ArrayList<BaseRowDescriptor> rowDescriptors1 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors1.add(new GeneralRowDescriptor(R.drawable.home_tab_ic_profile_remaining, "共享 ", "80", RowActionEnum.MY_FIRST));
        rowDescriptors1.add(new GeneralRowDescriptor(R.drawable.home_tab_ic_profile_zone, "动态", "20", RowActionEnum.MY_FIRST));


        GroupDescriptor groupDescriptor1 = new GroupDescriptor("", rowDescriptors1);

        ArrayList<BaseRowDescriptor> rowDescriptors2 = new ArrayList<BaseRowDescriptor>();

        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "反馈", RowActionEnum.MY_FIRST));
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.home_tab_ic_profile_setting, "设置", RowActionEnum.MY_FIRST));
        GroupDescriptor groupDescriptor2 = new GroupDescriptor("", rowDescriptors2);


        groupDescriptors.add(groupDescriptor1);
        groupDescriptors.add(groupDescriptor2);
        ContainerDescriptor containerDescriptor = new ContainerDescriptor(groupDescriptors);
        mWidgetContainerView.initializeData(containerDescriptor, this);
        mWidgetContainerView.notifyDataChanged();
    }

    @Override
    public void onClick(View v) {
        BmobManager.uploadXunLeiAccount(getActivity());
    }

    public void initializeView() {

    }

    @Override
    public void onRowClick(View rowView, RowActionEnum action) {

    }
}
