package http;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public class RequestTask extends AsyncTask<Void, Long, Object> {
    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... params) {
        Object o = request.callback.preRequest(request.url);
        if (o != null) {
            return o;
        }
        if (request.delayTime > 0) {
            try {
                Thread.sleep(request.delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return doRequest(0);
    }

    public Object doRequest(int retry) {
        try {

            HttpURLConnection connection = HttpURLConnectionUtil.execute(request, request.isEnableProgressUpdated() ? new OnProgressUpdatedListener() {
                @Override
                public void onProgressUpdated(long curPos, long contentLength) {
                    publishProgress(curPos, contentLength);
                }
            } : null);
            if (request.isEnableProgressUpdated()) {
                return request.callback.parse(connection, new OnProgressUpdatedListener() {
                    @Override
                    public void onProgressUpdated(long curPos, long contentLength) {
                        publishProgress(curPos, contentLength);
                    }
                });
            } else {
                return request.callback.parse(connection, null);
            }

        } catch (AppException e) {
            if (e.type == AppException.ErrorType.TIMEOUT) {
                if (retry < request.maxRetryCount)
                    retry++;
                doRequest(retry);
            }
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof AppException) {
            AppException e = (AppException) o;
            if (request.onGlobalExceptionListener != null) {
                if (!request.onGlobalExceptionListener.handleException(e)) {
                    request.callback.onFailure(e);
                }
            } else {
                request.callback.onFailure(e);
            }
        } else {
            request.callback.onSuccess(o);
        }
        request.callback.onCompleted();
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        request.callback.onProgressUpdated(values[0], values[1]);
    }
}
