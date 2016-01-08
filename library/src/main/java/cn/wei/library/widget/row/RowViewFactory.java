package cn.wei.library.widget.row;


import android.content.Context;

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
		default:
			break;
		}
		return rowView;
	}
}
