package cn.wei.library.widget.row;


import android.content.Context;

import cn.wei.library.widget.row.expand.IOSRowView;
import cn.wei.library.widget.row.expand.UserIconRowView;

public class RowViewFactory {

    public static BaseRowView produceRowView(Context context, RowClassEnum clazz) {
        BaseRowView rowView = null;
        switch (clazz) {
            case GeneralRowView:
                rowView = new GeneralRowView(context);
                break;
            case SimpleInfoRowView:
                rowView = new SimpleInfoRowView(context);
                break;
            case IOSRowView:
                rowView = new IOSRowView(context);
                break;
            case UserIconRowView:
                rowView=new UserIconRowView(context);
                break;
            default:
                break;
        }
        return rowView;
    }
}
