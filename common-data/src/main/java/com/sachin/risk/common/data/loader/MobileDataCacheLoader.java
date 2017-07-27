package com.sachin.risk.common.data.loader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.sachin.risk.common.data.cache.MobileDataCache;
import com.sachin.risk.common.data.dao.RcMobileDataMapper;
import com.sachin.risk.common.data.model.MobileData;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sachin.risk.common.util.cache.AbstractCacheLoader;

/**
 * 手机号归属地缓存
 * 依赖数据表更新时间
 * 不对删除数据更新缓存
 *
 * @author shicheng.zhang
 * @since 17-7-26 下午6:23
 */
@Service
public class MobileDataCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(MobileDataCacheLoader.class);

    @Resource
    private RcMobileDataMapper rcMobileDataMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcMobileDataMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<String, MobileData> mobileDataMap = new ConcurrentHashMap<>();
        long startId = 0;
        while (true) {
            List<MobileData> mobileDatas = rcMobileDataMapper.queryByLimit(startId, 5000);
            if (CollectionUtils.isEmpty(mobileDatas)) {
                break;
            }
            for (MobileData mobileData : mobileDatas) {
                mobileDataMap.put(mobileData.getPrefix(), mobileData);
            }
            startId = mobileDatas.get(mobileDatas.size() - 1).getId();
        }

        MobileDataCache.getInstance().setMobileDataMap(mobileDataMap);
        MobileDataCache.getInstance().setLastTime(lastTime);
        MobileDataCache.getInstance().setLastCount(mobileDataMap.size());

        logger.info("MobileDataCache cache load finished. size: {}, use time: {}", mobileDataMap.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (MobileDataCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        // 2. 数据库最大更新时间和缓存上次更新时间相同，则不更新缓存
        Date lastTime = rcMobileDataMapper.queryMaxUpdateTime();
        if (lastTime == null || MobileDataCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload PersonalIdDataCache cache.");
            return;
        }
        // 3. 根据缓存的上次更新时间，查询大于等于此时间的所有记录更新缓存，并记录当前的数据库最大更新时间
        List<MobileData> mobileDatas = rcMobileDataMapper
                .queryGteUpdateTime(MobileDataCache.getInstance().getLastTime());
        for (MobileData mobileData : mobileDatas) {
            MobileDataCache.getInstance().putMobileData(mobileData);
        }
        MobileDataCache.getInstance().setLastTime(lastTime);
        MobileDataCache.getInstance().setLastCount(mobileDatas.size());
        logger.info("MobileDataCache cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
