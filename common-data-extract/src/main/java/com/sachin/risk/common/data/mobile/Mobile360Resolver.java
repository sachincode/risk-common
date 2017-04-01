package com.sachin.risk.common.data.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachin.risk.common.data.Constants;
import com.sachin.risk.common.data.HttpClientHolder;
import com.sachin.risk.common.data.Resolver;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 下午3:50
 * @Description:
 */
public class Mobile360Resolver implements Resolver<MobileInfo> {

    private static final String API_URL = "http://cx.shouji.360.cn/phonearea.php?number=";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(Mobile360Resolver.class);

    @Override
    public MobileInfo resolve(String param) {
        HttpClient httpClient = HttpClientHolder.getHttpClient();
        HttpGet httpget = new HttpGet(API_URL + param);
        try {
            HttpResponse response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, HTTP.UTF_8);
            Map map = OBJECT_MAPPER.readValue(s, Map.class);
            if (map == null) {
                return null;
            }

            // {"code":0,"data":{"province":"\u6d59\u6c5f","city":"\u676d\u5dde","sp":"\u79fb\u52a8"}}

            if (String.valueOf(map.get("code")).equals("0")) {
                Map<String, String> data = (Map<String, String>) map.get("data");
                MobileInfo mobileInfo = new MobileInfo();
                mobileInfo.setPrefix(param.substring(0, 7));
                mobileInfo.setProvince(data.get("province"));
                mobileInfo.setCity(data.get("city"));
                mobileInfo.setSp(data.get("sp"));
                mobileInfo.setSource(Constants.C360);
                return mobileInfo;
            }
        } catch (Exception e) {
            logger.error("resolve mobile info from 360 error. param: {}", param, e);
        }
        return null;
    }

}
