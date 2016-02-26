package http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import cn.wei.library.utils.Trace;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public abstract class AbstractCallback<T> implements ICallback<T> {
    protected String path;
    private boolean isCancelled;

    @Override
    public T parse(HttpURLConnection connection, OnProgressUpdatedListener listener) throws AppException {
        checkIfCancelled();
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                if (path != null) {
                    Trace.i(HttpContants.TAG_HTTP, path + " is downloading ");
                    FileOutputStream fos = new FileOutputStream(new File(
                            path));
                    InputStream is = connection.getInputStream();
                    byte[] buffer = new byte[2048];
                    int len = -1;
                    long contentLength = connection.getContentLength();
                    long curPos = 0;
                    while ((len = is.read(buffer)) != -1) {
                        checkIfCancelled();
                        fos.write(buffer, 0, len);
                        curPos += len;
                        if (listener != null) {
                            listener.onProgressUpdated(curPos, contentLength);
                        }
                    }
                    Trace.i(HttpContants.TAG_HTTP, path + " is completed ");
                    is.close();
                    fos.flush();
                    fos.close();
                    T t = bindData(path);
                    return postRequest(t);
                } else {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    InputStream is = null;
                    String contentEncoding = connection.getContentEncoding();
                    if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
                        is = new GZIPInputStream(connection.getInputStream());
                    } else if (contentEncoding != null && contentEncoding.equalsIgnoreCase("deflate")) {
                        is = new InflaterInputStream(connection.getInputStream());
                    } else {
                        is = connection.getInputStream();
                    }
                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        checkIfCancelled();
                        out.write(buffer, 0, len);
                    }
                    is.close();
                    out.flush();
                    out.close();
                    String responseInfo = new String(out.toByteArray(), "utf-8");
                    Trace.d(HttpContants.TAG_HTTP, "server response :" + responseInfo);
                    T t = bindData(responseInfo);
                    return postRequest(t);
                }
            } else {
                Trace.e(HttpContants.TAG_HTTP, "server error responseCode: " + responseCode + ",message: " + connection.getResponseMessage());
//                    connection.getErrorStream();
//                    connection.getResponseMessage();
                throw new AppException(responseCode, connection.getResponseMessage());
            }
        } catch (InterruptedIOException e) {
//            FIXME 验证 此处 会不会抛出 timeoutException
            Trace.e(HttpContants.TAG_HTTP, "TimeoutException: " + e.getMessage());
            throw new AppException(AppException.ErrorType.TIMEOUT, e.getMessage());
        } catch (IOException e) {
            Trace.e(HttpContants.TAG_HTTP, "IOException: " + e.getMessage());
            throw new AppException(AppException.ErrorType.IO, e.getMessage());
        }
    }


    public AbstractCallback<T> setCachePath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    public void checkIfCancelled() throws AppException {
        if (isCancelled) {
            Trace.e(HttpContants.TAG_HTTP, "CancelException: the request has been cancelled");
            throw new AppException(AppException.ErrorType.CANCEL, "the request has been cancelled");
        }
    }

    @Override
    public void onProgressUpdated(long curPos, long contentLength) {

    }

    protected abstract T bindData(String content) throws IOException, AppException;

    @Override
    public T preRequest(String url) {
        return null;
    }

    @Override
    public T postRequest(T t) {

        return t;
    }

    @Override
    public void onCompleted() {
    }
}
