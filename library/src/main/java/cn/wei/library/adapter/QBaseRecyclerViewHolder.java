package cn.wei.library.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qinwei on 2015/10/22 23:10
 * email:qinwei_it@163.com
 */
public abstract class QBaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    public QBaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void initializeData(int position);
}
