package http;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public interface OnGlobalExceptionListener {
    boolean handleException(AppException e);
}
