package com.sachin.risk.common.core.convert;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:43
 */
public class StringConverter extends AbstractBatchConverter<String> {

    @Override
    public String convert(Object source) {
        if (source == null) {
            return null;
        }
        return source.toString();
    }
}
