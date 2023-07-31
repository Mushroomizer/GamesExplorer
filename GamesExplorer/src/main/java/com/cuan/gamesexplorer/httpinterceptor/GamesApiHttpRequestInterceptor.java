package com.cuan.gamesexplorer.httpinterceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Map;

public class GamesApiHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private Map<String,String> headers;

    public GamesApiHttpRequestInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public ClientHttpResponse intercept(@Nullable HttpRequest request,@Nullable byte[] body,@Nullable ClientHttpRequestExecution execution) throws IOException {
        assert request != null;
        assert execution != null;

        for (String key : headers.keySet()) {

            request.getHeaders().set(key, headers.get(key));
        }
        if(body == null)
            return execution.execute(request,null);
        return execution.execute(request, body);
    }
}
