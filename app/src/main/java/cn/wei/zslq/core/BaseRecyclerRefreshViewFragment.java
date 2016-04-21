package cn.wei.zslq.core;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.wei.library.adapter.QBaseRecyclerViewHolder;
import cn.wei.library.widget.FooterView;
import cn.wei.zslq.R;

/**
 * Created by qinwei on 2015/10/26 13:51
 * email:qinwei_it@163.com
 */
public abstract class BaseRecyclerRefreshViewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, FooterView.OnFooterViewListener {
    public static final int REFRESH_STATE_REFRESH_COMPLETED = 2;
    public int refresh_state = 2;
    private final int VIEW_TYPE_FOOTER = -2;
    private final int VIEW_TYPE_HEADER = -1;
    private RecyclerView mRecyclerView;
    protected ArrayList<Object> modules = new ArrayList<>();
    protected DataAdapter adapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected FooterView footerView;


    @Override
    protected void initializeView(View v) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(cn.wei.library.R.id.generalSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(cn.wei.library.R.color.red, cn.wei.library.R.color.red, cn.wei.library.R.color.pink, cn.wei.library.R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView)  v.findViewById(cn.wei.library.R.id.generalRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public int lastVisiblePosition;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager instanceof LinearLayoutManager) {
                    lastVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                } else if (manager instanceof GridLayoutManager) {
                    lastVisiblePosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                } else if (manager instanceof StaggeredGridLayoutManager) {
                    lastVisiblePosition = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPositions(null)[0];
                }
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (footerView!=null&&footerView.status == FooterView.State.done && refresh_state == REFRESH_STATE_REFRESH_COMPLETED && modules.size() > 0) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisiblePosition + 1 == adapter.getItemCount() && isCanLoadMore()) {
                        footerView.notifyDataChanged(FooterView.State.ing);
                        loadMore();
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new DataAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

    }

    public void loadMore() {
    }

    @Override
    public void onRetryLoadMore() {

    }

    public class DataAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (VIEW_TYPE_FOOTER == viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(cn.wei.library.R.layout.recycler_footer, parent, false);
                footerView = (FooterView) view.findViewById(R.id.mFooterView);
                footerView.setOnFooterViewListener(BaseRecyclerRefreshViewFragment.this);
                return new FooterViewHolder(view);
            } else if (getHeaderViewLayoutId() != -1 && viewType == VIEW_TYPE_HEADER) {
                return new HeaderViewHolder(LayoutInflater.from(getActivity()).inflate(getHeaderViewLayoutId(), parent, false));
            }
            return onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            QBaseRecyclerViewHolder h = (QBaseRecyclerViewHolder) holder;
            if (getHeaderViewLayoutId() != -1 && position != 0) {
                if (!(isCanLoadMore() && position + 1 == getItemCount()))
                    position--;
            }
            h.initializeData(position);
        }

        @Override
        public int getItemViewType(int position) {
            if (getItemCount() == position + 1 && isCanLoadMore()) {
                return VIEW_TYPE_FOOTER;
            } else if (getHeaderViewLayoutId() != -1 && position == 0) {
                return VIEW_TYPE_HEADER;
            }
            return getAdapterItemViewType(position);
        }

        @Override
        public int getItemCount() {
            int count = modules.size();
            if (getHeaderViewLayoutId() != -1) {
                count++;
            }
            if (isCanLoadMore()) {
                count++;
            }
            return count;
        }

        class HeaderViewHolder extends QBaseRecyclerViewHolder {

            public HeaderViewHolder(View itemView) {
                super(itemView);
                initializeHeaderView(itemView);
                setIsRecyclable(false);
                if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setFullSpan(true);
                    itemView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void initializeData(int position) {

            }
        }

        class FooterViewHolder extends QBaseRecyclerViewHolder {
            public FooterViewHolder(View itemView) {
                super(itemView);
                setIsRecyclable(false);
                if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setFullSpan(true);
                    itemView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void initializeData(int position) {
            }

        }
    }

    public int getAdapterItemViewType(int position) {
        return 0;
    }


    public abstract QBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType);


    public int getHeaderViewLayoutId() {
        return -1;
    }

    public void initializeHeaderView(View view) {

    }

    protected boolean isCanLoadMore() {
        return false;
    }
}
