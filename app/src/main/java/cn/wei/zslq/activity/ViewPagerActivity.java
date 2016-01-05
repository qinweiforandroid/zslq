package cn.wei.zslq.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.wei.zslq.support.BaseActivity;
import cn.wei.zslq.R;

/**
 * Created by qinwei on 2016/1/5 10:22
 * email:qinwei_it@163.com
 */
public class ViewPagerActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ImageView[] views = new ImageView[3];

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_viewpager);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
    }

    @Override
    protected void initializeData() {
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.ic_load_err);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views[0] = view;

        ImageView view1 = new ImageView(this);
        view1.setImageResource(R.drawable.ic_launcher);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views[1] = view1;

        ImageView view2 = new ImageView(this);
        view2.setImageResource(R.drawable.teaching);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        views[2] = view2;

        mViewPager.setAdapter(new ViewPagerAdapter());
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views[position % 3],0);
            return views[position % 3];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views[position % views.length]);
        }
    }
}
