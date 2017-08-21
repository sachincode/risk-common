package com.sachin.risk.common.core.convert;

import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:44
 */
public class BigDecimalConverter extends AbstractBatchConverter<BigDecimal> {

    @Override
    public BigDecimal convert(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof BigDecimal) {
            return (BigDecimal) source;
        }
        try {
            return new BigDecimal(source.toString().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("error long source: " + source);
        }
    }

}
