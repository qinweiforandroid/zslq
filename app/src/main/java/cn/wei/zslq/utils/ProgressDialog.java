package cn.wei.zslq.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.wei.library.widget.ProgressWheel;
import cn.wei.zslq.R;


/**
 * @author qinwei email:qinwei_it@163.com
 * @created createTime: 2015-10-11 下午12:15:53
 * @version 1.0
 */

public class ProgressDialog extends Dialog {

	private String message;
	private ProgressWheel mProgressBar;
	private TextView mProgressDialogLabel;

	public ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public ProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public ProgressDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progress);
		mProgressDialogLabel = (TextView) findViewById(R.id.mProgressDialogLabel);
		mProgressBar = (ProgressWheel) findViewById(R.id.progress_wheel);
		if (TextUtils.isEmpty(message)) {
			mProgressDialogLabel.setVisibility(View.GONE);
		} else {
			mProgressDialogLabel.setVisibility(View.VISIBLE);
			mProgressDialogLabel.setText(message);
		}
	}

	public static ProgressDialog show(Context context, String message) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		return dialog;
	}
	private void setMessage(String message) {
		this.message = message;
	}
}
