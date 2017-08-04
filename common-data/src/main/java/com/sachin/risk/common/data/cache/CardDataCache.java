package com.sachin.risk.common.data.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sachin.risk.common.util.cache.AbstractCache;

/**
 * 内卡银行卡地区归属地缓存
 * 
 * @author shicheng.zhang
 * @since 17-8-4 上午10:26
 */
public class CardDataCache extends AbstractCache {

    // <pattern.Length,pattern.Length>
    ConcurrentHashMap<Integer, Integer> patternLengthMap = new ConcurrentHashMap<>();
    // <pattern,bank_code+card_type>
    ConcurrentHashMap<String, String> cardPatternMap = new ConcurrentHashMap<>();
    List<Integer> patternLengthList = new ArrayList<>();

    private CardDataCache() {
    }

    private static class CardDataCacheCacheHolder {
        private static final CardDataCache instance = new CardDataCache();
    }

    public static CardDataCache getInstance() {
        return CardDataCacheCacheHolder.instance;
    }

    public ConcurrentHashMap<Integer, Integer> getPatternLengthMap() {
        return patternLengthMap;
    }

    public void setPatternLengthMap(ConcurrentHashMap<Integer, Integer> patternLengthMap) {
        this.patternLengthMap = patternLengthMap;
        setPatternLengthList();
    }

    public ConcurrentHashMap<String, String> getCardPatternMap() {
        return cardPatternMap;
    }

    public void setCardPatternMap(ConcurrentHashMap<String, String> cardPatternMap) {
        this.cardPatternMap = cardPatternMap;
    }

    public List<Integer> getPatternLengthList() {
        return patternLengthList;
    }

    public void setPatternLengthList(List<Integer> patternLengthList) {
        this.patternLengthList = patternLengthList;
    }

    public void setPatternLengthList() {
        List<Integer> patterns = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : patternLengthMap.entrySet()) {
            patterns.add(entry.getKey());
        }
        Collections.sort(patterns);
        this.patternLengthList = patterns;
    }
}
