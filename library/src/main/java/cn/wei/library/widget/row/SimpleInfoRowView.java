package cn.wei.library.widget.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.library.R;


public class SimpleInfoRowView extends BaseRowView implements OnClickListener {
	private ImageView mWidgetRowActionImg;
	private ImageView mWidgetRowIconImg;
	private TextView mWidgetRowLabel;
	private OnRowClickListener listener;
	private SimpleInfoRowDescriptor rowDescriptor;

	public SimpleInfoRowView(Context context, RowClassEnum clazz) {
		super(context, clazz);
		initializeView();
	}

	public SimpleInfoRowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeView();
	}

	public SimpleInfoRowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeView();
	}

	public SimpleInfoRowView(Context context) {
		super(context);
		initializeView();
	}

	private void initializeView() {
		LayoutInflater.from(context).inflate(R.layout.widget_simple_info_row,
				this);
		mWidgetRowIconImg = (ImageView) findViewById(R.id.mWidgetRowIconImg);
		//mWidgetRowLabel = (TextView) findViewById(R.id.mWidgetRowLabel);
		mWidgetRowActionImg = (ImageView) findViewById(R.id.mWidgetRowActionImg);

	}

	@Override
	public void initializeData(BaseRowDescriptor baseRowDescriptor,
			OnRowClickListener listener) {
		this.listener = listener;
		this.rowDescriptor = (SimpleInfoRowDescriptor) baseRowDescriptor;
	}

	@Override
	public void notifyDataChanged() {
		if (rowDescriptor != null) {
			mWidgetRowIconImg.setBackgroundResource(rowDescriptor.iconResId);
			mWidgetRowActionImg.setBackgroundResource(R.drawable.action_row);
			//mWidgetRowLabel.setText(rowDescriptor.label);
			if (rowDescriptor.action != null) {
				setBackgroundResource(R.drawable.widgets_general_row_select);
				mWidgetRowActionImg.setVisibility(View.VISIBLE);
				setOnClickListener(this);
			} else {
				mWidgetRowActionImg.setVisibility(View.GONE);
			}
		} else {
			setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		if (listener != null) {
			listener.onRowClick(this,rowDescriptor.action);
		}
	}

}
