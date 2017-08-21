package com.sachin.risk.common.core.convert;

import com.sachin.risk.common.util.IpUtil;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:44
 */
public class IpLongConverter extends AbstractBatchConverter<Long> {

    /**
     * IP数据类型可能是点分格式
     * 也可能是Long类型数据 或 其字符串
     * 
     * @param source
     * @return
     */
    @Override
    public Long convert(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof Long) {
            return (Long) source;
        }

        try {
            String s = source.toString().trim();
            if (NumberUtils.isDigits(s)) {
                return Long.parseLong(s);
            } else if (IpUtil.isIp(s)) {
                return IpUtil.ipToLong(s);
            } else {
                throw new IllegalArgumentException("error ip source: " + source);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("error ip source: " + source);
        }
    }

}
