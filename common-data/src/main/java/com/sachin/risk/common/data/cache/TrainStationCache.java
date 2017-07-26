package com.sachin.risk.common.data.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.sachin.risk.common.data.model.TrainStation;
import com.sachin.risk.common.util.cache.AbstractCache;

/**
 * 火车站归属地缓存
 * 
 * @author shicheng.zhang
 * @since 17-7-25 下午6:16
 */
public class TrainStationCache extends AbstractCache {

    private ConcurrentHashMap<String, TrainStation> stationMap = new ConcurrentHashMap<>();

    private TrainStationCache() {
    }

    private static class TrainStationCacheHolder {
        private static final TrainStationCache instance = new TrainStationCache();
    }

    public static TrainStationCache getInstance() {
        return TrainStationCacheHolder.instance;
    }

    public ConcurrentHashMap<String, TrainStation> getStationMap() {
        return stationMap;
    }

    public void setStationMap(ConcurrentHashMap<String, TrainStation> stationMap) {
        this.stationMap = stationMap;
    }

    public TrainStation getTrainStation(String station) {
        if (station == null) {
            return null;
        }
        return stationMap.get(station);
    }

}
