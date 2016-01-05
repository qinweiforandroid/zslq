package cn.wei.library.adapter;

import java.util.ArrayList;
import java.util.Collection;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 */
public abstract class QBaseAdapter extends BaseAdapter {
	public ArrayList<Object> modules = new ArrayList<Object>();

	@Override
	public int getCount() {
		return modules.size();
	}

	@Override
	public Object getItem(int position) {
		return modules.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getAdapterViewAtPosition(position, convertView, parent);
	}

	public abstract View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent);

}
