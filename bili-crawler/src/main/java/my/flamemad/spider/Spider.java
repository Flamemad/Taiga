package my.flamemad.spider;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import my.flamemad.spider.control.Sign;
import my.flamemad.spider.net.HttpJavaBaseNetHand;
import my.flamemad.spider.control.MemoryRequestQueue;
import my.flamemad.spider.net.NetHand;
import my.flamemad.spider.control.RequestQueue;
import my.flamemad.spider.pipeline.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Slf4j
@Accessors(chain = true)
public class Spider {
    private Core core;
    private NetHand netHand;
    private Request request;
    private PipelineFilter pipelineFilter;
    private Pipeline pipeline;
    private RequestQueue queue;
    private ExecutorService executor;
    private Sign sign;
    private int thread;

    public Spider() {
        core = new Core();
        sign = new Sign();
        netHand = new HttpJavaBaseNetHand();
        pipeline = new CommandLinePipeline();
        queue = new MemoryRequestQueue();
        pipelineFilter = new NoActionPipelineFilter();
        thread = 1;
    }

    public void start() {
        if (queue.isEmpty()) {
            queue.set(request);
        }
        executor = Executors.newFixedThreadPool(thread);
        for (int i = 0; i < thread; i++) {
            Core core = this.core.clone();
            executor.execute(() -> core.run(queue,sign,
                    netHand, pipeline, pipelineFilter));
        }
        executor.shutdown();
    }

    public void run() {
        queue.set(request);
        core.run(queue, sign,netHand, pipeline, pipelineFilter);
    }

}
