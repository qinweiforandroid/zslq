package cn.wei.library.widget.row.expand;

import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.library.widget.row.RowClassEnum;

public class IOSRowDescriptor extends BaseRowDescriptor {
    public int iconResId;
    public String label;
    public String value;

    public IOSRowDescriptor(int iconResId, String label, RowActionEnum action) {
        this.iconResId = iconResId;
        this.label = label;
        this.action = action;
        this.clazz = RowClassEnum.IOSRowView;
    }

    public IOSRowDescriptor(int iconResId, String label, String value, RowActionEnum action) {
        this.value = value;
        this.iconResId = iconResId;
        this.label = label;
        this.action = action;
        this.clazz = RowClassEnum.IOSRowView;
    }

    public IOSRowDescriptor(String label, String value, RowActionEnum action) {
        this.value = value;
        this.label = label;
        this.action = action;
        this.clazz = RowClassEnum.IOSRowView;
    }

    public IOSRowDescriptor(String label, RowActionEnum action) {
        this.label = label;
        this.action = action;
        this.clazz = RowClassEnum.IOSRowView;
    }
}
