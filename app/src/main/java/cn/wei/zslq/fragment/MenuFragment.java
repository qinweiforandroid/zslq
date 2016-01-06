package cn.wei.zslq.fragment;

import android.view.View;

import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseFragment;
import cn.wei.zslq.utils.BmobManager;

/**
 * Created by qinwei on 2015/11/3 23:30
 * email:qinwei_it@163.com
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initializeView(View v) {
        v.findViewById(R.id.mAccountAddBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        ThunderAccount account = new ThunderAccount();
//        account.setAccount("maldini2009:1");
//        account.setPassword("7935953");
//        account.setAccountType(ThunderAccount.account_type_xunlei);
//        account.save(getActivity(), new SaveListener() {
//            @Override
//            public void onSuccess() {
//                Trace.d("ThunderAccount:onSuccess");
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Trace.d("ThunderAccount:onFailure，" + s);
//            }
//        });
        BmobManager.uploadXunLeiAccount(getActivity());
    }
}
