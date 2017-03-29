package com.sachin.risk.common.data;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 下午3:54
 * @Description:
 */
public class HttpClientHolder {

    private static final HttpClient HTTP_CLIENT = new DefaultHttpClient();

    public static HttpClient getHttpClient() {
        return HTTP_CLIENT;
    }
}
