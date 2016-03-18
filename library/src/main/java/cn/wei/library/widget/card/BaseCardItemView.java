package cn.wei.library.widget.card;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class BaseCardItemView extends LinearLayout {

	protected Context context;

	public BaseCardItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeContext(context);
		initializeView();
	}

	public BaseCardItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeContext(context);
		initializeView();
	}

	public BaseCardItemView(Context context) {
		super(context);
		initializeContext(context);
		initializeView();
	}

	private void initializeContext(Context c) {
		context = c;
	}

	public abstract void initializeView();

	public abstract void initializeData(BaseCardDescriptor baseCardDescriptor, CardClickListener listener);

	public abstract void notifyDataChanged();

}
