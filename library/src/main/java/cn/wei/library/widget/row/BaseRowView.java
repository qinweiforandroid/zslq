package cn.wei.library.widget.row;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class BaseRowView extends LinearLayout {
	protected Context context;
	protected RowClassEnum clazz;

	public BaseRowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeContext(context, RowClassEnum.GeneralRowView);
	}

	public BaseRowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeContext(context, RowClassEnum.GeneralRowView);
	}

	public BaseRowView(Context context) {
		super(context);
		initializeContext(context, RowClassEnum.GeneralRowView);
	}

	public BaseRowView(Context context, RowClassEnum clazz) {
		super(context);
		initializeContext(context, clazz);
	}

	private void initializeContext(Context context, RowClassEnum clazz) {
		this.clazz = clazz;
		this.context = context;
	}

	public abstract void initializeData(BaseRowDescriptor baseRowDescriptor, OnRowClickListener listener);

	public abstract void notifyDataChanged();

}
