package my.flamemad.spider.control;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class TimeCycleController {
    @SneakyThrows
    public static void stop(int time){
        TimeUnit.SECONDS.sleep(time);
    }
}
