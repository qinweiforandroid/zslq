package cn.wei.library.adapter;

import android.view.View;

public abstract class QBaseViewHolder {
	public abstract void initializeView(View view);

	/**
	 * 
	 * @param model
	 */
	public abstract void initializeData(int position);
	
	
}
