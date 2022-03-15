package my.flamemad.spider;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.net.Proxy;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Request implements Serializable {
    private static final long serialVersionUID = -999956900718131297L;
    private String link;
    private RequestMethod method;
    private Map<String,String> headers;
    private Map<String,String> param;
    private int timeout;
    private Proxy proxy;

    public String getParamGetFullLink(){
        if (param == null || param.isEmpty()){
            return link;
        }
        StringBuilder fullLink;
        if (link.endsWith("/")) {
            fullLink = new StringBuilder(link.substring(0,link.length() - 1));
        }else {
            fullLink = new StringBuilder(link);
        }
        fullLink.append("?");
        int size = param.size();
        int times = 0;
        for (Map.Entry<String,String> entry:param.entrySet()){
            fullLink.append(entry.getKey()).append("=").append(entry.getValue());
            times ++;
            if (times < size){
                fullLink.append("&");
            }
        }
        return fullLink.toString();
    }

    public Request copy(){
        return (Request) SerializationUtils.clone(this);
    }
}
