package com.sachin.risk.common.data.loader;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.sachin.risk.common.data.cache.CardAreaCache;
import com.sachin.risk.common.data.cache.CardDataCache;
import com.sachin.risk.common.data.dao.RcCardDataMapper;
import com.sachin.risk.common.data.model.CardData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sachin.risk.common.core.enums.CardType;
import com.sachin.risk.common.util.cache.AbstractCacheLoader;

/**
 * 内卡银行卡归属地缓存
 * 依赖数据表更新时间
 * 不对删除数据更新缓存
 * 依赖CardAreaCache缓存
 *
 * @see com.sachin.risk.common.data.cache.CardAreaCache
 * @author shicheng.zhang
 * @since 17-8-3 下午9:23
 */
@Service
public class CardDataCacheLoader extends AbstractCacheLoader {

    private static final Logger logger = LoggerFactory.getLogger(CardDataCacheLoader.class);

    @Resource
    private RcCardDataMapper rcCardDataMapper;

    @Override
    protected void doLoad() {
        long start = System.currentTimeMillis();
        Date lastTime = rcCardDataMapper.queryMaxUpdateTime();
        if (lastTime == null) {
            return;
        }

        ConcurrentHashMap<Integer, Integer> patternLengthMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, String> cardPatternMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, CardType> cardTypeMap = CardAreaCache.getInstance().getCardTypeMap();
        if (MapUtils.isNotEmpty(cardTypeMap)) {
            for (Map.Entry<String, CardType> entry : cardTypeMap.entrySet()) {
                CardType cardType = entry.getValue();
                String bankCode = entry.getKey().split("~")[0];
                List<String> patternList = rcCardDataMapper.queryDistinctPattern(bankCode, cardType);
                if (CollectionUtils.isNotEmpty(patternList)) {
                    for (String pattern : patternList) {
                        cardPatternMap.put(pattern, bankCode + cardType.getCode());
                        if (!patternLengthMap.containsKey(pattern.length())) {
                            patternLengthMap.put(pattern.length(), pattern.length());
                        }
                    }
                }
            }
        }
        CardDataCache.getInstance().setCardPatternMap(cardPatternMap);
        CardDataCache.getInstance().setPatternLengthMap(patternLengthMap);
        CardDataCache.getInstance().setLastTime(lastTime);
        CardDataCache.getInstance().setLastCount(cardPatternMap.size());
        logger.info("CardDataCache cache load finished. size: {}, use time: {}", cardPatternMap.size(),
                System.currentTimeMillis() - start);
    }

    @Override
    public void reload() {
        // 1、缓存的上次更新时间为空，则进行全量加载
        long start = System.currentTimeMillis();
        if (CardDataCache.getInstance().getLastTime() == null) {
            doLoad();
            return;
        }
        // 2. 数据库最大更新时间和缓存上次更新时间相同，则不更新缓存
        Date lastTime = rcCardDataMapper.queryMaxUpdateTime();
        if (lastTime == null || CardDataCache.getInstance().getLastTime().equals(lastTime)) {
            logger.info("no need to reload CardAreaCache cache.");
            return;
        }
        // 3. 根据缓存的上次更新时间，查询大于等于此时间的所有记录更新缓存，并记录当前的数据库最大更新时间
        boolean updatePatternLen = false;
        List<CardData> cardDatas = rcCardDataMapper
                .queryDistinctGteUpdateTime(CardDataCache.getInstance().getLastTime());
        for (CardData cardData : cardDatas) {
            if (!CardDataCache.getInstance().getCardPatternMap().containsKey(cardData.getPattern())) {
                CardDataCache.getInstance().getCardPatternMap().put(cardData.getPattern(),
                        cardData.getBankCode() + cardData.getCardType().getCode());
            }
            if (!CardDataCache.getInstance().getPatternLengthMap()
                    .containsKey(cardData.getPattern().length())) {
                CardDataCache.getInstance().getPatternLengthMap().put(cardData.getPattern().length(),
                        cardData.getPattern().length());
                updatePatternLen = true;
            }
        }
        if (updatePatternLen) {
            CardDataCache.getInstance().setPatternLengthList();
        }
        CardDataCache.getInstance().setLastTime(lastTime);
        CardDataCache.getInstance().setLastCount(cardDatas.size());
        logger.info("CardDataCache cache reload finished. use time: {}", System.currentTimeMillis() - start);
    }
}
