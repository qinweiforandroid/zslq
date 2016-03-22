package cn.wei.zslq.support;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;

import cn.wei.library.R;
import cn.wei.library.widget.FooterView;

public abstract class BaseListFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener<ListView>, PullToRefreshBase.OnLastItemVisibleListener, FooterView.OnFooterViewListener {
    protected ArrayList<Object> modules = new ArrayList<Object>();
    protected PullToRefreshListView mPullToRefreshLsv;
    protected ListAdapter adapter;
    protected FooterView footerView;

    @Override
    protected void initializeView(View v) {
        mPullToRefreshLsv = (PullToRefreshListView) v.findViewById(R.id.generalPullToRefreshLsv);
        mPullToRefreshLsv.setPullToRefreshOverScrollEnabled(getPullToRefreshOverScrollEnabled());
        addRefreshHeaderView(mPullToRefreshLsv.getRefreshableView());
        addRefreshFooterView(mPullToRefreshLsv.getRefreshableView());
        mPullToRefreshLsv.setOnItemClickListener(this);
        mPullToRefreshLsv.setOnRefreshListener(this);
        adapter = new ListAdapter();
        mPullToRefreshLsv.setAdapter(adapter);
        //TODO 添加footerView
        if (isCanLoadMore()) {
            mPullToRefreshLsv.setOnLastItemVisibleListener(this);
            footerView = new FooterView(getActivity());
            footerView.setOnFooterViewListener(this);
            footerView.setVisibility(View.GONE);
            mPullToRefreshLsv.getRefreshableView().addFooterView(footerView);
        }
    }

    protected void addRefreshHeaderView(ListView refreshableView) {

    }

    protected void addRefreshFooterView(ListView refreshableView) {

    }

    /**
     * ItemView的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public final void onRefresh(PullToRefreshBase<ListView> refreshView) {
        if (refreshView.getCurrentMode() == Mode.PULL_FROM_START) {
            onRefresh(true);
        } else {
            onRefresh(false);
        }
    }

    public void onRefresh(boolean isRefresh) {

    }

    /**
     * 设置是否有阻尼效果
     *
     * @return true 有阻尼效果 false 没有阻尼效果
     */
    protected boolean getPullToRefreshOverScrollEnabled() {
        return false;
    }

    public class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return modules.size();

        }

        @Override
        public Object getItem(int position) {
            return modules.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getAdapterViewAtPosition(position, convertView, parent);
        }

        @Override
        public int getViewTypeCount() {
            return getAdapterViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return getAdapterViewType(position);
        }

    }

    public abstract View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent);

    public int getAdapterViewType(int position) {
        return 0;
    }

    public int getAdapterViewTypeCount() {
        return 1;
    }

    public boolean isCanLoadMore() {
        return false;
    }

    @Override
    public final void onLastItemVisible() {
        if (footerView != null && footerView.isCanBeLoadMore()) {
            footerView.notifyDataChanged(FooterView.State.ing);
            loadMore();
        }
    }

    /**
     * 加载更多回调
     */
    public void loadMore() {

    }

    @Override
    public final void onRetryLoadMore() {
        loadMore();
    }

}
