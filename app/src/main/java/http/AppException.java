package http;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public class AppException extends Exception {
    public int responseCode;
    public String responseMessage;
    public ErrorType type;

    public enum ErrorType {
        PARSE_JSON, TIMEOUT, SERVER, IO, URL_NOT_VALID, CANCEL, UPLOAD, MANUAL, FILE_NOT_FOUND
    }

    public AppException(int responseCode, String responseMessage) {
        super(responseMessage);
        this.type = ErrorType.SERVER;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public AppException(ErrorType type, String detailMessage) {
        super(type.name() + ":" + detailMessage);
        this.responseMessage = detailMessage;
        this.type = type;
        switch (type) {
            case PARSE_JSON:
                this.responseCode = HttpContants.JSON_PARSE_ERROR;
                break;
            case URL_NOT_VALID:
                this.responseCode = HttpContants.URL_NOT_VALID;
                break;
        }

    }
}
