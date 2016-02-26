package cn.wei.library.widget.row.expand;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.library.R;
import cn.wei.library.utils.ImageDisplay;
import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.BaseRowView;
import cn.wei.library.widget.row.OnRowClickListener;

/**
 * 用戶头像row
 */
public class UserIconRowView extends BaseRowView implements OnClickListener {
    private ImageView mWidgetRowActionImg;
    private ImageView mWidgetRowIconImg;
    private TextView mWidgetRowLabel;
    private OnRowClickListener listener;
    private UserIconRowDescriptor rowDescriptor;
    private ImageView mUserIconImg;

    public UserIconRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    public UserIconRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public UserIconRowView(Context context) {
        super(context);
        initializeView();
    }

    public void setOnRowClickListener(OnRowClickListener onRowClickListener) {
        this.listener = onRowClickListener;
    }

    private void initializeView() {
        LayoutInflater.from(context).inflate(R.layout.widget_row_user_icon, this);
        mWidgetRowIconImg = (ImageView) findViewById(R.id.mWidgetRowIconImg);
        mWidgetRowLabel = (TextView) findViewById(R.id.mWidgetRowLabel);
        mUserIconImg = (ImageView) findViewById(R.id.mUserIconImg);
        mWidgetRowActionImg = (ImageView) findViewById(R.id.mWidgetRowActionImg);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRowClick(this, rowDescriptor.action);
        }
    }

    public void initializeData(UserIconRowDescriptor rowDescriptor, OnRowClickListener listener) {
        this.listener = listener;
        this.rowDescriptor = rowDescriptor;
    }

    @Override
    public void initializeData(BaseRowDescriptor baseRowDescriptor, OnRowClickListener listener) {
        this.listener = listener;
        this.rowDescriptor = (UserIconRowDescriptor) baseRowDescriptor;

    }

    public void notifyDataChanged() {
        if (rowDescriptor != null) {
            if (rowDescriptor.iconResId == 0) {
                mWidgetRowIconImg.setVisibility(View.GONE);
            } else {
                mWidgetRowIconImg.setBackgroundResource(rowDescriptor.iconResId);
            }
            mWidgetRowLabel.setText(rowDescriptor.label);
            ImageDisplay.getInstance().displayImage(rowDescriptor.iconUrl,mUserIconImg,R.drawable.ic_launcher,R.drawable.ic_launcher);
            if (rowDescriptor.action != null) {
                setBackgroundResource(R.drawable.widgets_general_row_select);
                mWidgetRowActionImg.setBackgroundResource(R.drawable.action_row);
                mWidgetRowActionImg.setVisibility(View.VISIBLE);
                setOnClickListener(this);
            } else {
                mWidgetRowActionImg.setVisibility(View.GONE);
            }
        } else {
            setVisibility(View.GONE);
        }
    }
}
