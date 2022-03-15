package my.flamemad.spider.pipeline;

import my.flamemad.spider.Response;

public class BasicPipelineFilter implements PipelineFilter{
    @Override
    public Object doFilter(Response response) {
        return response.getResult();
    }
}
