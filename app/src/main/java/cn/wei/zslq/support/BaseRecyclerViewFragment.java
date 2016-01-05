package cn.wei.zslq.support;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.wei.library.R;
import cn.wei.library.adapter.QBaseRecyclerViewHolder;

/**
 * Created by qinwei on 2015/10/22 23:04
 * email:qinwei_it@163.com
 */
public abstract class BaseRecyclerViewFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    protected ArrayList<Object> modules = new ArrayList<>();
    protected DataAdapter adapter;

    @Override
    protected void initializeView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.generalRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new DataAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
    }

    public class DataAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return BaseRecyclerViewFragment.this.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            QBaseRecyclerViewHolder h = (QBaseRecyclerViewHolder) holder;
            h.initializeData(position);
        }

        @Override
        public int getItemViewType(int position) {
            return getAdapterItemViewType(position);
        }


        @Override
        public int getItemCount() {
            return modules.size();
        }
    }

    public int getAdapterItemViewType(int position) {
        return 0;
    }

    public abstract QBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
}
