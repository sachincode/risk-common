package com.sachin.risk.common.core.convert;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:44
 */
public class DateConverter extends AbstractBatchConverter<Date> {

    private static final String[] patterns = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyyMMdd" };

    /**
     * 时间类型可能是patterns的格式字符串
     * 可能是时间戳的long类型或者字符串类型
     * @param source
     * @return
     */
    @Override
    public Date convert(Object source) {
        if (source == null) {
            return null;
        }

        try {
            if (source instanceof Date) {
                return (Date) source;
            } else if (source instanceof Long) {
                return new Date((Long) source);
            } else {
                String s = source.toString().trim();
                try {
                    return DateUtils.parseDateStrictly(s, patterns);
                } catch (ParseException e) {
                    if (NumberUtils.isDigits(s)) {
                        return new Date(Long.parseLong(s));
                    } else {
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("error date source: " + source);
        }
    }

}
