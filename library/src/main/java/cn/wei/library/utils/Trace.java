package cn.wei.library.utils;

import android.util.Log;

public class Trace {
    private static final String TAG = "log";

    public static boolean model = true;

    public static void v(String msg) {
        if (model) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (model) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (model) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (model) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (model) {
            Log.e(TAG, msg);
        }
    }


    public static String getTraceInfo() {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        sb.append("class: ").append(stacks[1].getClassName())
                .append("; method: ").append(stacks[1].getMethodName())
                .append("; number: ").append(stacks[1].getLineNumber());

        return sb.toString();
    }

    public static void v(String tag, String msg) {
        if (model) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (model) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (model) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (model) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (model) {
            Log.e(tag, msg);
        }
    }
}
