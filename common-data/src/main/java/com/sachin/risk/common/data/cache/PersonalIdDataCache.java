package com.sachin.risk.common.data.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.sachin.risk.common.data.model.PersonalIdData;
import org.apache.commons.lang.StringUtils;

import com.sachin.risk.common.util.cache.AbstractCache;

/**
 * 身份证归属地缓存
 * 
 * @author shicheng.zhang
 * @since 17-7-26 下午3:26
 */
public class PersonalIdDataCache extends AbstractCache {

    private ConcurrentHashMap<String, PersonalIdData> personalIdDataMap = new ConcurrentHashMap<>();

    private PersonalIdDataCache() {
    }

    private static class PersonalIdDataCacheCacheHolder {
        private static final PersonalIdDataCache instance = new PersonalIdDataCache();
    }

    public static PersonalIdDataCache getInstance() {
        return PersonalIdDataCacheCacheHolder.instance;
    }

    public ConcurrentHashMap<String, PersonalIdData> getPersonalIdDataMap() {
        return personalIdDataMap;
    }

    public void setPersonalIdDataMap(ConcurrentHashMap<String, PersonalIdData> personalIdDataMap) {
        this.personalIdDataMap = personalIdDataMap;
    }

    public boolean putPersonalIdData(PersonalIdData personalIdData) {
        if (personalIdData == null || StringUtils.isBlank(personalIdData.getPrefix())) {
            return false;
        }
        personalIdDataMap.put(personalIdData.getPrefix(), personalIdData);
        return true;
    }

    public PersonalIdData getPersonalIdData(String personalId) {
        if (personalId == null) {
            return null;
        }
        return personalIdDataMap.get(StringUtils.substring(personalId, 0, 6));
    }

}
