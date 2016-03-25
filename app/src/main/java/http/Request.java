package http;

import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public class Request {


    public RequestTask task;
    public int delayTime = -1;

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    public String url;//请求url
    public String postContent;//post提交的数据
    public String filePath;//单文件上传
    public ArrayList<FileEntity> fileEntities;//多文件上传
    private HashMap<String, String> urlParameters;//请求参数
    public RequestMethod method;
    public ICallback callback;
    public Map<String, String> headers;
    private boolean enableProgressUpdated;
    public OnGlobalExceptionListener onGlobalExceptionListener;//全局异常处理
    public OnCompletedListener completedListener;//请求结束时回掉
    public volatile boolean isCancelled;
    public int maxRetryCount = 3;
    public String tag;

    public Request(String url, RequestMethod method) {
        this.method = method;
        this.url = url;
    }

    public Request(String url) {
        this.method = RequestMethod.GET;
        this.url = url;
    }


    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public void addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
    }

    public void put(String key, String value) {
        if (urlParameters == null) {
            urlParameters = new HashMap<>();
        }
        urlParameters.put(key, value);
    }

    public void put(String key, int value) {
        put(key, value + "");
    }

    public HashMap<String, String> getUrlParameters() {
        return urlParameters;
    }

    public void setOnGlobalExceptionListener(OnGlobalExceptionListener listener) {
        this.onGlobalExceptionListener = listener;
    }

    public void setEnableProgressUpdated(boolean enableProgressUpdated) {
        this.enableProgressUpdated = enableProgressUpdated;
    }

    public boolean isEnableProgressUpdated() {
        return enableProgressUpdated;
    }

    public void checkIfCancelled() throws AppException {
        if (isCancelled) {
            throw new AppException(AppException.ErrorType.CANCEL, "the request has been cancelled");
        }
    }

    public void execute(ExecutorService mExecutors) {
        task = new RequestTask(this);
        if (Build.VERSION.SDK_INT > 11) {
            task.executeOnExecutor(mExecutors);
        } else {
            task.execute();
        }
    }

    public void cancel(boolean force) {
        isCancelled = true;
        callback.cancel();
        if (force && task != null) {
            task.cancel(true);
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setOnCompletedListener(OnCompletedListener listener) {
        this.completedListener = listener;
    }
}
