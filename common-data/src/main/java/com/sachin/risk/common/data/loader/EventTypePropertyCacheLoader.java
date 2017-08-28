package com.sachin.risk.common.data.loader;

import com.sachin.risk.common.core.model.EventType;
import com.sachin.risk.common.data.cache.EventTypeCache;
import com.sachin.risk.common.data.cache.EventTypePropertyCache;
import com.sachin.risk.common.data.dao.RcEventTypePropertyMapper;
import com.sachin.risk.common.data.model.EventTypeProperty;
import com.sachin.risk.common.util.cache.AbstractCacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件类型属性缓存
 * @author shicheng.zhang
 * @since 17-7-18 下午8:16
 */
@Service
public class EventTypePropertyCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(EventTypePropertyCacheLoader.class);

    @Resource
    private RcEventTypePropertyMapper rcEventTypePropertyMapper;


    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcEventTypePropertyMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<Long, Map<String, EventTypeProperty>> idPropertyMap = new ConcurrentHashMap<>();
        List<EventTypeProperty> properties = rcEventTypePropertyMapper.queryAll();

        for (EventTypeProperty property : properties) {
            Map<String, EventTypeProperty> map = idPropertyMap.get(property.getEventTypeId());
            if (map == null) {
                idPropertyMap.put(property.getEventTypeId(), new HashMap<String, EventTypeProperty>());
            }
            idPropertyMap.get(property.getEventTypeId()).put(property.getEventPropKey(), property);
        }

        buildCodePropertyMap(idPropertyMap);
        EventTypePropertyCache.getInstance().setIdPropertyMap(idPropertyMap);
        EventTypePropertyCache.getInstance().setLastTime(lastTime);
        EventTypePropertyCache.getInstance().setLastCount(properties.size());

        logger.info("EventTypeProperty cache load finished. size: {}, use time: {}", properties.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (EventTypePropertyCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }

        Date lastTime = rcEventTypePropertyMapper.queryMaxUpdateTime();
        if (lastTime == null || EventTypePropertyCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload EventTypeProperty cache.");
            return;
        }
        // 因为量少目前全量加载，数据量增多以后需要修改reload()方法
        doLoad();
        logger.info("EventTypeProperty cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }

    private void buildCodePropertyMap(ConcurrentHashMap<Long, Map<String, EventTypeProperty>> idPropertyMap) {
        ConcurrentHashMap<String, Map<String, EventTypeProperty>> codePropertyMap = new ConcurrentHashMap<>();
        for (Map.Entry<Long, Map<String, EventTypeProperty>> entry : idPropertyMap.entrySet()) {
            EventType eventType = EventTypeCache.getInstance().getEventType(entry.getKey());
            if (eventType == null) {
                logger.error("EventType not exist. event type id: {}", entry.getKey());
                continue;
            }
            codePropertyMap.put(eventType.getCode(), entry.getValue());
        }
        EventTypePropertyCache.getInstance().setCodePropertyMap(codePropertyMap);
    }
}
