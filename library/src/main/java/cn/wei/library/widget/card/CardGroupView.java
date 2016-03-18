package cn.wei.library.widget.card;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import cn.wei.library.R;
import cn.wei.library.utils.DensityUtil;


public class CardGroupView extends LinearLayout {
    private CardGroupDescriptor descriptor;
    private TextView mCardGroupTitleLabel;
    private ImageView mCardGroupTitleIconImg;
    private TableLayout mTableLayout;
    private int rowSize = 3;
    private Context context;
    private CardClickListener listener;

    public CardGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    public CardGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public CardGroupView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.widget_card_group_view, this);
        mCardGroupTitleLabel = (TextView) findViewById(R.id.mCardGroupTitleLabel);
        mCardGroupTitleIconImg = (ImageView) findViewById(R.id.mCardGroupTitleIconImg);
        mTableLayout = (TableLayout) findViewById(R.id.mTableLayout);
    }

    public void initializeData(CardGroupDescriptor descriptor, CardClickListener listener) {
        this.listener = listener;
        this.descriptor = descriptor;
    }

    public void configColumnSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public void notifyDataChanged() {
        if (descriptor != null) {
            if (TextUtils.isEmpty(descriptor.groupTitle)) {
                findViewById(R.id.mGroupTitleContainer).setVisibility(View.GONE);
            } else {
                if (descriptor.groupIcon != 0) {
                    mCardGroupTitleIconImg.setImageResource(descriptor.groupIcon);
                }
                mCardGroupTitleLabel.setText(descriptor.groupTitle);

            }

            if (descriptor.cardDescriptors == null) {
                throw new IllegalArgumentException("you must setting descriptors ");
            }
            TableRow row = null;
            BaseCardItemView cardView;
            // TableRow.LayoutParams paramsRow = new TableRow.LayoutParams(0,
            // DensityUtil.dip2px(context, 55), 1);
            TableRow.LayoutParams paramsRow = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
            paramsRow.setMargins(DensityUtil.dip2px(context, 1), DensityUtil.dip2px(context, 1), DensityUtil.dip2px(context, 1),
                    DensityUtil.dip2px(context, 1));
            for (int i = 0; i < descriptor.cardDescriptors.size(); i += rowSize) {
                row = new TableRow(context);
                for (int j = 0; j < rowSize; j++) {
                    int curPosition = i + j;
                    if (curPosition > descriptor.cardDescriptors.size() - 1) {
                        View view = new View(context);
                        view.setLayoutParams(paramsRow);
                        view.setVisibility(View.INVISIBLE);
                        row.addView(view);
                        continue;
                    }
                    BaseCardDescriptor baseCardDescriptor = descriptor.cardDescriptors.get(i);
                    cardView = CardViewFactory.produceRowView(context, baseCardDescriptor.clazz);
                    cardView.setGravity(Gravity.CENTER);
                    cardView.setLayoutParams(paramsRow);
                    cardView.initializeData(descriptor.cardDescriptors.get(curPosition), listener);
                    cardView.notifyDataChanged();
                    row.addView(cardView);
                }
                mTableLayout.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
        }
    }

}
