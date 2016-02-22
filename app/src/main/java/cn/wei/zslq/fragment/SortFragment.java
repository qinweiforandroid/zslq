package cn.wei.zslq.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import cn.wei.library.adapter.QBaseAdapter;
import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseFragment;

/**
 * Created by qinwei on 2016/2/19 10:15
 * email:qinwei_it@163.com
 */
public class SortFragment extends BaseFragment {
    private ListView listView;
    private SortAdapter adapter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_test_sort;
    }

    @Override
    protected void initializeView(View v) {
        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new SortAdapter();
        for (int i = 0; i < 10; i++) {
            if (i > 2 && i < 5) {
                adapter.modules.add("b" + i);
            } else if (i >= 5) {
                adapter.modules.add("c" + i);
            } else if (i <= 2) {
                adapter.modules.add("a" + i);
            }
        }
        listView.setAdapter(adapter);
    }

    public class SortAdapter extends QBaseAdapter implements SectionIndexer {

        @Override
        public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_test_sort_item, null);
                holder.mSortTitleLabel = (TextView) convertView.findViewById(R.id.mSortTitleLabel);
                holder.mNameLabel = (TextView) convertView.findViewById(R.id.mNameLabel);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String data = (String) modules.get(position);
            int section = getSectionForPosition(position);
            if (position == getPositionForSection(section)) {
                holder.mSortTitleLabel.setText(data.substring(0, 1));
                holder.mSortTitleLabel.setVisibility(View.VISIBLE);
            } else {
                holder.mSortTitleLabel.setVisibility(View.GONE);
            }
            holder.mNameLabel.setText(data);
            return convertView;
        }

        @Override
        public Object[] getSections() {
            return new Object[0];
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            for (int i = 0; i < getCount(); i++) {
                char firstChar = ((String) modules.get(i)).charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int getSectionForPosition(int position) {
            String title = (String) modules.get(position);
            return title.charAt(0);
        }
    }

    public static class ViewHolder {
        private TextView mSortTitleLabel;
        private TextView mNameLabel;
    }
}
