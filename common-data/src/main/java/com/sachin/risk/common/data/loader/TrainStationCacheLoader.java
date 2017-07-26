package com.sachin.risk.common.data.loader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.sachin.risk.common.data.cache.TrainStationCache;
import com.sachin.risk.common.data.dao.RcTrainStationMapper;
import com.sachin.risk.common.data.model.TrainStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sachin.risk.common.util.cache.AbstractCacheLoader;

/**
 * 火车站归属地缓存
 * 依赖数据表更新时间
 * 不对删除数据更新缓存
 *
 * @author shicheng.zhang
 * @since 17-7-25 下午6:23
 */
@Service
public class TrainStationCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(TrainStationCacheLoader.class);

    @Resource
    private RcTrainStationMapper rcTrainStationMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcTrainStationMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<String, TrainStation> stationMap = new ConcurrentHashMap<>();
        List<TrainStation> trainStations = rcTrainStationMapper.queryAllTrainStation();
        for (TrainStation station : trainStations) {
            stationMap.put(station.getStation(), station);
        }

        TrainStationCache.getInstance().setStationMap(stationMap);
        TrainStationCache.getInstance().setLastTime(lastTime);
        TrainStationCache.getInstance().setLastCount(trainStations.size());

        logger.info("TrainStationCache cache load finished. size: {}, use time: {}", trainStations.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (TrainStationCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        // 2. 数据库最大更新时间和缓存上次更新时间相同，则不更新缓存
        Date lastTime = rcTrainStationMapper.queryMaxUpdateTime();
        if (lastTime == null || TrainStationCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload TrainStationCache cache.");
            return;
        }
        // 3. 因为量少，所以重加载也全量更新
        doLoad();
        logger.info("TrainStationCache cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
