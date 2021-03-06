package cn.wei.zslq.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.ContainerDescriptor;
import cn.wei.library.widget.row.ContainerView;
import cn.wei.library.widget.row.GeneralRowDescriptor;
import cn.wei.library.widget.row.GroupDescriptor;
import cn.wei.library.widget.row.OnRowClickListener;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.library.widget.row.SimpleInfoRowDescriptor;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.activity.MessageActivity;
import cn.wei.zslq.controller.main.JokeActivity;
import cn.wei.zslq.controller.main.LoginActivity;
import cn.wei.zslq.controller.profile.ProfileEditorActivity;
import cn.wei.zslq.domain.User;
import cn.wei.zslq.core.BaseFragment;

/**
 * Created by qinwei on 2015/11/3 23:30
 * email:qinwei_it@163.com
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener, OnRowClickListener {
    private ContainerView mWidgetContainerView;
    public boolean isBindViewPager = false;

    public static ProfileFragment getInstance(boolean isBindViewPager) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putBoolean("isBindViewPager", isBindViewPager);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_tab_profile;
    }

    @Override
    protected void initializeArguments(Bundle args) {
        super.initializeArguments(args);
        isBindViewPager = args.getBoolean("isBindViewPager");
    }

    @Override
    protected void initializeView(View v) {
        mWidgetContainerView = (ContainerView) v.findViewById(R.id.mWidgetContainerView);
        if (!isBindViewPager) {
            lazyLoad();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        bindData();
        showContent();
    }

    long lastStamp = 0;

    private void bindData() {
        if (System.currentTimeMillis() - lastStamp < 800) {
            this.lastStamp = System.currentTimeMillis();
            return;
        }
        ArrayList<BaseRowDescriptor> rowDescriptor0s = new ArrayList<BaseRowDescriptor>();
        User user = MyApplication.getLoginUser();
        if (user != null) {
            rowDescriptor0s.add(new SimpleInfoRowDescriptor(user.getIcon(), user.getUsername(), user.getNick(), RowActionEnum.ACTION_PRIFLE_INFO));
        } else {
            rowDescriptor0s.add(new SimpleInfoRowDescriptor(R.drawable.ic_launcher, "", "", RowActionEnum.ACTION_GO_LOGIN));
        }
        GroupDescriptor groupDescriptor0 = new GroupDescriptor("", rowDescriptor0s);

        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<GroupDescriptor>();
        ArrayList<BaseRowDescriptor> rowDescriptors1 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors1.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "开心笑", "20", RowActionEnum.ACTION_JOKE));
        rowDescriptors1.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "说一说", "20", RowActionEnum.MY_FIRST));
        GroupDescriptor groupDescriptor1 = new GroupDescriptor("", rowDescriptors1);

        ArrayList<BaseRowDescriptor> rowDescriptors2 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "动漫讯", RowActionEnum.MY_FIRST));
        rowDescriptors2.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "优惠券", RowActionEnum.ACTION_ABOUT_ME));
        GroupDescriptor groupDescriptor2 = new GroupDescriptor("", rowDescriptors2);

        ArrayList<BaseRowDescriptor> rowDescriptors3 = new ArrayList<BaseRowDescriptor>();
        rowDescriptors3.add(new GeneralRowDescriptor(R.drawable.ic_launcher, "退  出", RowActionEnum.ACTION_LOGIN_OUT));
        GroupDescriptor groupDescriptor3 = new GroupDescriptor("", rowDescriptors3);

        groupDescriptors.add(groupDescriptor0);
        groupDescriptors.add(groupDescriptor1);
        groupDescriptors.add(groupDescriptor2);
        groupDescriptors.add(groupDescriptor3);
        ContainerDescriptor containerDescriptor = new ContainerDescriptor(groupDescriptors);
        mWidgetContainerView.initializeData(containerDescriptor, this);
        mWidgetContainerView.notifyDataChanged();
    }

    @Override
    public void onClick(View v) {
    }

    private void goProfileEditor() {
        Intent intent = new Intent(getActivity(), ProfileEditorActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRowClick(View rowView, RowActionEnum action) {
        switch (action) {
            case ACTION_PRIFLE_INFO:
                goProfileEditor();
//                Intent intent1=new Intent(getActivity(), AActivity.class);
//                startActivity(intent1);
                break;
            case ACTION_JOKE:
                startActivity(new Intent(getActivity(), JokeActivity.class));
                break;
            case ACTION_GO_LOGIN:
                goLogin();
                break;
            case ACTION_LOGIN_OUT:
                doLoginOut();
                break;
            case ACTION_ABOUT_ME:
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void doLoginOut() {
//        MyApplication.getLoginUser().logOut(getActivity());
        new AlertDialog.Builder(getActivity())
                .setMessage("是否退出登录？")
                .setTitle(android.R.string.dialog_alert_title)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApplication.loginOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .setPositiveButton(android.R.string.cancel, null)
                .create().show();
    }

    private void goLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData();
    }
}
