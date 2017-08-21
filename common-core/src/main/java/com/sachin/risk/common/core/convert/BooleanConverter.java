package com.sachin.risk.common.core.convert;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:44
 */
public class BooleanConverter extends AbstractBatchConverter<Boolean> {

    @Override
    public Boolean convert(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof Boolean) {
            return (Boolean) source;
        }
        String s = source.toString().trim().toLowerCase();
        if ("true".equals(s)) {
            return true;
        } else if ("false".equals(s)) {
            return false;
        } else {
            throw new IllegalArgumentException("error boolean source: " + source);
        }
    }
}
