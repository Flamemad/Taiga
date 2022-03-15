package my.flamemad.spider.control;

import my.flamemad.spider.Request;

public interface RequestQueue {
    Request get();
    boolean set(Request request);
    Request getRetryRequest();
    boolean setRetryRequest(Request request);
    boolean isEmpty();
}
