package cn.wei.zslq.support;

import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.PullToRefreshGridView;

import java.util.ArrayList;

import cn.wei.library.R;

/**
 * <h1>说明:</h1> 此类是一个带有列表的activity <br/>
 * 1.支持图片异步加载 <br/>
 * 2.支持下拉刷新 <br/>
 * 3.支持滑倒底部 加载更多数据 <br/>
 * 4.PullToRefreshGridView <h1>注意:</h1> 布局文件中必须有 R.id.mPullToRefreshGridView
 *
 * @author 秦伟
 * @version 1.2
 * @created 创建时间: 2015-4-3 下午10:46:16
 */
public abstract class BaseGridViewListActivity extends BaseActivity implements OnItemClickListener,
        OnRefreshListener<GridView> {
    protected PullToRefreshGridView mPullToRefreshGridView;
    protected ListAdapter mAdapter;
    protected ArrayList<Object> modules = new ArrayList<Object>();

    @Override
    protected void initializeView() {
        mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.generalPullToRefreshGridView);
        if (mPullToRefreshGridView == null) {
            throw new IllegalArgumentException("you contentView must contains id:generalPullToRefreshLsv");
        }
        mPullToRefreshGridView.setMode(Mode.PULL_FROM_START);
        mPullToRefreshGridView.setPullToRefreshOverScrollEnabled(getPullToRefreshOverScrollEnabled());
        mAdapter = new ListAdapter();
        mPullToRefreshGridView.setOnRefreshListener(this);
        mPullToRefreshGridView.getRefreshableView().setAdapter(mAdapter);
        mPullToRefreshGridView.getRefreshableView().setOnItemClickListener(this);
    }


    protected boolean getPullToRefreshOverScrollEnabled() {
        return false;
    }

    @Override
    public void onRefresh(PullToRefreshBase<GridView> refreshView) {
        String label = DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最近更新：" + label);

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

    public int getAdapterViewTypeCount() {
        return 1;
    }

    public abstract View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent);


    public int getAdapterViewType(int position) {
        return 0;
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
}
