package cn.wei.zslq.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ViewSwitcher;

import cn.wei.zslq.support.BaseActivity;
import cn.wei.library.widget.EmptyView;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.InformationBean;
import cn.wei.zslq.utils.HaoXinCallBack;
import cn.wei.zslq.utils.UrlHelpper;
import http.AppException;
import http.Request;
import http.RequestManager;
import http.Trace;

/**
 * Created by qinwei on 2015/10/30 23:39
 * email:qinwei_it@163.com
 */
public class WebViewActivity extends BaseActivity implements EmptyView.OnRetryListener {
    public static final String KEY_INFORMATION_ID = "key_information_id";
    private WebView mWebView;
    private String informationId;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(KEY_INFORMATION_ID, id);
        return intent;
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        initializeEmptyView();
    }

    @Override
    protected void initializeData() {
        setTitle("详情");
        informationId = getIntent().getStringExtra(KEY_INFORMATION_ID);
        mWebView = (WebView) findViewById(R.id.mWebView);
        initalizeInformationContentWebView();
        loadInformationFromServer();
    }

    private void initializeEmptyView() {
        if (findViewById(R.id.mEmptyView) != null) {
            mEmptyView = (EmptyView) findViewById(cn.wei.library.R.id.mEmptyView);
            mEmptyView.setOnRetryListener(this);
            mEmptyView.notifyDataChanged(EmptyView.State.ing);
            mViewSwitcher = (ViewSwitcher) findViewById(cn.wei.library.R.id.mViewSwitcher);
            load();
        }
    }

    @Override
    public void onRetry() {
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        load();
    }

    private void loadInformationFromServer() {
        Request request = new Request(UrlHelpper.loadInformationDetail(informationId));
        request.setCallback(new HaoXinCallBack<InformationBean>() {
            @Override
            public void onSuccess(InformationBean result) {
                showContent();
                setTitle(result.getTitle());
                mWebView.loadDataWithBaseURL("", result.getContentValue(), "text/html", "UTF-8", "");
            }

            @Override
            public void onFailure(AppException exception) {
                exception.printStackTrace();
                mEmptyView.notifyDataChanged(EmptyView.State.error);
            }
        });
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        RequestManager.getInstance().execute(toString(), request);
    }

    private void initalizeInformationContentWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        // mWebView.setSaveEnabled(true);
        // 添加js交互接口类，并起别名 imagelistner
        mWebView.addJavascriptInterface(new JavascriptInterface(), "imagelistner");
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); "
                + "for(var i=0;i<objs.length;i++)  " + "{" + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imagelistner.openImage(this.src);  " + "    }  " + "}" + "})()");
    }

    // js通信接口
    public static class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            Trace.d(img);
            if (img.indexOf(".gif") > 0)
                return;
        }
    }

    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }
}
