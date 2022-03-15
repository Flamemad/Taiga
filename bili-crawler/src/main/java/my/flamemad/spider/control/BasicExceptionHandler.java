package my.flamemad.spider.control;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicExceptionHandler implements ExceptionHandler{
    @Override
    public boolean exceptionHandle(Exception e) {
        log.error("It's crashed!",e);
        return true;
    }
}
