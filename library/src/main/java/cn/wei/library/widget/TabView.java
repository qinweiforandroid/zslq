package cn.wei.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wei.library.R;


public class TabView extends LinearLayout {

    private ImageView mTabIconImg;
    private TextView mTabLabel;

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public TabView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_tab, this);
        mTabIconImg = (ImageView) findViewById(R.id.mTabIconImg);
        mTabLabel = (TextView) findViewById(R.id.mTabLabel);
    }

    public void initializeData(Tab tab) {
        mTabIconImg.setImageResource(tab.getIconRes());
        mTabLabel.setText(tab.getLabel());
    }

    public void notifyDataChanged(int number) {
        notifyDataChanged(number + "");
    }

    public void notifyDataChanged(String number) {
        BadgeView badgeView = new BadgeView(getContext());
        badgeView.setTargetView(mTabIconImg);
        badgeView.setBadgeCount(number);
    }
}
