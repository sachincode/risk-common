package com.sachin.risk.common.util.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author shicheng.zhang
 * @since 17-6-6 上午11:02
 */
public class CacheReloadHelper {

    private CacheReloadHelper() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheReloadHelper.class);

    private static final ConcurrentHashMap<String, CacheLoader> CACHE_LOADERS = new ConcurrentHashMap<String, CacheLoader>();

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    static {
        EXECUTOR_SERVICE.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<String, CacheLoader> entry : CACHE_LOADERS.entrySet()) {
                    try {
                        long start = System.currentTimeMillis();
                        CacheLoader cacheLoader = entry.getValue();
                        cacheLoader.reload();
                        LOGGER.info("cache schedule reload finished. type: {}, use time: {}", entry.getKey(),
                                System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        LOGGER.error("cache schedule reload error. type: {}", entry.getKey(), e);
                    }
                }
            }
        }, 60 * 3, 60, TimeUnit.SECONDS);
    }

    public static void addCacheLoader(AbstractCacheLoader cacheLoader) {
        String className = cacheLoader.getClass().getName();
        CACHE_LOADERS.putIfAbsent(className, cacheLoader);
    }

    public static void destroy() {
        CACHE_LOADERS.clear();
        EXECUTOR_SERVICE.shutdown();
    }
}
