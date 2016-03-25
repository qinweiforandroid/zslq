package http;

/**
 * 请求结束回掉/请求成功，请求失败，手动取消
 * Created by qinwei on 2016/3/22 18:28
 * email:qinwei_it@163.com
 */
public interface OnCompletedListener {
    void onCompleted(String tag);
}
