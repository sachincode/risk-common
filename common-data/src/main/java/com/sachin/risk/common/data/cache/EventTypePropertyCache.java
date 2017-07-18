package com.sachin.risk.common.data.cache;

import com.sachin.risk.common.data.model.EventTypeProperty;
import com.sachin.risk.common.util.cache.AbstractCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shicheng.zhang
 * @since 17-7-18 下午8:15
 */
public class EventTypePropertyCache extends AbstractCache {

    private ConcurrentHashMap<Long, Map<String, EventTypeProperty>> idPropertyMap = new ConcurrentHashMap<>();

    private EventTypePropertyCache() {
    }

    private static class EventTypePropertyCacheHolder {
        private static final EventTypePropertyCache instance = new EventTypePropertyCache();
    }

    public static EventTypePropertyCache getInstance() {
        return EventTypePropertyCacheHolder.instance;
    }

    public void setIdPropertyMap(ConcurrentHashMap<Long, Map<String, EventTypeProperty>> idPropertyMap) {
        this.idPropertyMap = idPropertyMap;
    }

    private Map<String, EventTypeProperty> getEventTypeProperty(Long eventTypeId) {
        if (eventTypeId == null) {
            return null;
        }
        return idPropertyMap.get(eventTypeId);
    }
}
