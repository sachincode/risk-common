package com.sachin.risk.common.data.loader;

import com.sachin.risk.common.core.model.EventType;
import com.sachin.risk.common.data.cache.EventTypeCache;
import com.sachin.risk.common.data.dao.RcEventTypeMapper;
import com.sachin.risk.common.util.cache.AbstractCacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件类型缓存加载 依赖数据表更新时间
 * 
 * @author shicheng.zhang
 * @since 17-7-14 下午6:13
 */
@Service
public class EventTypeCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(EventTypeCacheLoader.class);

    @Resource
    private RcEventTypeMapper rcEventTypeMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcEventTypeMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<String, EventType> codeEventMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<Long, EventType> idEventMap = new ConcurrentHashMap<>();
        List<EventType> eventTypes = rcEventTypeMapper.queryAllEventType();

        for (EventType eventType : eventTypes) {
            codeEventMap.put(eventType.getCode(), eventType);
            idEventMap.put(eventType.getId(), eventType);
        }

        EventTypeCache.getInstance().setCodeEventMap(codeEventMap);
        EventTypeCache.getInstance().setIdEventMap(idEventMap);
        EventTypeCache.getInstance().setLastTime(lastTime);
        EventTypeCache.getInstance().setLastCount(eventTypes.size());

        logger.info("EventType cache load finished. size: {}, use time: {}", eventTypes.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (EventTypeCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }

        Date lastTime = rcEventTypeMapper.queryMaxUpdateTime();
        if (lastTime == null || EventTypeCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload EventType cache.");
            return;
        }
        // 因为量少，所以重加载也全量更新
        doLoad();
        logger.info("EventType cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
