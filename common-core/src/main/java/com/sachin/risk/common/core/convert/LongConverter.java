package com.sachin.risk.common.core.convert;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:44
 */
public class LongConverter extends AbstractBatchConverter<Long> {

    @Override
    public Long convert(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof Long) {
            return (Long) source;
        }
        try {
            return Long.parseLong(source.toString().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("error long source: " + source);
        }
    }
}
