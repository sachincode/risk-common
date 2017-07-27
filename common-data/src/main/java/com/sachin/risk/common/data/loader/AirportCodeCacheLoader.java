package com.sachin.risk.common.data.loader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.sachin.risk.common.data.cache.AirportCodeCache;
import com.sachin.risk.common.data.dao.RcAirportCodeMapper;
import com.sachin.risk.common.data.model.AirportCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sachin.risk.common.util.cache.AbstractCacheLoader;

/**
 * 机场三字码属地缓存
 * 依赖数据表更新时间
 * 不对删除数据更新缓存
 *
 * @author shicheng.zhang
 * @since 17-7-26 下午6:23
 */
@Service
public class AirportCodeCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(AirportCodeCacheLoader.class);

    @Resource
    private RcAirportCodeMapper rcAirportCodeMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcAirportCodeMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<String, AirportCode> airportCodeMap = new ConcurrentHashMap<>();
        List<AirportCode> airportCodes = rcAirportCodeMapper.queryAllAirportCode();
        for (AirportCode airportCode : airportCodes) {
            airportCodeMap.put(airportCode.getAirportCode(), airportCode);
        }

        AirportCodeCache.getInstance().setAirportCodeMap(airportCodeMap);
        AirportCodeCache.getInstance().setLastTime(lastTime);
        AirportCodeCache.getInstance().setLastCount(airportCodes.size());

        logger.info("AirportCodeCache cache load finished. size: {}, use time: {}", airportCodeMap.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (AirportCodeCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        // 2. 数据库最大更新时间和缓存上次更新时间相同，则不更新缓存
        Date lastTime = rcAirportCodeMapper.queryMaxUpdateTime();
        if (lastTime == null || AirportCodeCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload AirportCodeCache cache.");
            return;
        }
        // 3. 根据缓存的上次更新时间，查询大于等于此时间的所有记录更新缓存，并记录当前的数据库最大更新时间
        List<AirportCode> airportCodes = rcAirportCodeMapper.queryGteUpdateTime(AirportCodeCache.getInstance().getLastTime());
        for (AirportCode airportCode : airportCodes) {
            AirportCodeCache.getInstance().putAirportCode(airportCode);
        }
        AirportCodeCache.getInstance().setLastTime(lastTime);
        AirportCodeCache.getInstance().setLastCount(airportCodes.size());
        logger.info("AirportCodeCache cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
