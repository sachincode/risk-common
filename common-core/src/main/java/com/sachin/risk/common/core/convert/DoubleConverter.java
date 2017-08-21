package com.sachin.risk.common.core.convert;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:44
 */
public class DoubleConverter extends AbstractBatchConverter<Double> {

    @Override
    public Double convert(Object source) {
        if (source == null) {
            return null;
        }
        if (source instanceof Double) {
            return (Double) source;
        }
        try {
            return Double.parseDouble(source.toString().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("error double source: " + source);
        }
    }
}
