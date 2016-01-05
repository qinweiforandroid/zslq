package cn.wei.zslq.support;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.wei.library.R;
import cn.wei.library.adapter.QBaseRecyclerViewHolder;
import cn.wei.library.widget.ProgressWheel;

/**
 * Created by qinwei on 2015/10/26 13:51
 * email:qinwei_it@163.com
 */
public abstract class BaseRecyclerRefreshViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final int REFRESH_STATE_REFRESH_COMPLETED = 2;
    public static final int LOAD_MORE_LOADING = 3;
    public static final int LOAD_MORE_COMPLETED = 4;
    public static final int LOAD_MORE_NO_DATA = 5;
    public static final int LOAD_MORE_ERROR = 6;
    public int load_more_state = 4;
    public int refresh_state = 2;
    private final int VIEW_TYPE_FOOTER = -2;
    private final int VIEW_TYPE_HEADER = -1;
    private RecyclerView mRecyclerView;
    protected ArrayList<Object> modules = new ArrayList<>();
    protected DataAdapter adapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void initializeView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.generalSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.gold, R.color.red, R.color.pink, R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.generalRecyclerView);
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
                if (load_more_state == LOAD_MORE_COMPLETED && refresh_state == REFRESH_STATE_REFRESH_COMPLETED && modules.size() > 0) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisiblePosition + 1 == adapter.getItemCount() && isCanLoadMore()) {
                        load_more_state = LOAD_MORE_LOADING;
                        loadMore();
                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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


    public void loadMoreCompleted() {
        load_more_state = LOAD_MORE_COMPLETED;
        adapter.notifyItemChanged(adapter.getItemCount() - 1);
    }

    public void loadMoreError() {
        load_more_state = LOAD_MORE_ERROR;
        adapter.notifyItemChanged(adapter.getItemCount() - 1);
    }

    public void noData() {
        load_more_state = LOAD_MORE_NO_DATA;
        adapter.notifyItemChanged(adapter.getItemCount() - 1);
    }

    public class DataAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (VIEW_TYPE_FOOTER == viewType) {
                return new FooterViewHolder(LayoutInflater.from(BaseRecyclerRefreshViewActivity.this).inflate(R.layout.recycler_footer, parent, false));
            } else if (getHeaderViewLayoutId() != -1 && viewType == VIEW_TYPE_HEADER) {
                return new HeaderViewHolder(LayoutInflater.from(BaseRecyclerRefreshViewActivity.this).inflate(getHeaderViewLayoutId(), parent, false));
            }
            return BaseRecyclerRefreshViewActivity.this.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            QBaseRecyclerViewHolder h = (QBaseRecyclerViewHolder) holder;
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
            return modules.size();
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

        class FooterViewHolder extends QBaseRecyclerViewHolder implements View.OnClickListener {

            private TextView mFooterLabel;
            private ProgressWheel mFooterProgress;
            private View view;

            public FooterViewHolder(View itemView) {
                super(itemView);
                this.view = itemView;
                setIsRecyclable(false);
                mFooterLabel = (TextView) view.findViewById(R.id.mFooterLabel);
                mFooterProgress = (ProgressWheel) view.findViewById(R.id.mFooterProgress);
                mFooterLabel.setOnClickListener(this);
                if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setFullSpan(true);
                    itemView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void initializeData(int position) {
                if (load_more_state == LOAD_MORE_COMPLETED || load_more_state == LOAD_MORE_LOADING) {
                    mFooterProgress.setVisibility(View.VISIBLE);
                    mFooterLabel.setEnabled(false);
                    mFooterLabel.setVisibility(View.GONE);
                } else if (load_more_state == LOAD_MORE_NO_DATA) {
                    mFooterLabel.setText("没有更多数据");
                    mFooterProgress.setVisibility(View.GONE);
                    mFooterLabel.setEnabled(false);
                    mFooterLabel.setVisibility(View.VISIBLE);
                } else if (load_more_state == LOAD_MORE_ERROR) {
                    mFooterLabel.setVisibility(View.VISIBLE);
                    mFooterProgress.setVisibility(View.GONE);
                    mFooterLabel.setText("加载失败,点击重试");
                    mFooterLabel.setEnabled(true);
                }
            }

            @Override
            public void onClick(View v) {
                load_more_state = LOAD_MORE_LOADING;
                initializeData(adapter.getItemCount() - 1);
                loadMore();
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
