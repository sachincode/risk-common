package com.sachin.risk.common.data.loader;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.sachin.risk.common.data.cache.IpDataCache;
import com.sachin.risk.common.data.dao.RcIpDataMapper;
import com.sachin.risk.common.data.model.IpData;
import com.sachin.risk.common.data.model.IpKey;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sachin.risk.common.util.cache.AbstractCacheLoader;

/**
 * IP归属地缓存
 * 依赖数据表更新时间
 * 不对删除数据更新缓存
 *
 * @author shicheng.zhang
 * @since 17-7-26 下午6:23
 */
@Service
public class IpDataCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(IpDataCacheLoader.class);

    @Resource
    private RcIpDataMapper rcIpDataMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcIpDataMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        TreeMap<IpKey, IpData> ipDataTreeMap = new TreeMap<IpKey, IpData>();
        long startId = 0;
        while (true) {
            List<IpData> ipDatas = rcIpDataMapper.queryByLimit(startId, 5000);
            if (CollectionUtils.isEmpty(ipDatas)) {
                break;
            }
            for (IpData ipData : ipDatas) {
                ipDataTreeMap.put(new IpKey(ipData.getStartIp(), ipData.getEndIp()), ipData);
            }
            startId = ipDatas.get(ipDatas.size() - 1).getId();
        }

        IpDataCache.getInstance().setIpDataTreeMap(ipDataTreeMap);
        IpDataCache.getInstance().setLastTime(lastTime);
        IpDataCache.getInstance().setLastCount(ipDataTreeMap.size());
        logger.info("IpDataCache cache load finished. size: {}, use time: {}", ipDataTreeMap.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (IpDataCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        // 2. 数据库最大更新时间和缓存上次更新时间相同，则不更新缓存
        Date lastTime = rcIpDataMapper.queryMaxUpdateTime();
        if (lastTime == null || IpDataCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload IpDataCache cache.");
            return;
        }
        // 3. 根据缓存的上次更新时间，查询大于等于此时间的所有记录更新缓存，并记录当前的数据库最大更新时间
        List<IpData> ipDatas = rcIpDataMapper.queryGteUpdateTime(IpDataCache.getInstance().getLastTime());
        TreeMap<IpKey, IpData> ipDataTreeMap = new TreeMap<IpKey, IpData>();
        for (IpData ipData : ipDatas) {
            ipDataTreeMap.put(new IpKey(ipData.getStartIp(), ipData.getEndIp()), ipData);
        }
        IpDataCache.getInstance().putIpData(ipDataTreeMap);
        IpDataCache.getInstance().setLastTime(lastTime);
        IpDataCache.getInstance().setLastCount(ipDataTreeMap.size());
        logger.info("IpDataCache cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
