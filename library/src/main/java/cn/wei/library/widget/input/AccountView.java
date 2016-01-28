package cn.wei.library.widget.input;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wei.library.R;

public class AccountView extends LinearLayout implements TextWatcher, OnClickListener, OnFocusChangeListener {

    private EditText mAccountContentEdt;
    private ImageView mAccountClearImg;
    private View view;
    private TextView mAccountLabel;
    private OnTextChangedListener listener;

    public interface OnTextChangedListener {
        void onTextChanged(CharSequence c);
    }

    public void setOnTextChangedListener(OnTextChangedListener listener) {
        this.listener = listener;
    }

    public AccountView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context, attrs);
    }

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs);
    }

    private void initializeView(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_input_account, this);
        mAccountLabel = (TextView) findViewById(R.id.mAccountLabel);
        mAccountContentEdt = (EditText) findViewById(R.id.mAccountContentEdt);
        mAccountClearImg = (ImageView) findViewById(R.id.mAccountClearImg);
        mAccountClearImg.setOnClickListener(this);
        mAccountContentEdt.setOnFocusChangeListener(this);
        mAccountContentEdt.addTextChangedListener(this);
        mAccountClearImg.setVisibility(View.GONE);
        parseAttr(context, attrs);
        if (mAccountLabel.getText().toString().isEmpty()) {
            mAccountLabel.setVisibility(View.GONE);
        }
    }


    public void initializeData(View view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (listener != null) {
            listener.onTextChanged(s);
        }
        if (TextUtils.isEmpty(s)) {
            if (view != null)
                view.setEnabled(false);
            mAccountClearImg.setVisibility(View.INVISIBLE);
        } else {
            if (view != null)
                view.setEnabled(true);
            mAccountClearImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        mAccountContentEdt.setText(null);
    }

    public CharSequence getText() {
        return mAccountContentEdt.getText();
    }

    public void setHint(String hintText) {
        mAccountContentEdt.setHint(hintText);
    }

    public void setText(String string) {
        mAccountContentEdt.setText(string);
        Editable etext = mAccountContentEdt.getText();
        Selection.setSelection(etext, etext.length());
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void parseAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.accountview);
        if (a.hasValue(R.styleable.accountview_hint)) {
            mAccountContentEdt.setHint(a.getString(R.styleable.accountview_hint));
        }
        if (a.hasValue(R.styleable.accountview_android_inputType)) {
            mAccountContentEdt.setInputType(a.getInt(R.styleable.accountview_android_inputType, InputType.TYPE_CLASS_TEXT));
        }
        if (a.hasValue(R.styleable.accountview_android_gravity)) {
            mAccountContentEdt.setGravity(a.getInt(R.styleable.accountview_android_gravity, Gravity.LEFT));
        }
        if (a.hasValue(R.styleable.accountview_android_minEms)) {
            mAccountContentEdt.setMinEms(a.getInt(R.styleable.accountview_android_minEms, -1));
        }
        if (a.hasValue(R.styleable.accountview_android_maxEms)) {
            mAccountContentEdt.setMaxEms(a.getInt(R.styleable.accountview_android_maxEms, Integer.MAX_VALUE));
        }
        if (a.hasValue(R.styleable.accountview_android_maxLength)) {
            mAccountContentEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(a.getInt(R.styleable.accountview_android_maxLength, Integer.MAX_VALUE))});
        }
        if (a.hasValue(R.styleable.accountview_text)) {
            mAccountContentEdt.setText(a.getString(R.styleable.accountview_text));
        }
        if (a.hasValue(R.styleable.accountview_android_singleLine)) {
            mAccountContentEdt.setSingleLine(a.getBoolean(R.styleable.accountview_android_singleLine, true));
        }

        if (a.hasValue(R.styleable.accountview_label)) {
            mAccountLabel.setText(a.getString(R.styleable.accountview_label));
        }
        if (a.hasValue(R.styleable.accountview_leftIcon)) {
            int icon = R.styleable.accountview_leftIcon;
            mAccountLabel.setCompoundDrawablesWithIntrinsicBounds(a.getDrawable(icon), null, null, null);
        }

        if(a.hasValue(R.styleable.accountview_bg_edit)){
            mAccountContentEdt.setBackgroundColor(a.getInt(R.styleable.accountview_bg_edit,R.color.white));
        }

        a.recycle();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && !TextUtils.isEmpty(mAccountContentEdt.getText().toString())) {
            mAccountClearImg.setVisibility(View.VISIBLE);
        } else {
            mAccountClearImg.setVisibility(View.GONE);
        }
    }
}
