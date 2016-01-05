package http;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public abstract class JsonReaderCallback<T extends IEntity> extends AbstractCallback<T> {

    public JsonReaderCallback(String tempPath) {
        super.path = tempPath;
    }

    @Override
    public T bindData(String path) throws AppException {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            T t = ((Class<T>) type).newInstance();
            FileReader fileReader = new FileReader(path);
            JsonReader reader = new JsonReader(fileReader);
            reader.beginObject();
            t.readFromJson(reader);
            reader.endObject();
            reader.close();
            fileReader.close();
            return t;
        } catch (JsonSyntaxException e) {
            throw new AppException(AppException.ErrorType.PARSE_JSON, e.getMessage());
        } catch (InstantiationException e) {
            throw new AppException(AppException.ErrorType.PARSE_JSON, e.getMessage());
        } catch (IllegalAccessException e) {
            throw new AppException(AppException.ErrorType.PARSE_JSON, e.getMessage());
        } catch (FileNotFoundException e) {
            throw new AppException(AppException.ErrorType.FILE_NOT_FOUND, e.getMessage());
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO, e.getMessage());
        } finally {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
