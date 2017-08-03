package com.sachin.risk.common.data.loader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.sachin.risk.common.core.enums.CardType;
import com.sachin.risk.common.data.cache.CardAreaCache;
import com.sachin.risk.common.data.dao.RcCardAreaMapper;
import com.sachin.risk.common.data.model.CardArea;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sachin.risk.common.util.cache.AbstractCacheLoader;

/**
 * 银行卡归属地缓存
 * 依赖数据表更新时间
 * 不对删除数据更新缓存
 *
 * @author shicheng.zhang
 * @since 17-8-3 下午9:23
 */
@Service
public class CardAreaCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(CardAreaCacheLoader.class);

    @Resource
    private RcCardAreaMapper rcCardAreaMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcCardAreaMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<String, CardArea> cardAreaMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, CardArea> cutMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, CardType> cardTypeMap = new ConcurrentHashMap<>();
        long startId = 0, count = 0;
        while (true) {
            List<CardArea> cardAreas = rcCardAreaMapper.queryByLimit(startId, 5000);
            if (CollectionUtils.isEmpty(cardAreas)) {
                break;
            }
            count += cardAreas.size();
            for (CardArea area : cardAreas) {
                cardAreaMap.put(area.getCardAreaCode(), area);
                if (!cutMap.containsKey(area.getBankCode() + area.getCardType().getCode())) {
                    cutMap.put(area.getBankCode() + area.getCardType().getCode(), area);
                }
                if (!cardTypeMap.containsKey(area.getBankCode() + "~" + area.getCardType().getCode())) {
                    cardTypeMap.put(area.getBankCode() + "~" + area.getCardType().getCode(), area.getCardType());
                }
            }
            startId = cardAreas.get(cardAreas.size() - 1).getId();
        }

        CardAreaCache.getInstance().setCardAreaMap(cardAreaMap);
        CardAreaCache.getInstance().setCutMap(cutMap);
        CardAreaCache.getInstance().setCardTypeMap(cardTypeMap);
        CardAreaCache.getInstance().setLastTime(lastTime);
        CardAreaCache.getInstance().setLastCount((int) count);
        logger.info("CardAreaCache cache load finished. size: {}, use time: {}", count,
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (CardAreaCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        // 2. 数据库最大更新时间和缓存上次更新时间相同，则不更新缓存
        Date lastTime = rcCardAreaMapper.queryMaxUpdateTime();
        if (lastTime == null || CardAreaCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload CardAreaCache cache.");
            return;
        }
        // 3. 根据缓存的上次更新时间，查询大于等于此时间的所有记录更新缓存，并记录当前的数据库最大更新时间
        List<CardArea> cardAreas = rcCardAreaMapper.queryGteUpdateTime(CardAreaCache.getInstance().getLastTime());

        ConcurrentHashMap<String, CardArea> cardAreaMap = CardAreaCache.getInstance().getCardAreaMap();
        ConcurrentHashMap<String, CardArea> cutMap = CardAreaCache.getInstance().getCutMap();
        ConcurrentHashMap<String, CardType> cardTypeMap = CardAreaCache.getInstance().getCardTypeMap();

        for (CardArea area : cardAreas) {
            cardAreaMap.put(area.getCardAreaCode(), area);
            if (!cutMap.containsKey(area.getBankCode() + area.getCardType().getCode())) {
                cutMap.put(area.getBankCode() + area.getCardType().getCode(), area);
            }
            if (!cardTypeMap.containsKey(area.getBankCode() + "~" + area.getCardType().getCode())) {
                cardTypeMap.put(area.getBankCode() + "~" + area.getCardType().getCode(), area.getCardType());
            }
        }
        CardAreaCache.getInstance().setLastTime(lastTime);
        CardAreaCache.getInstance().setLastCount(cardAreas.size());
        logger.info("CardAreaCache cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
