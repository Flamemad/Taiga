package my.flamemad.spider.control;

import my.flamemad.spider.Core;
import my.flamemad.spider.Request;
import my.flamemad.spider.Response;

public interface ResponseHandler {
    Object responseHandle(Request request,Response response, RequestQueue queue, Core core);
}
