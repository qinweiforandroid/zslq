package cn.wei.zslq.controller.profile;

import android.view.View;

import java.util.ArrayList;

import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.GroupDescriptor;
import cn.wei.library.widget.row.GroupView;
import cn.wei.library.widget.row.OnRowClickListener;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.library.widget.row.expand.IOSRowDescriptor;
import cn.wei.zslq.R;
import cn.wei.zslq.core.BaseActivity;

/**
 * Created by qinwei on 2016/1/22 14:23
 * email:qinwei_it@163.com
 */
public class ProfileIconEditorActivity extends BaseActivity implements OnRowClickListener {
    private GroupView mWidgetGroupView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_profile_user_icon_edtior);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mWidgetGroupView=(GroupView) findViewById(R.id.mWidgetGroupView);
        ArrayList<BaseRowDescriptor> rowDatas=new ArrayList<>();
        rowDatas.add(new IOSRowDescriptor(R.drawable.ic_launcher,"从相册选一张",RowActionEnum.MY_FIRST));
        rowDatas.add(new IOSRowDescriptor(R.drawable.ic_launcher,"拍一张照片",RowActionEnum.MY_FIRST));
        GroupDescriptor groupDescriptor=new GroupDescriptor("",rowDatas);
        mWidgetGroupView.initializeData(groupDescriptor,this);
        mWidgetGroupView.notifyDataChanged();
    }

    @Override
    protected void initializeData() {
        setTitle("设置头像");
    }

    @Override
    public void onRowClick(View rowView, RowActionEnum action) {

    }
}
