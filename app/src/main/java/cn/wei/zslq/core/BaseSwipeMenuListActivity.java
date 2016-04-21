package cn.wei.zslq.core;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.PullToRefreshSwipeMenuListView;

import java.util.ArrayList;

import cn.wei.library.R;

/**
 * 左滑动操作的listview
 * 
 * @author 秦伟
 * @version 1.0
 * @created 创建时间: 2015-2-10 下午3:34:31
 */
public abstract class BaseSwipeMenuListActivity extends BaseActivity implements OnRefreshListener<SwipeMenuListView>, OnItemClickListener,
		OnMenuItemClickListener, OnSwipeListener {
	protected PullToRefreshSwipeMenuListView mPullToRefresh;
	protected ListAdapter mAdapter;
	protected ArrayList<Object> modules = new ArrayList<Object>();

	@Override
	protected void initializeView() {
		mPullToRefresh = (PullToRefreshSwipeMenuListView) findViewById(R.id.generalPullToRefreshLsv);
		if (mPullToRefresh == null) {
			throw new IllegalArgumentException("you contentView must contains id:generalPullToRefreshLsv");
		}
		mPullToRefresh.setMode(Mode.PULL_FROM_START);
		mPullToRefresh.setPullToRefreshOverScrollEnabled(getPullToRefreshOverScrollEnabled());
		mAdapter = new ListAdapter();
		mPullToRefresh.setAdapter(mAdapter);
		mPullToRefresh.setOnRefreshListener(this);
		mPullToRefresh.getRefreshableView().setOnItemClickListener(this);
		addRefreshHeaderView(mPullToRefresh.getRefreshableView());
		addRefreshFooterView(mPullToRefresh.getRefreshableView());
		initailizeSwipeMenuList();
	}

	protected void addRefreshHeaderView(ListView refreshListView) {
	}

	protected void addRefreshFooterView(ListView refreshListView) {
	}

	private void initailizeSwipeMenuList() {
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				createSwipeMenuItems(menu);
			}
		};
		// set creator
		mPullToRefresh.getRefreshableView().setMenuCreator(creator);

		// step 2. listener item click event
		mPullToRefresh.getRefreshableView().setOnMenuItemClickListener(this);

		// set SwipeListener
		mPullToRefresh.getRefreshableView().setOnSwipeListener(this);

		// other setting
		mPullToRefresh.getRefreshableView().setCloseInterpolator(getCloseInterpolator());

	}

	protected boolean getPullToRefreshOverScrollEnabled() {
		return false;
	}

	@Override
	public void onRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
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

	protected abstract void createSwipeMenuItems(SwipeMenu menu);

	protected Interpolator getCloseInterpolator() {
		return new BounceInterpolator();
	}

	@Override
	public void onSwipeStart(int position) {
	}

	@Override
	public void onSwipeEnd(int position) {
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

	public int getAdapterViewTypeCount() {
		return 1;
	}

	public int getAdapterViewType(int position) {
		return 0;
	}

}
