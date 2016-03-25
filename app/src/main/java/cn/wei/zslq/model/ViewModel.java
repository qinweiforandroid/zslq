package cn.wei.zslq.model;

import android.content.Context;
import android.content.DialogInterface;

import cn.wei.library.utils.NetworkUtils;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.controller.OnProgressUpdatedListener;
import cn.wei.zslq.utils.ProgressDialogUtils;
import cn.wei.zslq.utils.ToastUtil;
import http.AppException;
import http.OnGlobalExceptionListener;
import http.RequestManager;

/**
 * Created by qinwei on 2015/12/18 16:10
 * email:qinwei_it@163.com
 */
public abstract class ViewModel implements OnGlobalExceptionListener {
    protected Context context;
    protected Controller controller;
    private OnProgressUpdatedListener listener;

    public ViewModel(Context context) {
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

    protected void onResponseSuccess(String tag) {
        if (controller != null) {
            controller.onSuccess(tag);
        }
    }

    protected void onResponseError(String tag, int errorCode, String errorMsg) {
        if (controller != null) {
            controller.onFailure(tag, errorCode, errorMsg);
        }
    }


    public void showToast(String message) {
        ToastUtil.show(context, message);
    }

    public void showLong(String message) {
        ToastUtil.showLong(context, message);
    }

    public void showProgress(String message) {
//        NativeProgressDialogUtils.showProgressDialog(context, message);
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
//        NativeProgressDialogUtils.closeProgressDialog();
        ProgressDialogUtils.closeProgressDialog();
    }

    public boolean checkNetworkConnected() {
        if (NetworkUtils.isNetworkConnected(context)) {
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
