package cn.wei.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.wei.library.R;


public class FooterView extends LinearLayout implements OnClickListener {

    private ProgressBar mProgressBar;
    private TextView mFooterLabel;
    public State status = State.done;
    private OnFooterViewListener listener;

    public interface OnFooterViewListener {
        /**
         * 加载更多失败重试回调函数
         */
        void onRetryLoadMore();
    }

    public enum State {
        /**
         * 正在加载
         */
        ing,
        /**
         * 加载成功
         */
        done,
        /**
         * 加载失败
         */
        error,
        /**
         * 无数据
         */
        no_data
    }

    public FooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public FooterView(Context context) {
        super(context);
        initializeView(context);
    }


    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer, this);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        mFooterLabel = (TextView) findViewById(R.id.mFooterLabel);
        this.setOnClickListener(this);
    }


    public boolean isCanBeLoadMore() {
        switch (status) {
            case done:
            case error:
                return true;
            default:
                return false;
        }
    }

    /**
     * update footerView UI
     *
     * @param status footerView status
     */
    public void notifyDataChanged(State status) {
        this.status = status;
        setVisibility(View.VISIBLE);
        switch (status) {
            case done:
                setVisibility(GONE);
                break;
            case ing:
                mFooterLabel.setText("正在加载");
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case error:
                mFooterLabel.setText("点击重试");
                mProgressBar.setVisibility(View.GONE);
                break;
            case no_data:
                mProgressBar.setVisibility(View.GONE);
                mFooterLabel.setText("没有更多数据!");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (status) {
            case error:
                if (listener != null) {
                    listener.onRetryLoadMore();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置重试加载监听器
     *
     * @param listener 加载监听器
     */
    public void setOnFooterViewListener(OnFooterViewListener listener) {
        this.listener = listener;
    }
}
