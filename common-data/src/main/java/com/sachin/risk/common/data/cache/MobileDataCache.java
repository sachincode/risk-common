package com.sachin.risk.common.data.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.sachin.risk.common.data.model.MobileData;
import org.apache.commons.lang.StringUtils;

import com.sachin.risk.common.util.cache.AbstractCache;

/**
 * 手机号归属地缓存
 * 
 * @author shicheng.zhang
 * @since 17-7-26 下午3:26
 */
public class MobileDataCache extends AbstractCache {

    private ConcurrentHashMap<String, MobileData> mobileDataMap = new ConcurrentHashMap<>();

    private MobileDataCache() {
    }

    private static class MobileDataCacheCacheHolder {
        private static final MobileDataCache instance = new MobileDataCache();
    }

    public static MobileDataCache getInstance() {
        return MobileDataCacheCacheHolder.instance;
    }

    public ConcurrentHashMap<String, MobileData> getMobileDataMap() {
        return mobileDataMap;
    }

    public void setMobileDataMap(ConcurrentHashMap<String, MobileData> mobileDataMap) {
        this.mobileDataMap = mobileDataMap;
    }

    public boolean putMobileData(MobileData mobileData) {
        if (mobileData == null || StringUtils.isBlank(mobileData.getPrefix())) {
            return false;
        }
        mobileDataMap.put(mobileData.getPrefix(), mobileData);
        return true;
    }

    public MobileData getMobileData(String mobile) {
        if (mobile == null) {
            return null;
        }
        return mobileDataMap.get(StringUtils.substring(mobile, 0, 7));
    }

}
