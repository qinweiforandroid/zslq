package http;

import com.google.gson.stream.JsonReader;

/**
 * Created by qinwei on 2015/10/7.
 * email:qinwei_it@163.com
 */
public interface IEntity {
    void readFromJson(JsonReader reader) throws AppException;
}
