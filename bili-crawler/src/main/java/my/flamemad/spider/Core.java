package my.flamemad.spider;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import my.flamemad.spider.control.*;
import my.flamemad.spider.net.NetHand;
import my.flamemad.spider.pipeline.Pipeline;
import my.flamemad.spider.pipeline.PipelineFilter;

@Slf4j
@Data
@Accessors(chain = true)
public class Core implements Cloneable {
    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;
    private ExceptionHandler exceptionHandler;
    private boolean isRepeatable;
    private boolean isStepStop;
    private int retrySleepTime;
    private int stopTime;

    public Core() {
        requestHandler = new NoActionRequestHandler();
        responseHandler = new NoActionResponseHandler();
        exceptionHandler = new BasicExceptionHandler();
        isRepeatable = false;
        isStepStop = false;
        retrySleepTime = 1;
        stopTime = 10;
    }

    public void run(RequestQueue queue, Sign sign,
                    NetHand netHand, Pipeline pipeline, PipelineFilter filter) {
        Response response;
        Request request;
        do {
            if (sign.isEnd()) break;
            request = queue.getRetryRequest();
            if (request == null) {
                request = queue.get();
                if (request == null) {
                    continue;
                }else {
                    queue.set(requestHandler.requestHandle(request.copy()));
                }
            }
            response = netHand.transfer(request);
            if (response.hasException()) {
                queue.setRetryRequest(request);
                if (exceptionHandler.exceptionHandle(
                        response.getException())) {
                    break;
                }
            } else {
                Object o = responseHandler.responseHandle(request, response, queue, this);
                if (o == null) {
                    log.info("在人为设定下的正常退出");
                    break;
                } else if (o instanceof Response) {
                    pipeline.transfer(filter.doFilter((Response) o));
                } else if (o instanceof Boolean) {
                    if ((Boolean) o) {
                        queue.setRetryRequest(request);
                    }
                } else {
                    log.error("意外的返回值导致的程序异常退出");
                    break;
                }
            }
            if (isStepStop) TimeCycleController.stop(stopTime);
        } while (isRepeatable);
    }

    @Override
    @SneakyThrows
    public Core clone() {
        return (Core) super.clone();
    }
}
