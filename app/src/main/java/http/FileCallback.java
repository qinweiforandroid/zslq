package http;

import java.io.IOException;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public abstract class FileCallback extends  AbstractCallback<String> {
    @Override
    protected String bindData(String content) throws IOException {
        Trace.e(content);
        return content;
    }
}
