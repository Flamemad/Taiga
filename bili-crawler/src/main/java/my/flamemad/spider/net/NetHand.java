package my.flamemad.spider.net;

import my.flamemad.spider.Request;
import my.flamemad.spider.Response;

public interface NetHand {
    Response transfer(Request request);
}
