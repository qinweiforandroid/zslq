package cn.wei.zslq.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.wei.library.adapter.QBaseRecyclerViewHolder;
import cn.wei.library.utils.Trace;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.ThunderAccount;
import cn.wei.zslq.core.BaseRecyclerRefreshViewFragment;

/**
 * Created by qinwei on 2016/1/6 11:12
 * email:qinwei_it@163.com
 */
public class ThunderAccountListFragment extends BaseRecyclerRefreshViewFragment {

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_thunder_account;
    }

    @Override
    protected void initializeView(View v) {
        super.initializeView(v);
        GridLayoutManager lm = new GridLayoutManager(getActivity(), 2);
        setLayoutManager(lm);
        loadDataFromServer(false);
    }

    private void loadDataFromServer(final boolean refresh) {
        BmobQuery<ThunderAccount> bmobQuery = new BmobQuery<ThunderAccount>();
        bmobQuery.findObjects(getActivity(), new FindListener<ThunderAccount>() {
            @Override
            public void onSuccess(List<ThunderAccount> list) {
                modules.clear();
                modules.addAll(list);
                adapter.notifyDataSetChanged();
                if (refresh) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(true);
                    Toast.makeText(getActivity(), "更新成功!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Trace.e("查询失败:onError code:" + i + ",mesasge:" + s);
                if (refresh) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mSwipeRefreshLayout.setEnabled(false);
        loadDataFromServer(true);
    }

    @Override
    public QBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        QBaseRecyclerViewHolder holder = new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.list_thunder_account_item, null));
        return holder;
    }

    class ViewHolder extends QBaseRecyclerViewHolder implements View.OnClickListener {
        private TextView mAccountPasswordLabel;
        private TextView mAccountNameLabel;
        private ThunderAccount account;

        public ViewHolder(View view) {
            super(view);
            mAccountNameLabel = (TextView) view.findViewById(R.id.mAccountNameLabel);
            mAccountPasswordLabel = (TextView) view.findViewById(R.id.mAccountPasswordLabel);
            view.findViewById(R.id.mAccountCopyNameBtn).setOnClickListener(this);
            view.findViewById(R.id.mAccountCopyPwdBtn).setOnClickListener(this);
        }

        @Override
        public void initializeData(int position) {
            account = (ThunderAccount) modules.get(position);
            mAccountNameLabel.setText(account.getAccount());
            mAccountPasswordLabel.setText(account.getPassword());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mAccountCopyNameBtn:
                    copyToClipboard(account.getAccount());
                    break;
                case R.id.mAccountCopyPwdBtn:
                    copyToClipboard(account.getPassword());
                    break;
                default:
                    break;
            }
        }
    }

    public void copyToClipboard(String text) {
        ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
    }

}