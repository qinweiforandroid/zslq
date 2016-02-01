package cn.wei.zslq.fragment;

import android.content.Intent;
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
import cn.wei.zslq.controller.im.TalkListActivity;
import cn.wei.zslq.controller.seller.SellerSimpleInfoListActivity;
import cn.wei.zslq.support.BaseFragment;
import cn.wei.zslq.utils.BmobManager;

/**
 * Created by qinwei on 2015/11/3 23:30
 * email:qinwei_it@163.com
 */
public class FindFragment extends BaseFragment implements View.OnClickListener, OnRowClickListener {
    private ContainerView mWidgetContainerView;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initializeView(View v) {
        mWidgetContainerView = (ContainerView) v.findViewById(R.id.mWidgetContainerView);
        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<GroupDescriptor>();
        ArrayList<BaseRowDescriptor> rowDescriptors1 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors1.add(new GeneralRowDescriptor(R.drawable.more_my_album, "朋友圈", "20", RowActionEnum.ACTION_FRIEND));
        GroupDescriptor groupDescriptor1 = new GroupDescriptor("", rowDescriptors1);

        ArrayList<BaseRowDescriptor> rowDescriptors2 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "刘圩商家", RowActionEnum.ACTION_SELLER));
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.more_my_album, "扫一扫", RowActionEnum.MY_FIRST));
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.more_my_favorite, "摇一摇", RowActionEnum.MY_FIRST));
        GroupDescriptor groupDescriptor2 = new GroupDescriptor("", rowDescriptors2);

        ArrayList<BaseRowDescriptor> rowDescriptors3 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors3.add(new GeneralRowDescriptor(R.drawable.more_emoji_store, "附近的人", RowActionEnum.MY_FIRST));
        rowDescriptors3.add(new GeneralRowDescriptor(R.drawable.more_my_bank_card, "漂流瓶", RowActionEnum.MY_FIRST));
        GroupDescriptor groupDescriptor3 = new GroupDescriptor("", rowDescriptors3);

        groupDescriptors.add(groupDescriptor1);
        groupDescriptors.add(groupDescriptor2);
        groupDescriptors.add(groupDescriptor3);
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
        switch (action){
            case ACTION_FRIEND:
                goFriends();
                break;
            case ACTION_SELLER:
                goSeller();
                break;
            default:
                break;
        }
    }

    private void goSeller() {
        Intent intent=new Intent(getActivity(), SellerSimpleInfoListActivity.class);
        startActivity(intent);
    }

    private void goFriends() {
        Intent intent=new Intent(getActivity(), TalkListActivity.class);
        startActivity(intent);
    }
}
