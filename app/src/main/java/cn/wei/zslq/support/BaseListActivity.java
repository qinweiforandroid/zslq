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

/**
 * <h1>说明:</h1> 此类是一个带有列表的activity <br/>
 * 1.支持图片异步加载 <br/>
 * 2.支持下拉刷新 <br/>
 * 3.支持滑倒底部 加载更多数据 <br/>
 * 4.支持ListView所有功能 <h1>注意:</h1> 布局文件中必须有 R.id.generalPullToRefreshLsv
 *
 * @author 秦伟
 * @version 1.2
 * @created 创建时间: 2015-8-21 上午12:21:41
 */
public abstract class BaseListActivity extends BaseActivity implements OnItemClickListener, OnRefreshListener<ListView>, PullToRefreshBase.OnLastItemVisibleListener, FooterView.OnFooterViewListener {
    protected ArrayList<Object> modules = new ArrayList<Object>();
    protected PullToRefreshListView mPullToRefreshLsv;
    protected ListAdapter adapter;
    private FooterView footerView;

    @Override
    protected void initializeView() {
        mPullToRefreshLsv = (PullToRefreshListView) findViewById(R.id.generalPullToRefreshLsv);
        mPullToRefreshLsv.setPullToRefreshOverScrollEnabled(getPullToRefreshOverScrollEnabled());
        addRefreshHeaderView(mPullToRefreshLsv.getRefreshableView());
        addRefreshFooterView(mPullToRefreshLsv.getRefreshableView());
        mPullToRefreshLsv.setOnItemClickListener(this);
        mPullToRefreshLsv.setOnRefreshListener(this);
        adapter = new ListAdapter();
        mPullToRefreshLsv.setAdapter(adapter);
        if (isCanLoadMore()) {
            mPullToRefreshLsv.setOnLastItemVisibleListener(this);
            footerView = new FooterView(this);
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

    /**
     * 由子类控制是否需要加载更多功能
     * @return true 需要加载更多 false 不要需要  默认false
     */
    public boolean isCanLoadMore() {
        return false;
    }

    @Override
    public void onLastItemVisible() {
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
    public void onRetryLoadMore() {

    }

}
