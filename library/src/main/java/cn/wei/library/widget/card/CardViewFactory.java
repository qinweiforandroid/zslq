package cn.wei.library.widget.card;

import android.content.Context;

/**
 * @author qinwei email:qinwei_it@163.com
 * @version 1.0
 * @created createTime: 2016-1-8 下午5:13:36
 */

public class CardViewFactory {
    public static BaseCardItemView produceRowView(Context context, CardClassEnum clazz) {
        BaseCardItemView cardView = null;
        switch (clazz) {
            case CardItemView:
                cardView = new CardItemView(context);
                break;
            default:
                throw new IllegalAccessError(clazz.name() + " not find");
        }
        return cardView;
    }
}
