package cn.wei.zslq.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by qinwei on 2016/3/17 16:35
 * email:qinwei_it@163.com
 */
public class ToastUtil {
    private static Toast toast;

    public static void show(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();

    }

    public static void showLong(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();
    }
}
