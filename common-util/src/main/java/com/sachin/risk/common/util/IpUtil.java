package com.sachin.risk.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shicheng.zhang
 * @since 17-4-25 下午6:02
 */
public class IpUtil {

    public static final String IP_REGEX = "([0-9]|[0-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    private IpUtil() {
    }

    /**
     *
     * @param ipAddress example: 192.168.1.2
     * @return example: 3232235778
     */
    public static long ipToLong(String ipAddress) {
        if (!isIp(ipAddress)) {
            throw new IllegalArgumentException("illegal ip: " + ipAddress);
        }
        long result = 0;
        String[] ipAddressInArray = ipAddress.trim().split("\\.");
        for (int i = 3; i >= 0; i--) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            // left shifting 24,16,8,0 and bitwise OR
            result |= ip << (i * 8);
        }
        return result;
    }

    /**
     *
     * @param ip example: 3232235778
     * @return example: 192.168.1.2
     */
    public static String longToIp(long ip) {
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            sb.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                sb.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return sb.toString();
    }

    /**
     * 是否点分IP
     * 
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {
        if (ip == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(IP_REGEX);
        Matcher isIp = pattern.matcher(ip);
        return isIp.matches();
    }

    /**
     * 是否局域网IP
     * 
     * @param ip
     * @return
     */
    public static boolean isLocalIp(String ip) {
        long ipToLong = ipToLong(ip);
        return isLocalIp(ipToLong);
    }

    /**
     * 是否局域网IP
     * 
     * @param ip
     * @return
     */
    public static boolean isLocalIp(long ip) {
        return (ip >= 167772160L && ip <= 184549375L) || (ip >= 2886729728L && ip <= 2887778303L)
                || (ip >= 3232235520L && ip <= 3232301055L);
    }

}
