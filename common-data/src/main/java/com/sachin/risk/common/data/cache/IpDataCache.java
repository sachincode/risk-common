package com.sachin.risk.common.data.cache;

import java.util.TreeMap;

import com.sachin.risk.common.data.model.IpData;
import com.sachin.risk.common.data.model.IpKey;
import com.sachin.risk.common.util.IpUtil;
import org.apache.commons.collections.MapUtils;

import com.sachin.risk.common.util.cache.AbstractCache;
import org.apache.commons.lang.StringUtils;

/**
 * 手机号归属地缓存
 * 
 * @author shicheng.zhang
 * @since 17-7-26 下午3:26
 */
public class IpDataCache extends AbstractCache {

    private TreeMap<IpKey, IpData> ipDataTreeMap = new TreeMap<IpKey, IpData>();

    private IpDataCache() {
    }

    private static class IpDataCacheCacheHolder {
        private static final IpDataCache instance = new IpDataCache();
    }

    public static IpDataCache getInstance() {
        return IpDataCacheCacheHolder.instance;
    }

    public TreeMap<IpKey, IpData> getIpDataTreeMap() {
        return ipDataTreeMap;
    }

    public void setIpDataTreeMap(TreeMap<IpKey, IpData> ipDataTreeMap) {
        this.ipDataTreeMap = ipDataTreeMap;
    }

    public synchronized boolean putIpData(IpData ipData) {
        if (ipData == null || ipData.getStartIp() == null || ipData.getEndIp() == null) {
            return false;
        }
        ipDataTreeMap.put(new IpKey(ipData.getStartIp(), ipData.getEndIp()), ipData);
        return true;
    }

    public synchronized boolean putIpData(TreeMap<IpKey, IpData> treeMap) {
        if (MapUtils.isEmpty(treeMap)) {
            return false;
        }
        ipDataTreeMap.putAll(treeMap);
        return true;
    }

    public IpData getIpData(IpKey ipKey) {
        if (ipKey == null) {
            return null;
        }
        return ipDataTreeMap.get(ipKey);
    }

    /**
     * 
     * @param ip 点分ip，比如：1.3.2.3
     * @return
     */
    public IpData getIpData(String ip) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        long ipToLong = IpUtil.ipToLong(ip);
        return ipDataTreeMap.get(new IpKey(ipToLong, ipToLong));
    }

    /**
     * 
     * @param ip 整形IP
     * @return
     */
    public IpData getIpData(Long ip) {
        if (ip == null) {
            return null;
        }
        return ipDataTreeMap.get(new IpKey(ip, ip));
    }

}
