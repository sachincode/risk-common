package com.sachin.risk.common.data.loader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.sachin.risk.common.data.cache.PersonalIdDataCache;
import com.sachin.risk.common.data.dao.RcPersonalIdDataMapper;
import com.sachin.risk.common.data.model.PersonalIdData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sachin.risk.common.util.cache.AbstractCacheLoader;

/**
 * 身份证归属地缓存
 * 依赖数据表更新时间
 * 不对删除数据更新缓存
 *
 * @author shicheng.zhang
 * @since 17-7-26 下午6:23
 */
@Service
public class PersonalIdDataCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(PersonalIdDataCacheLoader.class);

    @Resource
    private RcPersonalIdDataMapper rcPersonalIdDataMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcPersonalIdDataMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<String, PersonalIdData> personalIdDataMap = new ConcurrentHashMap<>();
        List<PersonalIdData> personalIdDatas = rcPersonalIdDataMapper.queryAllPersonalIdData();
        for (PersonalIdData personalIdData : personalIdDatas) {
            personalIdDataMap.put(personalIdData.getPrefix(), personalIdData);
        }

        PersonalIdDataCache.getInstance().setPersonalIdDataMap(personalIdDataMap);
        PersonalIdDataCache.getInstance().setLastTime(lastTime);
        PersonalIdDataCache.getInstance().setLastCount(personalIdDatas.size());

        logger.info("PersonalIdDataCache cache load finished. size: {}, use time: {}", personalIdDatas.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (PersonalIdDataCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        // 2. 数据库最大更新时间和缓存上次更新时间相同，则不更新缓存
        Date lastTime = rcPersonalIdDataMapper.queryMaxUpdateTime();
        if (lastTime == null || PersonalIdDataCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload PersonalIdDataCache cache.");
            return;
        }
        // 3. 根据缓存的上次更新时间，查询大于等于此时间的所有记录更新缓存，并记录当前的数据库最大更新时间
        List<PersonalIdData> personalIdDatas = rcPersonalIdDataMapper
                .queryGteUpdateTime(PersonalIdDataCache.getInstance().getLastTime());
        for (PersonalIdData personalIdData : personalIdDatas) {
            PersonalIdDataCache.getInstance().putPersonalIdData(personalIdData);
        }
        PersonalIdDataCache.getInstance().setLastTime(lastTime);
        PersonalIdDataCache.getInstance().setLastCount(personalIdDatas.size());
        logger.info("PersonalIdDataCache cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
