package http;

import java.io.IOException;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public abstract class XmlCallback<T> extends AbstractCallback<T> {
    @Override
    protected T bindData(String content) throws IOException {
        return null;
    }
}
