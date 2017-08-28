package com.sachin.risk.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author shicheng.zhang
 * @since 17-8-15 下午8:48
 */
public class HostUtil {

    private static Logger logger = LoggerFactory.getLogger(HostUtil.class);

    private HostUtil(){}

    public static String getHostName() {
        String name = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            name = address.getHostName();
        } catch (Exception e) {
            logger.error("get server name error.", e);
        }
        return name;
    }

    public static String getHostIp() {
        String ip = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (Exception e) {
            logger.error("get server ip error.", e);
        }
        return ip;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        try {
            // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
            String ip = request.getHeader("X-Real-IP");
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Forwarded-For");
                }
                if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        } catch (Exception e) {
            logger.error("get request remote address error.", e);
            return request.getRemoteAddr();
        }
    }

}
