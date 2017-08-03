package com.sachin.risk.common.data.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.sachin.risk.common.core.enums.CardType;
import com.sachin.risk.common.data.model.CardArea;

import com.sachin.risk.common.util.cache.AbstractCache;

/**
 * 银行卡地区归属地缓存
 * 
 * @author shicheng.zhang
 * @since 17-8-3 下午9:26
 */
public class CardAreaCache extends AbstractCache {

    // <cardAreaCode, CardArea>
    private ConcurrentHashMap<String, CardArea> cardAreaMap = new ConcurrentHashMap<>();
    // <bank_code+card_type,CardAreaData>
    private ConcurrentHashMap<String, CardArea> cutMap = new ConcurrentHashMap<>();
    // <"bank_code"+"~"+"card_type","card_type">
    private ConcurrentHashMap<String, CardType> cardTypeMap = new ConcurrentHashMap<>();

    private CardAreaCache() {
    }

    private static class CardAreaCacheCacheHolder {
        private static final CardAreaCache instance = new CardAreaCache();
    }

    public static CardAreaCache getInstance() {
        return CardAreaCacheCacheHolder.instance;
    }

    public ConcurrentHashMap<String, CardArea> getCardAreaMap() {
        return cardAreaMap;
    }

    public void setCardAreaMap(ConcurrentHashMap<String, CardArea> cardAreaMap) {
        this.cardAreaMap = cardAreaMap;
    }

    public ConcurrentHashMap<String, CardArea> getCutMap() {
        return cutMap;
    }

    public void setCutMap(ConcurrentHashMap<String, CardArea> cutMap) {
        this.cutMap = cutMap;
    }

    public ConcurrentHashMap<String, CardType> getCardTypeMap() {
        return cardTypeMap;
    }

    public void setCardTypeMap(ConcurrentHashMap<String, CardType> cardTypeMap) {
        this.cardTypeMap = cardTypeMap;
    }
}
