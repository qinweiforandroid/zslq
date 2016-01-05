package cn.wei.zslq.support;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.umeng.analytics.MobclickAgent;

import cn.wei.library.R;
import cn.wei.library.widget.EmptyView;

/**
 * fragment简单的封装
 *
 * @author 秦伟
 * @version 1.0
 * @created 创建时间: 2015-8-22 下午7:07:47
 */
public abstract class BaseFragment extends Fragment implements EmptyView.OnRetryListener {
    private View view;
    protected ViewSwitcher mViewSwitcher;
    protected EmptyView mEmptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeArguments(savedInstanceState);
    }

    /**
     * 初始化fragment的参数配置
     *
     * @param args 配置参数
     */
    protected void initializeArguments(Bundle args) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getFragmentLayoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view.findViewById(R.id.mEmptyView) != null) {
            mEmptyView = (EmptyView) view.findViewById(R.id.mEmptyView);
            mEmptyView.setOnRetryListener(this);
            mEmptyView.notifyDataChanged(EmptyView.State.ing);
            mViewSwitcher = (ViewSwitcher) view.findViewById(R.id.mViewSwitcher);
            load();
        }
        initializeView(view);
    }

    protected abstract int getFragmentLayoutId();

    protected abstract void initializeView(View v);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public View getCurrentView() {
        return view;
    }

    @Override
    public void onRetry() {
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        load();
    }

    public void load() {
        mViewSwitcher.setDisplayedChild(0);
    }

    public void showContent() {
        mEmptyView.notifyDataChanged(EmptyView.State.done);
        mViewSwitcher.setDisplayedChild(1);
    }
}
