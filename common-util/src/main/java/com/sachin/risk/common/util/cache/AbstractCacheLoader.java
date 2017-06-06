package com.sachin.risk.common.util.cache;

/**
 * 
 * @author shicheng.zhang
 * @since 17-6-05 下午8:06
 */
public abstract class AbstractCacheLoader implements CacheLoader {

    /**
     * 首次启动时加载缓存
     * 
     * @param reload 是否自动reload缓存
     */
    public void load(boolean reload) {
        if (reload) {
            CacheReloadHelper.addCacheLoader(this);
        }
        doLoad();
    }

    protected abstract void doLoad();

    @Override
    public void load() {
        load(false);
    }
}
