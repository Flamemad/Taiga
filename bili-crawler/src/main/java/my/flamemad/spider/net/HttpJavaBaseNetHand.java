package my.flamemad.spider.net;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import my.flamemad.spider.Request;
import my.flamemad.spider.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

@Slf4j
@Data
public class HttpJavaBaseNetHand implements NetHand {
    private ThreadLocal<HttpURLConnection> connection;

    public HttpJavaBaseNetHand() {
        this.connection = new ThreadLocal<>();
    }

    @Override
    public Response transfer(Request request) {
        Response response = new Response();
        try {
            URL u = new URL(request.getParamGetFullLink());
            connection.set((HttpURLConnection) u.openConnection());
            setConnection(request);
            connection.get().connect();
            String str = getResult();
            response.setResult(str).setResponseCode(connection.get().getResponseCode());
        } catch (IOException e) {
            try {
                response.setResponseCode(connection.get().getResponseCode());
            } catch (IOException ignored){
                //  ignored
            }
            response.setException(e);
        } finally {
            connection.get().disconnect();
        }
        return response;
    }

    private void setConnection(Request request) throws ProtocolException {
        connection.get().setRequestMethod(request.getMethod().toString());
        connection.get().setConnectTimeout(request.getTimeout());
        if (request.getHeaders() != null) {
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                connection.get().setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private String getResult() throws IOException {
        StringBuilder buffer = new StringBuilder();
        InputStream inputStream = connection.get().getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        str = buffer.toString();
        bufferedReader.close();
        inputStreamReader.close();
        return str;
    }
}
