package cn.wei.library.widget.row;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.library.R;
import cn.wei.library.utils.ImageDisplay;


public class SimpleInfoRowView extends BaseRowView implements OnClickListener {
    private ImageView mWidgetRowActionImg;
    private ImageView mInfoIconImg;
    private TextView mInfoAccountLabel;
    private TextView mInfoNickLabel;
    private SimpleInfoRowDescriptor rowDescriptor;
    private OnRowClickListener listener;

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
        LayoutInflater.from(context).inflate(R.layout.widget_row_simple_info,
                this);
        mInfoIconImg = (ImageView) findViewById(R.id.mInfoIconImg);
        mInfoAccountLabel = (TextView) findViewById(R.id.mInfoAccountLabel);
        mInfoNickLabel = (TextView) findViewById(R.id.mInfoNickLabel);
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
            if (TextUtils.isEmpty(rowDescriptor.iconUrl)) {
                mInfoIconImg.setImageResource(rowDescriptor.iconResId);
            } else {
                ImageDisplay.getInstance().displayImage(rowDescriptor.iconUrl, mInfoIconImg);
            }
            mInfoAccountLabel.setText(rowDescriptor.account);
            mInfoNickLabel.setText(rowDescriptor.nick);
            mWidgetRowActionImg.setBackgroundResource(R.drawable.action_row);
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
            listener.onRowClick(this, rowDescriptor.action);
        }
    }

}
