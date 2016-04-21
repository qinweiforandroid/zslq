package cn.wei.zslq.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.TimeHelper;
import cn.wei.library.widget.EmptyView;
import cn.wei.library.widget.FooterView;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.domain.JokeBean;
import cn.wei.zslq.model.impl.BaiduDataSupportApiModel;
import cn.wei.zslq.core.BaseListFragment;
import cn.wei.zslq.utils.Constants;

/**
 * Created by qinwei on 2016/3/5 21:26
 * email:qinwei_it@163.com
 */
public class JokeFragment extends BaseListFragment implements Controller {
    public boolean isBindViewPager = true;
    private BaiduDataSupportApiModel viewModel;
    public int load_data_state = 1;
    private int pageNum = 1;

    public static JokeFragment getInstance(boolean isBindViewPager) {
        JokeFragment fragment = new JokeFragment();
        Bundle args = new Bundle();
        args.putBoolean("isBindViewPager", isBindViewPager);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_joke_list;
    }

    @Override
    protected void initializeArguments(Bundle args) {
        super.initializeArguments(args);
        isBindViewPager = args.getBoolean("isBindViewPager");
    }

    @Override
    protected void initializeView(View v) {
        super.initializeView(v);
        viewModel = new BaiduDataSupportApiModel(getActivity());
        viewModel.setController(this);
        if (!isBindViewPager) {
            lazyLoad();
        }
    }


    @Override
    protected void lazyLoad() {
        load_data_state = Constants.LOAD_DATA_STATE_LOAD_FIRST;
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        viewModel.loadJokeData(pageNum);
    }

    @Override
    public void onRefresh(boolean isRefresh) {
        if (isRefresh) {
            pageNum = 1;
            load_data_state = Constants.LOAD_DATA_STATE_LOAD_REFRESH;
            viewModel.loadJokeData(pageNum);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JokeBean bean = (JokeBean) parent.getAdapter().getItem(position);
        copyToClipboard(bean.getText());
    }

    public void copyToClipboard(String text) {
        ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore() {
        footerView.notifyDataChanged(FooterView.State.ing);
        load_data_state = Constants.LOAD_DATA_STATE_LOAD_MORE;
        viewModel.loadJokeData(pageNum);
    }

    @Override
    public void onRetry() {
        load_data_state = Constants.LOAD_DATA_STATE_LOAD_FIRST;
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        viewModel.loadJokeData(pageNum);
    }

    @Override
    public boolean isCanLoadMore() {
        return true;
    }

    @Override
    public void onSuccess(String action) {
        switch (load_data_state) {
            case Constants.LOAD_DATA_STATE_LOAD_FIRST:
                if (viewModel.jokes.size() == 0) {
                    mEmptyView.notifyDataChanged(EmptyView.State.empty);
                } else {
                    pageNum++;
                    modules.addAll(viewModel.jokes);
                    adapter.notifyDataSetChanged();
                    mEmptyView.notifyDataChanged(EmptyView.State.done);
                    showContent();
                }
                break;
            case Constants.LOAD_DATA_STATE_LOAD_REFRESH:
                if (viewModel.jokes.size() == 0) {
                    mEmptyView.notifyDataChanged(EmptyView.State.empty);
                } else {
                    pageNum++;
                    modules.clear();
                    modules.addAll(viewModel.jokes);
                    adapter.notifyDataSetChanged();
                }
                mPullToRefreshLsv.onRefreshComplete();
                break;
            case Constants.LOAD_DATA_STATE_LOAD_MORE:
                if (viewModel.jokes.size() == 0) {
                    footerView.notifyDataChanged(FooterView.State.no_data);
                } else {
                    pageNum++;
                    modules.addAll(viewModel.jokes);
                    adapter.notifyDataSetChanged();
                    footerView.notifyDataChanged(FooterView.State.done);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(String action, int errorCode, String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        switch (load_data_state) {
            case Constants.LOAD_DATA_STATE_LOAD_FIRST:
                mEmptyView.notifyDataChanged(EmptyView.State.error);
                break;
            case Constants.LOAD_DATA_STATE_LOAD_REFRESH:
                mPullToRefreshLsv.onRefreshComplete();
                break;
            case Constants.LOAD_DATA_STATE_LOAD_MORE:
                footerView.notifyDataChanged(FooterView.State.error);
                break;
            default:
                break;
        }
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_joke_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }

    public class ViewHolder extends QBaseViewHolder {

        private TextView mJokeItemTitleLabel;
        private TextView mJokeItemCreateDateLabel;
        private TextView mJokeItemContentLabel;

        @Override
        public void initializeView(View view) {
            mJokeItemTitleLabel = (TextView) view.findViewById(R.id.mJokeItemTitleLabel);
            mJokeItemCreateDateLabel = (TextView) view.findViewById(R.id.mJokeItemCreateDateLabel);
            mJokeItemContentLabel = (TextView) view.findViewById(R.id.mJokeItemContentLabel);
        }

        @Override
        public void initializeData(int position) {
            JokeBean joke = (JokeBean) modules.get(position);
            mJokeItemContentLabel.setText(Html.fromHtml(joke.getText()));
            mJokeItemTitleLabel.setText(joke.getTitle());
            mJokeItemCreateDateLabel.setText(TimeHelper.getTimeRule2(joke.getCt()));
        }
    }
}
