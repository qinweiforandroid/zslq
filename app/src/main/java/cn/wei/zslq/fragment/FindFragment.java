package cn.wei.zslq.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import cn.wei.library.widget.EmptyView;
import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.ContainerDescriptor;
import cn.wei.library.widget.row.ContainerView;
import cn.wei.library.widget.row.GeneralRowDescriptor;
import cn.wei.library.widget.row.GroupDescriptor;
import cn.wei.library.widget.row.OnRowClickListener;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.main.PictureListActivity;
import cn.wei.zslq.controller.zone.TalkListActivity;
import cn.wei.zslq.controller.seller.SellerSimpleInfoListActivity;
import cn.wei.zslq.core.BaseFragment;
import cn.wei.zslq.utils.BmobManager;

/**
 * Created by qinwei on 2015/11/3 23:30
 * email:qinwei_it@163.com
 */
public class FindFragment extends BaseFragment implements View.OnClickListener, OnRowClickListener {
    private ContainerView mWidgetContainerView;
    public boolean isBindViewPager = false;

    public static FindFragment getInstance(boolean isBindViewPager) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putBoolean("isBindViewPager", isBindViewPager);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_tab_find;
    }

    @Override
    protected void initializeArguments(Bundle args) {
        super.initializeArguments(args);
        isBindViewPager = args.getBoolean("isBindViewPager");
    }

    @Override
    protected void initializeView(View v) {
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        mWidgetContainerView = (ContainerView) v.findViewById(R.id.mWidgetContainerView);
        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<GroupDescriptor>();
        ArrayList<BaseRowDescriptor> rowDescriptors1 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors1.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "朋友圈", "20", RowActionEnum.ACTION_FRIEND));
        GroupDescriptor groupDescriptor1 = new GroupDescriptor("", rowDescriptors1);

        ArrayList<BaseRowDescriptor> rowDescriptors2 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "商  家", RowActionEnum.ACTION_SELLER));
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "妹纸图", RowActionEnum.ACTION_PICTURE));
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "入口二", RowActionEnum.MY_FIRST));
        GroupDescriptor groupDescriptor2 = new GroupDescriptor("", rowDescriptors2);

        ArrayList<BaseRowDescriptor> rowDescriptors3 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors3.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "功能一", RowActionEnum.MY_FIRST));
        rowDescriptors3.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "功能二", RowActionEnum.MY_FIRST));
        GroupDescriptor groupDescriptor3 = new GroupDescriptor("", rowDescriptors3);

        groupDescriptors.add(groupDescriptor1);
        groupDescriptors.add(groupDescriptor2);
        groupDescriptors.add(groupDescriptor3);
        ContainerDescriptor containerDescriptor = new ContainerDescriptor(groupDescriptors);
        mWidgetContainerView.initializeData(containerDescriptor, this);
        if (!isBindViewPager) {
            lazyLoad();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mWidgetContainerView.notifyDataChanged();
        showContent();
    }

    @Override
    public void onClick(View v) {
        BmobManager.uploadXunLeiAccount(getActivity());
    }

    @Override
    public void onRowClick(View rowView, RowActionEnum action) {
        switch (action) {
            case ACTION_FRIEND:
                goFriends();
                break;
            case ACTION_SELLER:
                goSeller();
                break;
            case ACTION_PICTURE:
                goPictureList();
                break;
            default:
                break;
        }
    }

    private void goPictureList() {
        Intent intent = new Intent(getActivity(), PictureListActivity.class);
        startActivity(intent);
    }

    private void goSeller() {
        Intent intent = new Intent(getActivity(), SellerSimpleInfoListActivity.class);
        startActivity(intent);
    }

    private void goFriends() {
        Intent intent = new Intent(getActivity(), TalkListActivity.class);
        startActivity(intent);
    }
}
