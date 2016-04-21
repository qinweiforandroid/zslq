
package cn.wei.zslq.activity;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.wei.library.widget.chat.KeyboardUtil;
import cn.wei.library.widget.chat.PanelLayout;
import cn.wei.zslq.R;
import cn.wei.zslq.core.BaseActivity;

/**
 * @author Nana
 * @Description 主消息界面
 * @date 2014-7-15
 * <p/>
 */
public class MessageActivity extends BaseActivity {


    private ListView mLsv;
    private EditText mSendEdt;
    private PanelLayout mPanelRoot;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mSendEdt = (EditText) findViewById(R.id.send_edt);
        mPanelRoot = (PanelLayout) findViewById(R.id.panel_root);
        mLsv = (ListView) findViewById(R.id.mLsv);

        mLsv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));

        mLsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    KeyboardUtil.hideKeyboard(mSendEdt);
                    mPanelRoot.setVisibility(View.GONE);
                }

                return false;
            }
        });
    }

    @Override
    protected void initializeData() {
        setTitle("聊天详情");
    }

    public void onClickPlusIv(final View view) {
        if (mPanelRoot.getVisibility() == View.VISIBLE) {
            KeyboardUtil.showKeyboard(mSendEdt);
        } else {
            KeyboardUtil.hideKeyboard(mSendEdt);
            mPanelRoot.setVisibility(View.VISIBLE);
        }
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据1");
            data.add("测试数据3");
        }
        return data;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP &&
                event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mPanelRoot.getVisibility() == View.VISIBLE) {
                mPanelRoot.setVisibility(View.GONE);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
