package cn.wei.zslq.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.wei.zslq.R;
import cn.wei.zslq.utils.Constants;
import cn.wei.zslq.utils.PrefsAccessor;
import http.Trace;

/**
 * Created by qinwei on 2015/12/21 10:57
 * email:qinwei_it@163.com
 */
public class ChatPlugin extends LinearLayout implements View.OnClickListener, TextWatcher, View.OnKeyListener {
    private InputMethodManager inputManager;
    private String currentInputMethod;
    private TextView mChatPluginSendLabel;
    private Button mChatPluginRecordBtn;
    private ImageView mChatPluginVoiceBtn;
    private EditText mChatPluginMsgContentEdt;
    private ImageView mChatPluginKeyboardImg;
    private ImageView mChatPluginToolImg;
    private ImageView mChatPluginEmoImg;
    private View mChatPluginToolContainer;
    private LinearLayout mChatEmoContainer;
    private Activity activity;
    private View root;
    private OnChatPluginListener listener;
    //键盘布局相关参数
    int rootBottom = Integer.MIN_VALUE, keyboardHeight = 0;

    public ChatPlugin(Context context) {
        super(context);
        initializeView();

    }


    public interface OnChatPluginListener {
        void onTextSend(String content);

        void onVoiceSend(String path);
    }

    public void setOnChatPluginListener(OnChatPluginListener listener) {
        this.listener = listener;
    }

    public ChatPlugin(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public ChatPlugin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    private void initializeView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_chat_plugin, this);
    }

    public void init(Activity activity, View root) {
        this.activity = activity;
        this.root = root;
        initSoftInputMethod(activity);
        initView(root);
    }

    private void initSoftInputMethod(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        currentInputMethod = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        keyboardHeight = PrefsAccessor.getInstance(getContext()).getInt(currentInputMethod, 0);
        Trace.d("keyboardHeight:" + keyboardHeight);
    }

    /**
     * @Description 初始化界面控件
     * 有点庞大 todo
     */
    private void initView(View root) {
        // 界面底部输入框布局
        mChatPluginSendLabel = (TextView) this.findViewById(R.id.mChatPluginSendLabel);
        mChatPluginVoiceBtn = (ImageView) this.findViewById(R.id.mChatPluginVoiceBtn);
        mChatPluginRecordBtn = (Button) this.findViewById(R.id.mChatPluginRecordBtn);
        mChatPluginMsgContentEdt = (EditText) this.findViewById(R.id.mChatPluginMsgContentEdt);
        mChatPluginMsgContentEdt.setOnKeyListener(this);
        RelativeLayout.LayoutParams messageEdtParam = (RelativeLayout.LayoutParams) mChatPluginMsgContentEdt.getLayoutParams();
        messageEdtParam.addRule(RelativeLayout.LEFT_OF, R.id.mChatPluginEmoImg);
        messageEdtParam.addRule(RelativeLayout.RIGHT_OF, R.id.mChatPluginVoiceBtn);
        mChatPluginKeyboardImg = (ImageView) this.findViewById(R.id.mChatPluginKeyboardImg);
        mChatPluginToolImg = (ImageView) this.findViewById(R.id.mChatPluginToolImg);
        mChatPluginEmoImg = (ImageView) this.findViewById(R.id.mChatPluginEmoImg);
        mChatPluginMsgContentEdt.setOnFocusChangeListener(msgEditOnFocusChangeListener);
        mChatPluginMsgContentEdt.setOnClickListener(this);
        mChatPluginMsgContentEdt.addTextChangedListener(this);
        mChatPluginToolImg.setOnClickListener(this);
        mChatPluginEmoImg.setOnClickListener(this);
        mChatPluginKeyboardImg.setOnClickListener(this);
        mChatPluginVoiceBtn.setOnClickListener(this);
        mChatPluginSendLabel.setOnClickListener(this);

        mChatPluginToolContainer = findViewById(R.id.mChatPluginToolContainer);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mChatPluginToolContainer.getLayoutParams();
        if (keyboardHeight > 0) {
            params.height = keyboardHeight;
            mChatPluginToolContainer.setLayoutParams(params);
        }
        mChatEmoContainer = (LinearLayout) findViewById(R.id.mChatEmoContainer);
        RelativeLayout.LayoutParams paramEmo = (RelativeLayout.LayoutParams) mChatEmoContainer.getLayoutParams();
        if (keyboardHeight > 0) {
            paramEmo.height = keyboardHeight;
            mChatEmoContainer.setLayoutParams(paramEmo);
        }
        root.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            root.getGlobalVisibleRect(r);
            // 进入Activity时会布局，第一次调用onGlobalLayout，先记录开始软键盘没有弹出时底部的位置
            if (rootBottom == Integer.MIN_VALUE) {
                rootBottom = r.bottom;
                return;
            }
            // adjustResize，软键盘弹出后高度会变小
            if (r.bottom < rootBottom) {
                //按照键盘高度设置表情框和发送图片按钮框的高度
                keyboardHeight = rootBottom - r.bottom;
                PrefsAccessor.getInstance(getContext()).saveInt(currentInputMethod, keyboardHeight);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mChatPluginToolContainer.getLayoutParams();
                params.height = keyboardHeight;
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) mChatEmoContainer.getLayoutParams();
                params1.height = keyboardHeight;
            }
        }
    };
    private View.OnFocusChangeListener msgEditOnFocusChangeListener = new android.view.View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                if (keyboardHeight == 0) {
                    mChatPluginToolContainer.setVisibility(View.GONE);
                    mChatEmoContainer.setVisibility(View.GONE);
                } else {
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                    if (mChatPluginToolContainer.getVisibility() == View.GONE) {
                        mChatPluginToolContainer.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.mChatPluginToolImg: {
                mChatPluginRecordBtn.setVisibility(View.GONE);
                mChatPluginKeyboardImg.setVisibility(View.GONE);
                mChatPluginMsgContentEdt.setVisibility(View.VISIBLE);
                mChatPluginVoiceBtn.setVisibility(View.VISIBLE);
                mChatPluginEmoImg.setVisibility(View.VISIBLE);
                if (keyboardHeight != 0) {
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                }
                if (mChatPluginToolContainer.getVisibility() == View.VISIBLE) {
                    if (!mChatPluginMsgContentEdt.hasFocus()) {
                        mChatPluginMsgContentEdt.requestFocus();
                    }
                    inputManager.toggleSoftInputFromWindow(mChatPluginMsgContentEdt.getWindowToken(), 1, 0);
                    if (keyboardHeight == 0) {
                        mChatPluginToolContainer.setVisibility(View.GONE);
                    }
                } else if (mChatPluginToolContainer.getVisibility() == View.GONE) {
                    mChatPluginToolContainer.setVisibility(View.VISIBLE);
                    inputManager.hideSoftInputFromWindow(mChatPluginMsgContentEdt.getWindowToken(), 0);
                }
                if (null != mChatEmoContainer
                        && mChatEmoContainer.getVisibility() == View.VISIBLE) {
                    mChatEmoContainer.setVisibility(View.GONE);
                }

            }
            break;
            case R.id.mChatPluginEmoImg: {
                /**yingmu 调整成键盘输出*/
                mChatPluginRecordBtn.setVisibility(View.GONE);
                mChatPluginKeyboardImg.setVisibility(View.GONE);
                mChatPluginMsgContentEdt.setVisibility(View.VISIBLE);
                mChatPluginVoiceBtn.setVisibility(View.VISIBLE);
                mChatPluginEmoImg.setVisibility(View.VISIBLE);
                /**end*/
                if (keyboardHeight != 0) {
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                }
                if (mChatEmoContainer.getVisibility() == View.VISIBLE) {
                    if (!mChatPluginMsgContentEdt.hasFocus()) {
                        mChatPluginMsgContentEdt.requestFocus();
                    }
                    inputManager.toggleSoftInputFromWindow(mChatPluginMsgContentEdt.getWindowToken(), 1, 0);
                    if (keyboardHeight == 0) {
                        mChatEmoContainer.setVisibility(View.GONE);
                    }
                } else if (mChatEmoContainer.getVisibility() == View.GONE) {
                    mChatEmoContainer.setVisibility(View.VISIBLE);
                    inputManager.hideSoftInputFromWindow(mChatPluginMsgContentEdt.getWindowToken(), 0);
                }
                if (mChatPluginToolContainer.getVisibility() == View.VISIBLE) {
                    mChatPluginToolContainer.setVisibility(View.GONE);
                }
            }
            break;
            case R.id.mChatPluginVoiceBtn: {
                inputManager.hideSoftInputFromWindow(mChatPluginMsgContentEdt.getWindowToken(), 0);
                mChatPluginMsgContentEdt.setVisibility(View.GONE);
                mChatPluginVoiceBtn.setVisibility(View.GONE);
                mChatPluginRecordBtn.setVisibility(View.VISIBLE);
                mChatPluginKeyboardImg.setVisibility(View.VISIBLE);
                mChatEmoContainer.setVisibility(View.GONE);
                mChatPluginToolContainer.setVisibility(View.GONE);
                mChatPluginToolImg.setVisibility(View.VISIBLE);
                mChatPluginSendLabel.setVisibility(View.GONE);
            }
            break;
            case R.id.mChatPluginKeyboardImg: {
                mChatPluginRecordBtn.setVisibility(View.GONE);
                mChatPluginKeyboardImg.setVisibility(View.GONE);
                mChatPluginMsgContentEdt.setVisibility(View.VISIBLE);
                mChatPluginVoiceBtn.setVisibility(View.VISIBLE);
                mChatPluginEmoImg.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(mChatPluginMsgContentEdt.getText().toString())) {
                    mChatPluginSendLabel.setVisibility(View.VISIBLE);
                } else {
                    mChatPluginToolImg.setVisibility(View.VISIBLE);
                }
            }
            break;
            case R.id.mChatPluginMsgContentEdt:
                if (mChatPluginToolContainer.getVisibility() == View.GONE) {
                    mChatPluginToolContainer.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.mChatPluginSendLabel:
                String message = mChatPluginMsgContentEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    listener.onTextSend(mChatPluginMsgContentEdt.getText().toString().trim());
                    mChatPluginMsgContentEdt.setText("");
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mChatPluginSendLabel.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) mChatPluginMsgContentEdt
                    .getLayoutParams();
            param.addRule(RelativeLayout.LEFT_OF, R.id.mChatPluginEmoImg);
            mChatPluginToolImg.setVisibility(View.GONE);
        } else {
            mChatPluginToolImg.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) mChatPluginMsgContentEdt
                    .getLayoutParams();
            param.addRule(RelativeLayout.LEFT_OF, R.id.mChatPluginEmoImg);
            mChatPluginSendLabel.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void actFinish() {
        inputManager.hideSoftInputFromWindow(mChatPluginMsgContentEdt.getWindowToken(), 0);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && (mChatPluginToolContainer.getVisibility() == View.VISIBLE || mChatEmoContainer.getVisibility() == View.VISIBLE)) {
            if (inputManager.isActive()) {
                inputManager.hideSoftInputFromWindow(mChatPluginMsgContentEdt.getWindowToken(), 0);
            }
            mChatPluginToolContainer.setVisibility(View.GONE);
            mChatEmoContainer.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    public void doInputMethodChanged() {
        currentInputMethod = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        PrefsAccessor.getInstance(getContext()).saveString(Constants.KEY_DEFAULT_INPUT_METHOD, currentInputMethod);
        int height = PrefsAccessor.getInstance(getContext()).getInt(currentInputMethod, 0);
        if (keyboardHeight != height) {
            keyboardHeight = height;
            mChatPluginToolContainer.setVisibility(View.GONE);
            mChatEmoContainer.setVisibility(View.GONE);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mChatPluginMsgContentEdt.requestFocus();
            if (keyboardHeight != 0 && mChatPluginToolContainer.getLayoutParams().height != keyboardHeight) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mChatPluginToolContainer.getLayoutParams();
                params.height = keyboardHeight;
            }
            if (keyboardHeight != 0 && mChatEmoContainer.getLayoutParams().height != keyboardHeight) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mChatEmoContainer.getLayoutParams();
                params.height = keyboardHeight;
            }
        } else {
            mChatPluginToolContainer.setVisibility(View.VISIBLE);
            mChatEmoContainer.setVisibility(View.VISIBLE);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            mChatPluginMsgContentEdt.requestFocus();
        }
    }
}
