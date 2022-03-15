package my.flamemad.spider.control;


import my.flamemad.spider.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class MemoryRequestQueue implements RequestQueue{
    private final BlockingQueue<Request> requests;
    private final BlockingQueue<Request> retryRequests;

    public MemoryRequestQueue() {
        requests = new LinkedTransferQueue<>();
        retryRequests = new LinkedTransferQueue<>();
    }

    public Request get() {
        return requests.poll();
    }

    public boolean set(Request request) {
        if (request != null) {
            return requests.offer(request);
        }else {
            return false;
        }
    }

    @Override
    public Request getRetryRequest() {
        return retryRequests.poll();
    }

    @Override
    public boolean setRetryRequest(Request request) {
        return retryRequests.offer(request);
    }

    @Override
    public boolean isEmpty() {
        return requests.isEmpty();
    }

}
