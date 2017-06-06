package com.sachin.risk.common.data.loader;

import com.sachin.risk.common.data.cache.DictCache;
import com.sachin.risk.common.data.dao.RcDictEntryMapper;
import com.sachin.risk.common.data.dao.RcDictTableMapper;
import com.sachin.risk.common.data.model.DictEntry;
import com.sachin.risk.common.data.model.DictTable;
import com.sachin.risk.common.util.cache.AbstractCacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典表缓存加载
 * 
 * @author shicheng.zhang
 * @since 17-4-27 下午12:00
 */
@Service
public class DictCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(DictCacheLoader.class);

    private static final int COMMON_DICT_TYPE = 1;
    private static final int LIST_DICT_TYPE = 2;

    @Resource
    private RcDictTableMapper rcDictTableMapper;
    @Resource
    private RcDictEntryMapper rcDictEntryMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = getMaxUpdateTime();
        if (lastTime == null) {
            return;
        }
        List<DictTable> dictTables = rcDictTableMapper.queryAll();
        Map<Long, DictTable> tableMap = getTableIdMap(dictTables);
        ConcurrentHashMap<String, Map<String, DictEntry>> commonDictMap = new ConcurrentHashMap<String, Map<String, DictEntry>>();
        ConcurrentHashMap<String, Map<String, DictEntry>> listDictMap = new ConcurrentHashMap<String, Map<String, DictEntry>>();
        List<DictEntry> dictEntries = rcDictEntryMapper.queryAll();
        for (DictEntry dictEntry : dictEntries) {
            DictTable dictTable = tableMap.get(dictEntry.getTableId());
            if (dictTable == null) {
                continue;
            }
            if (dictTable.getTableType() == COMMON_DICT_TYPE) {
                if (commonDictMap.get(dictTable.getTableName()) == null) {
                    commonDictMap.put(dictTable.getTableName(), new HashMap<String, DictEntry>());
                }
                commonDictMap.get(dictTable.getTableName()).put(dictEntry.getDictCode(), dictEntry);
            } else if (dictTable.getTableType() == LIST_DICT_TYPE) {
                if (listDictMap.get(dictTable.getTableName()) == null) {
                    listDictMap.put(dictTable.getTableName(), new HashMap<String, DictEntry>());
                }
                listDictMap.get(dictTable.getTableName()).put(dictEntry.getDictCode(), dictEntry);
            }
        }
        DictCache.getInstance().setCommonDictMap(commonDictMap);
        DictCache.getInstance().setListDictMap(listDictMap);
        DictCache.getInstance().setLastTime(lastTime);
        logger.info("dict cache load finished. common size: {}, list size: {}, use time: {}", commonDictMap.size(),
                listDictMap.size(), System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        long start = System.currentTimeMillis();
        if (DictCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        List<DictTable> dictTables = rcDictTableMapper.queryAll();
        removeDeletedTable(dictTables);
        Date lastTime = getMaxUpdateTime();
        if (lastTime == null || DictCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload dict cache.");
            return;
        }
        Map<Long, DictTable> tableMap = getTableIdMap(dictTables);
        for (Map.Entry<Long, DictTable> entry : tableMap.entrySet()) {
            DictTable dictTable = entry.getValue();
            if (dictTable.getUpdateTime().compareTo(lastTime) < 0) {
                continue;
            }
            List<DictEntry> dictEntries = rcDictEntryMapper.queryByTableId(dictTable.getId());
            Map<String, DictEntry> entryMap = new HashMap<String, DictEntry>();
            for (DictEntry dictEntry : dictEntries) {
                entryMap.put(dictEntry.getDictCode(), dictEntry);
            }
            if (dictTable.getTableType() == COMMON_DICT_TYPE) {
                DictCache.getInstance().getCommonDictMap().put(dictTable.getTableName(), entryMap);
            } else if (dictTable.getTableType() == LIST_DICT_TYPE) {
                DictCache.getInstance().getListDictMap().put(dictTable.getTableName(), entryMap);
            }
        }

        logger.info("dict cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }

    private Map<Long, DictTable> getTableIdMap(List<DictTable> dictTables) {
        Map<Long, DictTable> map = new HashMap<Long, DictTable>();
        if (dictTables == null) {
            return map;
        }
        for (DictTable table : dictTables) {
            map.put(table.getId(), table);
        }
        return map;
    }

    private Map<String, DictTable> getTableNameMap(List<DictTable> dictTables) {
        Map<String, DictTable> map = new HashMap<String, DictTable>();
        if (dictTables == null) {
            return map;
        }
        for (DictTable table : dictTables) {
            map.put(table.getTableName(), table);
        }
        return map;
    }

    private Date getMaxUpdateTime() {
        Date tableDate = rcDictTableMapper.queryMaxUpdateTime();
        Date entryDate = rcDictEntryMapper.queryMaxUpdateTime();
        if (tableDate == null || entryDate == null) {
            return entryDate == null ? tableDate : entryDate;
        }
        return entryDate.compareTo(tableDate) >= 0 ? entryDate : tableDate;
    }

    /**
     * 比对库和内存中的tbl_name,如库里不存在，内存存在，则需要清除内存中的字典表
     * 
     * @param dictTables
     */
    private void removeDeletedTable(List<DictTable> dictTables) {
        Map<String, DictTable> nameMap = getTableNameMap(dictTables);
        Set<String> names = new HashSet<String>();
        names.addAll(getNotInKey(nameMap, DictCache.getInstance().getCommonDictMap()));
        names.addAll(getNotInKey(nameMap, DictCache.getInstance().getListDictMap()));
        for (String name : names) {
            DictTable dictTable = nameMap.get(name);
            if (dictTable != null) {
                if (dictTable.getTableType() == COMMON_DICT_TYPE) {
                    DictCache.getInstance().getCommonDictMap().remove(name);
                } else if (dictTable.getTableType() == LIST_DICT_TYPE) {
                    DictCache.getInstance().getListDictMap().remove(name);
                }
            }
            logger.info("dict table has been deleted from db and cache. table name: {}", name);
        }
    }

    private Set<String> getNotInKey(Map<String, DictTable> nameMap,
            ConcurrentHashMap<String, Map<String, DictEntry>> dictMap) {
        Set<String> names = new HashSet<String>();
        for (Map.Entry<String, Map<String, DictEntry>> entry : dictMap.entrySet()) {
            if (!nameMap.containsKey(entry.getKey())) {
                names.add(entry.getKey());
            }
        }
        return names;
    }
}
