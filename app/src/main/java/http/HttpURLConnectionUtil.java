package http;

import android.webkit.URLUtil;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.wei.library.utils.Trace;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public class HttpURLConnectionUtil {
    private static final int CONNECT_TIME_OUT = 15 * 1000;
    private static final int READ_TIME_OUT = 15 * 1000;

    public static HttpURLConnection execute(Request request, OnProgressUpdatedListener listener) throws AppException {
        if (!URLUtil.isNetworkUrl(request.url)) {
            throw new AppException(AppException.ErrorType.URL_NOT_VALID, "the url:" + request.url + " is not valid");
        }
        Trace.d(HttpContants.TAG_HTTP, "request url:" + request.url);
        Trace.d(HttpContants.TAG_HTTP, "request mehtod:" + request.method.name());
        switch (request.method) {
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
                return post(request, listener);
        }
        return null;
    }

    public static HttpURLConnection get(Request request) throws AppException {
        request.checkIfCancelled();
        HttpURLConnection connection = null;
        try {
            if (request.getUrlParameters() != null) {
                if (request.url.contains("?")) {
                    request.url += getParams(request.getUrlParameters());
                } else {
                    request.url += "?" + getParams(request.getUrlParameters());
                }
            }

            connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(Request.RequestMethod.GET.name());//提交
            connection.setConnectTimeout(CONNECT_TIME_OUT);
            connection.setReadTimeout(READ_TIME_OUT);
            addHeaders(connection, request.headers);
            request.checkIfCancelled();
            return connection;
        } catch (InterruptedIOException e) {
            throw new AppException(AppException.ErrorType.TIMEOUT, e.getMessage());
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO, e.getMessage());
        }
    }

    public static HttpURLConnection post(Request request, OnProgressUpdatedListener listener) throws AppException {
        request.checkIfCancelled();
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(Request.RequestMethod.POST.name());
            connection.setConnectTimeout(CONNECT_TIME_OUT);
            connection.setReadTimeout(READ_TIME_OUT);
            connection.setDoOutput(true);

            addHeaders(connection, request.headers);

            OutputStream os = connection.getOutputStream();
            if (request.filePath != null) {
                UploadUtil.upload(os, request.filePath);
                Trace.d(HttpContants.TAG_HTTP, Trace.getTraceInfo() + " uploadFile:" + request.filePath);
            } else if (request.fileEntities != null) {
                UploadUtil.upload(os, request.postContent, request.fileEntities, listener);
                Trace.d(HttpContants.TAG_HTTP, Trace.getTraceInfo() + " uploadFiles:" + request.fileEntities.size());
            } else if (request.getUrlParameters() != null) {
                String parameters = getParams(request.getUrlParameters());
                os.write(parameters.getBytes());
                Trace.d(HttpContants.TAG_HTTP, Trace.getTraceInfo() + " request commit:" + parameters);
            } else if (request.postContent != null) {
                Trace.d(HttpContants.TAG_HTTP, Trace.getTraceInfo() + " request commit:" + request.postContent);
                os.write(request.postContent.getBytes());//提交post的数据
            } else {
                Trace.e(HttpContants.TAG_HTTP, "ManualException the post request has no post content");
                throw new AppException(AppException.ErrorType.MANUAL, "the post request has no post content");
            }
            request.checkIfCancelled();
        } catch (InterruptedIOException e) {
            Trace.e(HttpContants.TAG_HTTP, "TimeoutException: " + e.getMessage());
            throw new AppException(AppException.ErrorType.TIMEOUT, e.getMessage());
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO, e.getMessage());
        }
        return connection;
    }

    private static void addHeaders(HttpURLConnection connection, Map<String, String> headers) {
        if (headers == null || headers.size() == 0) return;
        StringBuilder builder = new StringBuilder("request list_zone_talk_header：\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.append("key=" + entry.getKey() + ",value=" + entry.getValue() + "\n");
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
        Trace.d(HttpContants.TAG_HTTP, builder.toString());
    }

    private static String getParams(HashMap<String, String> requestParams) {
        if (requestParams == null) {
            return null;
        }
        String params = "";
        int i = 0;
        StringBuilder builder = new StringBuilder("request params：\n");
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            params += entry.getKey() + "=" + entry.getValue();
            builder.append("key=" + entry.getKey() + ",value=" + entry.getValue() + "\n");
            if (i != requestParams.size() - 1) {
                params += "&";
            }
            i++;
        }
        Trace.d(HttpContants.TAG_HTTP, builder.toString());
        return params;
    }
}
