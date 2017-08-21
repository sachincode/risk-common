package com.sachin.risk.common.core.convert;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:17
 */
public interface Converter<T> {

    public T convert(Object source);

}
