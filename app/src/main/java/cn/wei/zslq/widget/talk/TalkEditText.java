package cn.wei.zslq.widget.talk;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wei.zslq.R;


public class TalkEditText extends LinearLayout implements OnClickListener, TextWatcher {

    private EditText mTalkEdt;
    private TextView mTalkEditorSendLabel;
    private Context context;
    private InputMethodManager inputMethodManager;
    private ImageView mTalkEditorEmoImg;
    private OnTalkPlugListener listener;

    public interface OnTalkPlugListener {
        void onSendText(CharSequence text);
    }

    public void setOnTalkPlugListener(OnTalkPlugListener listener) {
        this.listener = listener;
    }

    public TalkEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    public TalkEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public TalkEditText(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.widget_im_talk_editor_view, this);
        mTalkEditorEmoImg = (ImageView) findViewById(R.id.mTalkEditorEmoImg);
        mTalkEditorSendLabel = (TextView) findViewById(R.id.mTalkEditorSendLabel);
        mTalkEditorSendLabel.setOnClickListener(this);
        mTalkEditorEmoImg.setOnClickListener(this);
        mTalkEdt = (EditText) findViewById(R.id.mTalkEdt);
        mTalkEdt.addTextChangedListener(this);
        mTalkEditorSendLabel.setEnabled(false);
        inputMethodManager = (InputMethodManager) mTalkEdt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTalkEditorSendLabel:
                if (mTalkEditorSendLabel.isEnabled() && listener != null&& !TextUtils.isEmpty(mTalkEdt.getText().toString().trim())) {
                    listener.onSendText(mTalkEdt.getText());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            mTalkEditorSendLabel.setEnabled(false);
        } else {
            mTalkEditorSendLabel.setEnabled(true);
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }

    public void showSoftInput() {
        mTalkEdt.requestFocus();
        inputMethodManager.showSoftInput(mTalkEdt, 0);
    }

    public void hideInputSoft() {
        this.setVisibility(View.GONE);
        inputMethodManager.hideSoftInputFromWindow(mTalkEdt.getWindowToken(), 0); // 强制隐藏键盘
    }

    public void setHintText(String text) {
        mTalkEdt.setHint(text);
    }

    public void setText(String text) {
        mTalkEdt.setText(text);
    }

    public String getText() {
        return mTalkEdt.getText().toString();
    }

}
