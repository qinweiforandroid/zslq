package cn.wei.library.widget.row.expand;

import cn.wei.library.widget.row.BaseRowDescriptor;
import cn.wei.library.widget.row.RowActionEnum;
import cn.wei.library.widget.row.RowClassEnum;

public class UserIconRowDescriptor extends BaseRowDescriptor {
    public String label;
    public String iconUrl;
    public int iconResId;
    public UserIconRowDescriptor(String label, String iconUrl, RowActionEnum action) {
        this.iconUrl = iconUrl;
        this.label = label;
        this.action = action;
        this.clazz = RowClassEnum.UserIconRowView;
    }
}
