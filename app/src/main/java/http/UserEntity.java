package http;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public class UserEntity implements IEntity {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    private String id;
    private String name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public void readFromJson(JsonReader reader) throws AppException {
        try {
            String node = null;
            while (reader.hasNext()) {
                node = reader.nextName();
                switch (node) {
                    case ID:
                        id = reader.nextString();
                        break;
                    case NAME:
                        name = reader.nextString();
                        break;
                    case EMAIL:
                        email = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO, e.getMessage());
        }
    }
}
