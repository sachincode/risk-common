package com.sachin.risk.common.data.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.sachin.risk.common.data.model.AirportCode;
import com.sachin.risk.common.util.cache.AbstractCache;
import org.apache.commons.lang.StringUtils;

/**
 * 机场三字码归属地缓存
 * 
 * @author shicheng.zhang
 * @since 17-7-26 下午3:26
 */
public class AirportCodeCache extends AbstractCache {

    private ConcurrentHashMap<String, AirportCode> airportCodeMap = new ConcurrentHashMap<>();

    private AirportCodeCache() {
    }

    private static class AirportCodeCacheCacheHolder {
        private static final AirportCodeCache instance = new AirportCodeCache();
    }

    public static AirportCodeCache getInstance() {
        return AirportCodeCacheCacheHolder.instance;
    }

    public ConcurrentHashMap<String, AirportCode> getAirportCodeMap() {
        return airportCodeMap;
    }

    public void setAirportCodeMap(ConcurrentHashMap<String, AirportCode> airportCodeMap) {
        this.airportCodeMap = airportCodeMap;
    }

    public boolean putAirportCode(AirportCode airportCode) {
        if (airportCode == null || StringUtils.isBlank(airportCode.getAirportCode())) {
            return false;
        }
        airportCodeMap.put(airportCode.getAirportCode(), airportCode);
        return true;
    }

    public AirportCode getAirportCode(String airportCode) {
        if (airportCode == null) {
            return null;
        }
        return airportCodeMap.get(airportCode);
    }

}
