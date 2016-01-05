package com.handmark.pulltorefresh;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class PullToRefreshSwipeMenuListView extends
		PullToRefreshAdapterViewBase<SwipeMenuListView> {

	public PullToRefreshSwipeMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PullToRefreshSwipeMenuListView(
			Context context,
			Mode mode,
			AnimationStyle animStyle) {
		super(context, mode, animStyle);
		// TODO Auto-generated constructor stub
	}

	public PullToRefreshSwipeMenuListView(Context context,
			Mode mode) {
		super(context, mode);
		// TODO Auto-generated constructor stub
	}

	public PullToRefreshSwipeMenuListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected SwipeMenuListView createRefreshableView(Context context,
			AttributeSet attrs) {
		MySwipeMenuListView mySwipeMenuListView = new MySwipeMenuListView(
				context, attrs);
		mySwipeMenuListView.setId(0);
		return mySwipeMenuListView;
	}

	class MySwipeMenuListView extends SwipeMenuListView implements
			EmptyViewMethodAccessor {

		public MySwipeMenuListView(Context context, AttributeSet attrs,
				int defStyle) {
			super(context, attrs, defStyle);
		}

		public MySwipeMenuListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public MySwipeMenuListView(Context context) {
			super(context);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshSwipeMenuListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

	}

}
