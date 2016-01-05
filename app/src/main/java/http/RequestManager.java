package http;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public class RequestManager {
    private static RequestManager mInstance;
    private ExecutorService mExecutors;
    private LinkedHashMap<String, Request> requests;

    private RequestManager() {
        requests = new LinkedHashMap<>();
        mExecutors = Executors.newFixedThreadPool(5);
    }

    public static RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }

    public void execute(String tag, Request request) {
        request.setTag(tag);
        requests.put(tag, request);
        request.execute(mExecutors);
    }

    /**
     * @param tag
     */
    public void cancelRequest(String tag) {
        if (containsTag(tag))
            cancelRequest(tag, false);
    }

    public void cancelRequest(String tag, boolean force) {
        Trace.d("before cancel size=" + requests.size());
        Request request = requests.remove(tag);
        request.cancel(force);
        Trace.d("after cancel size=" + requests.size());
    }

    public void cancelRequests() {
        for (int i = 0; i < requests.size(); i++) {
            requests.get(i).cancel(false);
        }
        requests.clear();
    }

    public boolean containsTag(String tag) {
        return requests.containsKey(tag);
    }

    public void removeRequest(String tag) {
        cancelRequest(tag, true);
    }
}
