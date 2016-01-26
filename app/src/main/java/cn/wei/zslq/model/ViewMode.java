package cn.wei.zslq.model;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.controller.OnProgressUpdatedListener;
import cn.wei.zslq.utils.ProgressDialogUtils;
import http.AppException;
import http.OnGlobalExceptionListener;
import http.RequestManager;

/**
 * Created by qinwei on 2015/12/18 16:10
 * email:qinwei_it@163.com
 */
public abstract class ViewMode implements OnGlobalExceptionListener {
    protected Context context;
    private Toast mToast;
    protected Controller controller;
    private OnProgressUpdatedListener listener;

    public ViewMode(Context context) {
        this.context = context;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setOnProgressUpdatedListener(OnProgressUpdatedListener listener) {
        this.listener = listener;
    }

    protected void onProgressUpdated(String tag, long curPos, long contentLength) {
        if (listener != null) {
            listener.onProgressUpdated(tag, curPos, contentLength);
        }
    }

    protected void onRequestSuccess(String tag) {
        if (controller != null) {
            controller.onSuccess(tag);
            RequestManager.getInstance().removeRequest(tag);
        }
    }

    protected void onRequestError(String tag, int errorCode, String errorMsg) {
        if (controller != null) {
            controller.onFailure(tag, errorCode, errorMsg);
            RequestManager.getInstance().removeRequest(tag);
        }
    }

    protected boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public void showToast(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }

    public void showLong(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(message);
        mToast.show();
    }

    public void showProgress(String message) {
        ProgressDialogUtils.showProgressDialog(context, message);
    }

    public void showProgressCanCancel(final String tag, String message) {
        ProgressDialogUtils.showProgressDialog(context, message, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                RequestManager.getInstance().cancelRequest(tag);
            }
        });
    }

    public void closeProgress() {
        ProgressDialogUtils.closeProgressDialog();
    }

    public boolean checkNetworkConnected() {
        if (isNetworkConnected(context)) {
            return true;
        } else {
            showToast("请检查网络连接!");
            return false;
        }
    }

    public boolean canRequest(String tag) {
        return !RequestManager.getInstance().containsTag(tag);
    }

    @Override
    public boolean handleException(AppException e) {
        switch (e.type) {
            case IO:
            case PARSE_JSON:
            case SERVER:
                if (e.responseCode == 403) {
//                    FIXME go login
                } else {
                    showToast("网络不给力啊");
                }
                break;
            case TIMEOUT:
                showToast("连接超时，请重试");
                break;
        }
        return true;
    }
}
