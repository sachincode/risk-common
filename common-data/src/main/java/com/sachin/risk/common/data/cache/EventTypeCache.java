package com.sachin.risk.common.data.cache;

import com.sachin.risk.common.core.model.EventType;
import com.sachin.risk.common.util.cache.AbstractCache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件类型缓存
 * 
 * @author shicheng.zhang
 * @since 17-7-13 下午4:16
 */
public class EventTypeCache extends AbstractCache {

    private ConcurrentHashMap<String, EventType> codeEventMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, EventType> idEventMap = new ConcurrentHashMap<>();

    private EventTypeCache() {
    }

    private static class EventTypeCacheHolder {
        private static final EventTypeCache instance = new EventTypeCache();
    }

    public static EventTypeCache getInstance() {
        return EventTypeCacheHolder.instance;
    }

    public ConcurrentHashMap<String, EventType> getCodeEventMap() {
        return codeEventMap;
    }

    public void setCodeEventMap(ConcurrentHashMap<String, EventType> codeEventMap) {
        this.codeEventMap = codeEventMap;
    }

    public ConcurrentHashMap<Long, EventType> getIdEventMap() {
        return idEventMap;
    }

    public void setIdEventMap(ConcurrentHashMap<Long, EventType> idEventMap) {
        this.idEventMap = idEventMap;
    }

    public EventType getEventType(String eventTypeCode) {
        if (eventTypeCode == null) {
            return null;
        }
        return codeEventMap.get(eventTypeCode);
    }

    public EventType getEventType(Long eventTypeId) {
        if (eventTypeId == null) {
            return null;
        }
        return idEventMap.get(eventTypeId);
    }
}
