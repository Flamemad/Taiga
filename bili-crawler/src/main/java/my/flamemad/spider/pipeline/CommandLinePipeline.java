package my.flamemad.spider.pipeline;


public class CommandLinePipeline implements Pipeline {

    @Override
    public void transfer(Object o) {
        System.out.println(o.toString());
    }

    @Override
    public void close() {
    }
}
