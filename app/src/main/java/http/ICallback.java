package http;

import java.net.HttpURLConnection;

/**
 * http 请求过程中的回调接口
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public interface ICallback<T> {
    /**
     * 请求任务执行成功被调用
     * 你可以在此方法进行修改UI(UPDATE UI)
     * @param result
     */
    void onSuccess(T result);

    /**
     * 请求任务失败
     * @param e
     */
    void onFailure(AppException e);

    /**
     * work in subThread
     * 在http请求之前被调用
     * 如果返回值不等于null 将直接调用{@code onSuccess()}
     * @param url
     * @return
     */
    T preRequest(String url);

    T parse(HttpURLConnection connection, OnProgressUpdatedListener listener) throws AppException;

    /**
     * work in subThread
     * 在http请求之后被调用
     * 你可以在此方法进行缓存http返回的数据(save to db or sdcard)
     * @param t
     * @return
     */
    T postRequest(T t);

    /**
     * it work in mainThread
     * 进度更新
     * @param curPos
     * @param contentLength
     */
    void onProgressUpdated(long curPos, long contentLength);

    /**
     * 取消请求任务
     */
    void cancel();
}
