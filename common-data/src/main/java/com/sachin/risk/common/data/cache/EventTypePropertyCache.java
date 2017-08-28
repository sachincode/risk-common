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

    // 事件类型ID -- （事件属性编码，事件属性）
    private ConcurrentHashMap<Long, Map<String, EventTypeProperty>> idPropertyMap = new ConcurrentHashMap<>();
    // 事件类型编码 -- （事件属性编码，事件属性）
    private ConcurrentHashMap<String, Map<String, EventTypeProperty>> codePropertyMap = new ConcurrentHashMap<>();

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

    public Map<String, EventTypeProperty> getEventTypeProperty(Long eventTypeId) {
        if (eventTypeId == null) {
            return null;
        }
        return idPropertyMap.get(eventTypeId);
    }

    public Map<String, EventTypeProperty> getEventTypeProperty(String eventTypeCode) {
        if (eventTypeCode == null) {
            return null;
        }
        return codePropertyMap.get(eventTypeCode);
    }

    public void setCodePropertyMap(ConcurrentHashMap<String, Map<String, EventTypeProperty>> codePropertyMap) {
        this.codePropertyMap = codePropertyMap;
    }
}
