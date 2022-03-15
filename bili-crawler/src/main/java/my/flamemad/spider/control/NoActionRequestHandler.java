package my.flamemad.spider.control;

import my.flamemad.spider.Request;

public class NoActionRequestHandler implements RequestHandler {
    @Override
    public Request requestHandle(Request request) {
        return request;
    }

}
