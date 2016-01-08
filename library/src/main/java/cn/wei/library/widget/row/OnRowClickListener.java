package cn.wei.library.widget.row;

import android.view.View;

public interface OnRowClickListener extends BaseRowViewClickListener {
	void onRowClick(View rowView, RowActionEnum action);
}
