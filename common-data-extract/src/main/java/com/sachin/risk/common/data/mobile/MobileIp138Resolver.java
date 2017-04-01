package com.sachin.risk.common.data.mobile;

import com.google.common.base.Splitter;
import com.sachin.risk.common.data.Constants;
import com.sachin.risk.common.data.HttpClientHolder;
import com.sachin.risk.common.data.Resolver;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 下午3:50
 * @Description:
 */
public class MobileIp138Resolver implements Resolver<MobileInfo> {

    private static final String API_URL = "http://www.ip138.com:8080/search.asp?action=mobile&mobile=";
    private static final Logger logger = LoggerFactory.getLogger(MobileIp138Resolver.class);
    private static final Splitter splitter = Splitter.on("|").trimResults().omitEmptyStrings();

    @Override
    public MobileInfo resolve(String param) {
        HttpClient httpClient = HttpClientHolder.getHttpClient();
        HttpGet httpget = new HttpGet(API_URL + param);
        try {
            HttpResponse response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String gbkHtml = EntityUtils.toString(entity, "gb2312");

            Document document = Jsoup.parse(gbkHtml);
            Elements tables = document.getElementsByTag("table");
            if (tables.size() <= 0) {
                return null;
            }
            Element resultTable = null;
            for (Element table : tables) {
                if (table.text().contains("查询结果")) {
                    resultTable = table;
                    break;
                }
            }
            if (resultTable == null) {
                return null;
            }
            Elements trs = resultTable.getElementsByTag("tr");
            if (trs.size() <= 0) {
                return null;
            }

            MobileInfo mobileInfo = new MobileInfo();
            mobileInfo.setPrefix(param.substring(0, 7));
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() != 2) {
                    continue;
                }
                String title = trimString(tds.get(0).text());
                if ("卡号归属地".equals(title)) {
                    String value = tds.get(1).text().replaceAll("[\\u00A0]+", "|");
                    List<String> values = splitter.splitToList(value);
                    if (values.size() >= 1) {
                        mobileInfo.setProvince(values.get(0));
                        if (values.size() >= 2) {
                            if (values.get(1).endsWith("市")) {
                                mobileInfo.setCity(values.get(1).substring(0, values.get(1).length() - 1));
                            } else {
                                mobileInfo.setCity(values.get(1));
                            }
                        }
                    }
                } else if ("卡类型".equals(title)) {
                    String value = tds.get(1).text().replaceAll("[\\u00A0]+", " ");
                    mobileInfo.setCard(value);
                    mobileInfo.setSp(value);
                }
            }

            if (mobileInfo.getProvince() != null && mobileInfo.getProvince().length() > 0) {
                mobileInfo.setSource(Constants.IP138);
                return mobileInfo;
            }
        } catch (Exception e) {
            logger.error("resolve mobile info from ip138 error. param: {}", param, e);
        }
        return null;
    }

    private String trimString(String s) {
        if (s == null) {
            return null;
        }
        return s.trim().replace(" ", "").replaceAll("[\\u00A0]+", "");
    }
}
