package http;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonParser {
    private static Gson gson = new Gson();

    public static <T> T deserializeFromJson(String json, Class<T> clazz) {

        return gson.fromJson(json, clazz);
    }

    public static <T> T deserializeFromJson(String json, Type type) {
//		new JSONObject().opt()
        return gson.fromJson(json, type);
    }

    public static String serializeToJson(Object obj) {
        return gson.toJson(obj);
    }
}
