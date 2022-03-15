package my.flamemad.spider.pipeline;

import my.flamemad.spider.Response;

public interface PipelineFilter {
    Object doFilter(Response response);
}
