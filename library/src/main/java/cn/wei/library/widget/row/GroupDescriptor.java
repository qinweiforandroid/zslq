package cn.wei.library.widget.row;

import java.util.ArrayList;

public class GroupDescriptor {
	public String title;
	public ArrayList<BaseRowDescriptor> baseRowDescriptors;

	public GroupDescriptor(String title,
			ArrayList<BaseRowDescriptor> baseRowDescriptors) {
		this.title = title;
		this.baseRowDescriptors = baseRowDescriptors;
	}

}
