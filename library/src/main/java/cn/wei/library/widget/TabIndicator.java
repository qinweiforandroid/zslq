package cn.wei.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.wei.library.utils.TextUtil;

/**
 */
public class TabIndicator extends LinearLayout implements OnClickListener {
	private int mTabSize;
	private int mTabIndex = -1;
	private OnTabClickListener listener;
	private LayoutParams mTabParams;
	private final static int ID_PREFIX = 100000;

	public TabIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeView();
	}

	public TabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeView();
	}

	public TabIndicator(Context context) {
		super(context);
		initializeView();
	}

	public void setOnTabClickListener(OnTabClickListener listener) {
		this.listener = listener;
	}

	public void initializeData(ArrayList<Tab> tabs) {
		if (!TextUtil.isValidate(tabs)) {
			throw new IllegalArgumentException("the tabs should not be 0");
		}
		mTabSize = tabs.size();
		TabView tab = null;
		for (int i = 0; i < mTabSize; i++) {
			tab = new TabView(getContext());
			tab.setId(ID_PREFIX + i);
			tab.setOnClickListener(this);
			tab.initializeData(tabs.get(i));
			addView(tab, mTabParams);
		}
	}

	public void onDataChanged(int index, int number) {
		onDataChanged(index, number + "");
	}
	public void onDataChanged(int index, String number) {
		((TabView) (findViewById(ID_PREFIX + index))).notifyDataChanged(number);
	}

	private void initializeView() {
		setOrientation(LinearLayout.HORIZONTAL);
		MarginLayoutParams param = (MarginLayoutParams) new LayoutParams(0, LayoutParams.WRAP_CONTENT);
		mTabParams = new LayoutParams(param);
		mTabParams.weight = 1.0f;
	}

	@Override
	public void onClick(View v) {
		int index = v.getId() - ID_PREFIX;
		if (listener != null && mTabIndex != index) {
			if(listener.onTabClick(v.getId() - ID_PREFIX)) {
				v.setSelected(true);
				if (mTabIndex != -1) {
					View old = findViewById(ID_PREFIX + mTabIndex);
					old.setSelected(false);
				}
				mTabIndex = index;
//				listener.onTabClick(v.getId() - ID_PREFIX);
			}
		}
	}

	public interface OnTabClickListener {
		boolean onTabClick(int index);
	}

	public void setCurrentTab(int i) {
		if (i == mTabIndex) {
			return;
		}
		View view = findViewById(ID_PREFIX + i);
		onClick(view);
	}
}
