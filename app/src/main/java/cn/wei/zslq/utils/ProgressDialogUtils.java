package cn.wei.zslq.utils;

import android.content.Context;
import android.content.DialogInterface;

public class ProgressDialogUtils {
    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String message, DialogInterface.OnCancelListener listener) {
        progressDialog = ProgressDialog.show(context, message);
        progressDialog.setCanceledOnTouchOutside(false);
        if (listener != null) {
            progressDialog.setOnCancelListener(listener);
        }else{
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    public static void showProgressDialog(Context context, DialogInterface.OnCancelListener listener) {
        showProgressDialog(context, null, listener);
    }

    public static void showProgressDialog(Context context, String message) {
        showProgressDialog(context, message, null);
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, null, null);
    }

    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
    }

}
