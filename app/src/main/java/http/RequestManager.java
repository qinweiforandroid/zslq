package http;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.wei.library.utils.Trace;

/**
 * Created by qinwei on 2015/10/6.
 * email:qinwei_it@163.com
 */
public class RequestManager implements OnCompletedListener {
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
        Trace.d(HttpContants.TAG_HTTP, "add to RequestQueue:tag=" + tag);
        requests.put(tag, request);
        request.setOnCompletedListener(this);
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
        Trace.d(HttpContants.TAG_HTTP, "before cancel Queue size=" + requests.size());
        Request request = requests.remove(tag);
        if (request != null) {
            request.cancel(force);
        }
        Trace.d(HttpContants.TAG_HTTP, "after cancel Queue size=" + requests.size());
    }

    public void cancelRequests() {
        Trace.e(HttpContants.TAG_HTTP,"cancelAllRequests");
        if (requests == null) {
            return;
        }
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i) != null)
                Trace.e(HttpContants.TAG_HTTP, "tag:"+requests.get(i).tag);
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

    @Override
    public void onCompleted(String tag) {
        Trace.d(HttpContants.TAG_HTTP, "remove from RequestQueue tag=" + tag);
        if (containsTag(tag))
            requests.remove(tag);
    }
}
