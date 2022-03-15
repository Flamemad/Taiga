package my.flamemad.spider.control;

import lombok.Data;

@Data
public class Sign {
    private boolean isEnd;
    public Sign() {
        this.isEnd = false;
    }
}
