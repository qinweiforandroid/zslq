package cn.wei.library.widget.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.library.R;


public class GeneralRowView extends BaseRowView implements OnClickListener {
	private ImageView mWidgetRowActionImg;
	private ImageView mWidgetRowIconImg;
	private TextView mWidgetRowLabel;
	private OnRowClickListener listener;
	private GeneralRowDescriptor rowDescriptor;

	public GeneralRowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeView();
	}

	public GeneralRowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeView();
	}

	public GeneralRowView(Context context) {
		super(context);
		initializeView();
	}

	public void setOnRowClickListener(OnRowClickListener onRowClickListener) {
		this.listener = onRowClickListener;
	}

	private void initializeView() {
		int padding = (int) getContext().getResources().getDimension(R.dimen.widget_general_row_padding);
		setPadding(padding, padding, padding, padding);
		setGravity(Gravity.CENTER_VERTICAL);
		LayoutInflater.from(context).inflate(R.layout.widget_row_android, this);
		mWidgetRowIconImg = (ImageView) findViewById(R.id.mWidgetRowIconImg);
		mWidgetRowLabel = (TextView) findViewById(R.id.mWidgetRowLabel);
		mWidgetRowActionImg = (ImageView) findViewById(R.id.mWidgetRowActionImg);
	}

	@Override
	public void onClick(View v) {
		if (listener != null) {
			listener.onRowClick(this, rowDescriptor.action);
		}
	}

	public void initializeData(GeneralRowDescriptor rowDescriptor, OnRowClickListener listener) {
		this.listener = listener;
		this.rowDescriptor = rowDescriptor;
	}

	@Override
	public void initializeData(BaseRowDescriptor baseRowDescriptor, OnRowClickListener listener) {
		this.listener = listener;
		this.rowDescriptor = (GeneralRowDescriptor) baseRowDescriptor;

	}

	public void notifyDataChanged() {
		if (rowDescriptor != null) {
			mWidgetRowIconImg.setBackgroundResource(rowDescriptor.iconResId);
			mWidgetRowActionImg.setBackgroundResource(R.drawable.action_row);
			mWidgetRowLabel.setText(rowDescriptor.label);
			if (rowDescriptor.action != null) {
				setBackgroundResource(R.drawable.widgets_general_row_select);
//				mWidgetRowActionImg.setVisibility(View.VISIBLE);
				setOnClickListener(this);
			} else {
//				mWidgetRowActionImg.setVisibility(View.GONE);
			}
		} else {
			setVisibility(View.GONE);
		}
	}

}
