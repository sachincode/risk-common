package com.sachin.risk.common.data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 下午3:28
 * @Description:
 */
public class Constants {

    public static final String IP138 = "ip138";
    public static final String C360 = "360";
    public static final String K780 = "k780";

    public static final Set<String> DIRECT_CITY_SET = new HashSet<String>();
    public static final Set<String> SP_SET = new HashSet<String>();

    static {
        DIRECT_CITY_SET.add("北京");
        DIRECT_CITY_SET.add("天津");
        DIRECT_CITY_SET.add("上海");
        DIRECT_CITY_SET.add("重庆");

        SP_SET.add("移动");
        SP_SET.add("联通");
        SP_SET.add("电信");
    }

}
