package com.sachin.risk.common.data.idnumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
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

import java.util.List;
import java.util.Map;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 下午8:37
 * @Description:
 * https://www.nowapi.com
 * {
    "success": "1",
    "result": {
        "status": "ALREADY_ATT",
        "par": "512928",
        "idcard": "512928196412034413",
        "born": "1964年12月03日",
        "sex": "男",
        "att": "四川省 南充地区 武胜县",
        "postno": "637000",
        "areano": "0817",
        "style_simcall": "中国,四川,南充",
        "style_citynm": "中华人民共和国,四川省,南充市"
    }
}
 */
public class IdNumberK780Resolver implements Resolver<IdNumberInfo> {

    private static final String API_URL = "http://api.k780.com:88/?app=idcard.get&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json&idcard=";
    private static final Logger logger = LoggerFactory.getLogger(IdNumberK780Resolver.class);
    private static final Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public IdNumberInfo resolve(String param) {
        HttpClient httpClient = HttpClientHolder.getHttpClient();
        HttpGet httpget = new HttpGet(API_URL + param);
        try {
            HttpResponse response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, HTTP.UTF_8);
            logger.info("resolve IdNumberInfo from k780. param: {}, result: {}", param, s);
            Map map = OBJECT_MAPPER.readValue(s, Map.class);
            if (map == null) {
                return null;
            }

            if ("1".equals(String.valueOf(map.get("success")))) {
                IdNumberInfo idNumberInfo = new IdNumberInfo();
                Map<String, String> result = (Map<String, String>) map.get("result");
                if (!"ALREADY_ATT".equals(result.get("status"))) {
                    return null;
                }

                idNumberInfo.setPrefix(result.get("par"));
                if (idNumberInfo.getPrefix() == null || idNumberInfo.getPrefix().length() == 0) {
                    idNumberInfo.setPrefix(param.substring(0, 6));
                }
                String styleSimcall = result.get("style_simcall");
                if (styleSimcall == null) {
                    return null;
                }
                List<String> values = splitter.splitToList(styleSimcall);
                if (values.size() >= 2) {
                    idNumberInfo.setProvince(values.get(1));
                    if (values.size() >= 3) {
                        idNumberInfo.setCity(values.get(2));
                    }
                    idNumberInfo.setSource(Constants.K780);
                    return idNumberInfo;
                }
            }
        } catch (Exception e) {
            logger.error("resolve id number info from k780 error. param: {}", param, e);
        }
        return null;
    }
}
