
package cn.wei.zslq.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import cn.wei.zslq.R;
import cn.wei.zslq.widget.ChatPlugin;

/**
 * @author Nana
 * @Description 主消息界面
 * @date 2014-7-15
 * <p/>
 */
public class MessageActivity extends Activity implements ChatPlugin.OnChatPluginListener {

    private ChatPlugin mChatPlugin;
    private SwitchInputMethodReceiver receiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chat);

        receiver = new SwitchInputMethodReceiver();
        filter = new IntentFilter();
        filter.addAction("android.intent.action.INPUT_METHOD_CHANGED");
        initView();
    }

    @Override
    protected void onStart() {
        registerReceiver(receiver, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }

    private void initView() {
        // 绑定布局资源(注意放所有资源初始化之前)
        mChatPlugin = (ChatPlugin) findViewById(R.id.mChatPlugin);
        mChatPlugin.init(this, findViewById(R.id.root));
        mChatPlugin.setOnChatPluginListener(this);
    }


    @Override
    public void onTextSend(String content) {
        Toast.makeText(this, "message:" + content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVoiceSend(String path) {

    }

    private class SwitchInputMethodReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.INPUT_METHOD_CHANGED")) {
                mChatPlugin.doInputMethodChanged();
            }
        }
    }
}
