package cn.wei.library.widget.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.library.R;
import cn.wei.library.utils.ImageDisplay;

public class CardItemView extends BaseCardItemView implements OnClickListener {
    private CardClickListener listener;
    private CardDescriptor descriptor;
    private ImageView mCardItemIconImg;
    private TextView mCardItemLabel;

    public CardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardItemView(Context context) {
        super(context);
    }

    public void initializeData(CardDescriptor descriptor, CardClickListener listener) {
        this.descriptor = descriptor;
        this.listener = listener;
    }

    /**
     *
     */
    @Override
    public void initializeView() {
        LayoutInflater.from(context).inflate(R.layout.widget_card_view_item, this);
        mCardItemIconImg = (ImageView) findViewById(R.id.mCardItemIconImg);
        mCardItemLabel = (TextView) findViewById(R.id.mCardItemLabel);
        setOnClickListener(this);
    }

    @Override
    public void initializeData(BaseCardDescriptor baseCardDescriptor, CardClickListener listener) {
        this.descriptor = (CardDescriptor) baseCardDescriptor;
        this.listener = listener;
    }

    public void notifyDataChanged() {
        if (descriptor.getResIcon() != 0) {
            mCardItemIconImg.setImageResource(descriptor.getResIcon());
        } else {
            // TODO set iconUrl
            // mCardItemIconImg.setImageResource(descriptor.getResIcon());
            ImageDisplay.getInstance().displayImage(descriptor.getIconUrl(), mCardItemIconImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
        }
        mCardItemLabel.setText(descriptor.getLabel());
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onCardClickListener(descriptor.getId());
        }
    }
}
