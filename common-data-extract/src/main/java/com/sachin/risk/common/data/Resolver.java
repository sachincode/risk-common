package com.sachin.risk.common.data;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 上午11:52
 * @Description:
 */
public interface Resolver<T> {

    T resolve(String param);
}
