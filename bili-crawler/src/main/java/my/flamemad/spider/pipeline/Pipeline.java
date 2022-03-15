package my.flamemad.spider.pipeline;


public interface Pipeline {
    void transfer(Object o);
    void close();
}
