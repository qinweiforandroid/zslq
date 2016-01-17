package cn.wei.zslq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.zslq.support.BaseListFragment;
import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.widget.EmptyView;
import cn.wei.library.widget.FooterView;
import cn.wei.zslq.R;
import cn.wei.zslq.activity.WebViewActivity;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.entity.InformationBean;
import cn.wei.zslq.model.impl.InformationModel;
import cn.wei.zslq.utils.ActionClickUtils;
import cn.wei.zslq.utils.ImageUtils;

/**
 * Created by qinwei on 2015/10/29 17:41
 * email:qinwei_it@163.com
 */
public class InformationListFragment extends BaseListFragment implements Controller {
    private String countTotal;
    private InformationModel viewModel;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_information_list;
    }

    @Override
    protected void initializeArguments(Bundle args) {
        super.initializeArguments(args);
        viewModel = new InformationModel(getContext());
        viewModel.setController(this);
    }

    @Override
    public void initializeView(View view) {
        super.initializeView(view);
        loadDataFromServer();
    }

    @Override
    public void onRetry() {
        super.onRetry();
        loadDataFromServer();
    }

    public void loadDataFromServer() {
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        viewModel.loadInformationFirst();
    }

    @Override
    public void onRefresh(boolean isRefresh) {
        super.onRefresh(isRefresh);
        if (viewModel.extraInfo != null) {
            viewModel.loadInformationRefresh(viewModel.extraInfo.getCountTotal());
        }
    }


    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_information_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }

    @Override
    public void onSuccess(String tag) {
        switch (tag) {
            case InformationModel.ACTION_FIRST_LOAD:
                modules.addAll(viewModel.informationBeans);
                adapter.notifyDataSetChanged();
                showContent();
                break;
            case InformationModel.ACTION_MORE_LOAD:
                modules.addAll(viewModel.informationBeans);
                adapter.notifyDataSetChanged();
                footerView.notifyDataChanged(FooterView.State.done);
                break;
            case InformationModel.ACTION_REFRESH_LOAD:
                modules.clear();
                modules.addAll(viewModel.informationBeans);
                adapter.notifyDataSetChanged();
                mPullToRefreshLsv.onRefreshComplete();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(String tag, int errorCode, String errorMsg) {
        switch (tag) {
            case InformationModel.ACTION_FIRST_LOAD:
                mEmptyView.notifyDataChanged(EmptyView.State.error);
                break;
            case InformationModel.ACTION_MORE_LOAD:
                footerView.notifyDataChanged(FooterView.State.error);
                break;
            case InformationModel.ACTION_REFRESH_LOAD:
                mPullToRefreshLsv.onRefreshComplete();
                break;
            default:
                break;
        }
    }


    public class ViewHolder extends QBaseViewHolder {

        private ImageView mNewsItemIconImg;
        private TextView mNewsItemTitleLabel;
        private TextView mNewsItemCreateDateLabel;
        private TextView mNewsItemAuthorLabel;

        @Override
        public void initializeView(View view) {
            mNewsItemIconImg = (ImageView) view.findViewById(R.id.mNewsItemIconImg);
            mNewsItemTitleLabel = (TextView) view.findViewById(R.id.mNewsItemTitleLabel);
            mNewsItemCreateDateLabel = (TextView) view.findViewById(R.id.mNewsItemCreateDateLabel);
            mNewsItemAuthorLabel = (TextView) view.findViewById(R.id.mNewsItemAuthorLabel);
        }

        @Override
        public void initializeData(int position) {
            InformationBean information = (InformationBean) modules.get(position);
            ImageUtils.displayImage(information.getImages(), mNewsItemIconImg, ImageUtils.getUserIconOptions());
            mNewsItemTitleLabel.setText(information.getTitle());
            mNewsItemAuthorLabel.setText("作者:" + information.getAuthor());
            mNewsItemCreateDateLabel.setText(information.getCreateTimeValue());
        }
    }


    @Override
    public void loadMore() {
        InformationBean informationBean = (InformationBean) modules.get(modules.size()-1);
        footerView.notifyDataChanged(FooterView.State.ing);
        viewModel.loadInformationMore(informationBean.getId(), viewModel.extraInfo.getCountTotal());

    }

    @Override
    public void onRetryLoadMore() {
        InformationBean informationBean = (InformationBean) modules.get(modules.size()-1);
        footerView.notifyDataChanged(FooterView.State.ing);
        viewModel.loadInformationMore(informationBean.getId(), viewModel.extraInfo.getCountTotal());
    }

    @Override
    public boolean isCanLoadMore() {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InformationBean information = (InformationBean) parent.getAdapter().getItem(position);
        ActionClickUtils.onEvent(getActivity(),ActionClickUtils.ACTION_LOOK_INFORMATION);
        startActivity(WebViewActivity.getIntent(getActivity(), information.getId()));
    }
}
