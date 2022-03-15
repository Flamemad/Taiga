package my.flamemad.spider.control;

import my.flamemad.spider.Request;


public interface RequestHandler {
    Request requestHandle(Request request);
}
