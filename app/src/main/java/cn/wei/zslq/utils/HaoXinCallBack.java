package cn.wei.zslq.utils;


import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import http.AbstractCallback;
import http.AppException;
import http.JsonParser;
import http.Trace;

public abstract class HaoXinCallBack<T> extends AbstractCallback<T> {
    private Type type;


    public HaoXinCallBack() {
        type = getClass().getGenericSuperclass();
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    @Override
    public T bindData(String content) throws AppException {
        Trace.i(content);
        try {
            JSONObject jsonObject = new JSONObject(content);
            int responseCode = jsonObject.getInt("code");
            if (responseCode == 0) {
                String contentJson = jsonObject.opt("results").toString();
                return JsonParser.deserializeFromJson(contentJson, type);
            }
            throw new AppException(responseCode, jsonObject.getString("error") + "");
        } catch (JSONException e) {
            throw new AppException(AppException.ErrorType.PARSE_JSON, "ParseJsonException:" + e.getMessage());
        }catch(JsonSyntaxException e){
            throw new AppException(AppException.ErrorType.PARSE_JSON, "JsonSyntaxException:" + e.getMessage());
        }
    }
}
