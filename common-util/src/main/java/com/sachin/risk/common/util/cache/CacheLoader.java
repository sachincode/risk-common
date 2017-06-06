package com.sachin.risk.common.util.cache;

/**
 * @author shicheng.zhang
 * @since 17-4-24 下午9:06
 */
public interface CacheLoader {

    /**
     * 首次启动时加载缓存
     */
    void load();

    /**
     * 更新加载缓存
     */
    void reload();
}
