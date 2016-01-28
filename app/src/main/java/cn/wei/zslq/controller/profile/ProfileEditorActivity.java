package cn.wei.zslq.controller.profile;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.ContainerDescriptor;
import cn.wei.library.widget.row.ContainerView;
import cn.wei.library.widget.row.GroupDescriptor;
import cn.wei.library.widget.row.OnRowClickListener;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.library.widget.row.expand.IOSRowDescriptor;
import cn.wei.library.widget.row.expand.UserIconRowDescriptor;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.User;
import cn.wei.zslq.support.BaseActivity;

/**
 * Created by qinwei on 2016/1/21 17:43
 * email:qinwei_it@163.com
 */
public class ProfileEditorActivity extends BaseActivity implements OnRowClickListener {
    private ContainerView mWidgetContainerView;
    private User user;
    public static final int REQUEST_USER_NICK_EDITOR=1;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_profile_edtior);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        setTitle(getString(R.string.mTitleUserInfo));
        user= MyApplication.getLoginUser();
        mWidgetContainerView = (ContainerView) findViewById(R.id.mWidgetContainerView);
    }

    @Override
    protected void initializeData() {
        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<GroupDescriptor>();
        ArrayList<BaseRowDescriptor> rowDescriptors1 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors1.add(new UserIconRowDescriptor("头像", user.getIcon(), RowActionEnum.ACTION_USER_ICON));
        rowDescriptors1.add(new IOSRowDescriptor("昵称", user.getNick(), RowActionEnum.ACTION_USER_NICK));
        rowDescriptors1.add(new IOSRowDescriptor("签名", "这个家伙很懒什么也没留下!", RowActionEnum.ACTION_FRIEND));
        GroupDescriptor groupDescriptor1 = new GroupDescriptor("", rowDescriptors1);

        ArrayList<BaseRowDescriptor> rowDescriptors2 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors2.add(new IOSRowDescriptor("微信号", "qinwei_it@163.com", RowActionEnum.ACTION_FRIEND));
        rowDescriptors2.add(new IOSRowDescriptor("手机号", "未绑定", RowActionEnum.ACTION_FRIEND));
        rowDescriptors2.add(new IOSRowDescriptor("身份证", "342225********2011", null));
        GroupDescriptor groupDescriptor2 = new GroupDescriptor("个人信息", rowDescriptors2);

        groupDescriptors.add(groupDescriptor1);
        groupDescriptors.add(groupDescriptor2);
        ContainerDescriptor containerDescriptor = new ContainerDescriptor(groupDescriptors);
        mWidgetContainerView.initializeData(containerDescriptor, this);
        mWidgetContainerView.notifyDataChanged();
    }

    @Override
    public void onRowClick(View rowView, RowActionEnum action) {
        switch (action){
            case ACTION_USER_ICON:
                goUserIconEditor();
                break;
            case ACTION_USER_NICK:
                goUserNickEditor();
                break;
            default:
                break;
        }
    }

    private void goUserNickEditor() {
        Intent intent=new Intent(this,ProfileNickEditorActivity.class);
        intent.putExtra(ProfileNickEditorActivity.KEY_NICK,user.getNick());
        startActivityForResult(intent,REQUEST_USER_NICK_EDITOR);
    }

    private void goUserIconEditor() {
        Intent intent=new Intent(this,ProfileIconEditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            initializeData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
