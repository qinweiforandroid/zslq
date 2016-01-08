package cn.wei.library.widget.row;

public class SimpleInfoRowDescriptor extends BaseRowDescriptor {
	public int iconResId;
	public String label;

	public SimpleInfoRowDescriptor(int iconResId, String label, RowActionEnum action) {
		this.iconResId = iconResId;
		this.label = label;
		this.action = action;
		this.clazz = RowClassEnum.SimpleInfoRowView;
	}
}
