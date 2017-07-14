package com.sachin.risk.common.data.cache;

import com.sachin.risk.common.data.model.DictEntry;
import com.sachin.risk.common.util.cache.AbstractCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典表缓存
 * 使用约定：使用缓存的地方请勿修改缓存内容
 * @author shicheng.zhang
 * @since 17-4-27 上午11:40
 */
public class DictCache extends AbstractCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictCache.class);

    private ConcurrentHashMap<String, Map<String, DictEntry>> commonDictMap = new ConcurrentHashMap<String, Map<String, DictEntry>>();
    private ConcurrentHashMap<String, Map<String, DictEntry>> listDictMap = new ConcurrentHashMap<String, Map<String, DictEntry>>();


    private DictCache() {
    }

    private static class DictCacheHolder {
        private static final DictCache instance = new DictCache();
    }

    public static DictCache getInstance() {
        return DictCacheHolder.instance;
    }

    public ConcurrentHashMap<String, Map<String, DictEntry>> getCommonDictMap() {
        return commonDictMap;
    }

    public void setCommonDictMap(ConcurrentHashMap<String, Map<String, DictEntry>> commonDictMap) {
        this.commonDictMap = commonDictMap;
    }

    public ConcurrentHashMap<String, Map<String, DictEntry>> getListDictMap() {
        return listDictMap;
    }

    public void setListDictMap(ConcurrentHashMap<String, Map<String, DictEntry>> listDictMap) {
        this.listDictMap = listDictMap;
    }

    public Map<String, DictEntry> getCommonDictMap(String tableName) {
        Map<String, DictEntry> map = commonDictMap.get(tableName);
        if (map == null) {
            LOGGER.warn("common dict not exist. tableName: {}", tableName);
        }
        return map;
    }

    public Map<String, DictEntry> getListDictMap(String tableName) {
        Map<String, DictEntry> map = listDictMap.get(tableName);
        if (map == null) {
            LOGGER.warn("list dict not exist. tableName: {}", tableName);
        }
        return map;
    }
}
