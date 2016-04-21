package cn.wei.zslq.utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import http.AbstractCallback;
import http.AppException;
import http.JsonParser;

/**
 * Created by qinwei on 2016/4/21 14:34
 * email:qinwei_it@163.com
 */
public abstract class GankIoCallback<T> extends AbstractCallback<T> {
    @Override
    protected T bindData(String content) throws IOException, AppException {
        try {
            Type type = getClass().getGenericSuperclass();
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            JSONObject obj = new JSONObject(content);
            boolean result = obj.getBoolean("error");
            if (result) {
                throw new AppException(AppException.ErrorType.MANUAL, "manual");
            } else {
                return JsonParser.deserializeFromJson(obj.opt("results").toString(), type);
            }
        } catch (JSONException e) {
            throw new AppException(AppException.ErrorType.PARSE_JSON, e.getMessage());
        }
    }


}
