package my.flamemad.spider;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Response {
    private int ResponseCode;
    private String result;
    private Exception exception;

    public boolean hasException(){
        return exception != null;
    }
}
