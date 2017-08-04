package com.sachin.risk.common.data.helper;

import com.sachin.risk.common.data.cache.CardAreaCache;
import com.sachin.risk.common.data.cache.CardDataCache;
import com.sachin.risk.common.data.model.CardArea;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 内卡卡归属地帮助类
 * 从缓存中根据卡号取归属地信息等
 *
 * @see com.sachin.risk.common.data.cache.CardDataCache
 * @see com.sachin.risk.common.data.cache.CardAreaCache
 * @author shicheng.zhang
 * @since 17-8-4 下午2:11
 */
public class CardDataHelper {

    /**
     * 从缓存中根据内卡卡号取归属地信息等
     * 可能会抛出异常，调用方需try并处理异常
     * @param cardNo
     * @return
     */
    private static CardArea getCardAreaFromCache(String cardNo) {
        List<Integer> patternLengthList = CardDataCache.getInstance().getPatternLengthList();
        if (CollectionUtils.isEmpty(patternLengthList)) {
            return null;
        }

        Integer i = patternLengthList.size();
        for (; i > 0; i--) {
            Integer length = patternLengthList.get(i - 1);
            if (cardNo.length() < length) {
                return null;
            }
            String cardPattern = cardNo.substring(0, length);
            String bankCodeAndCardType = CardDataCache.getInstance().getCardPatternMap().get(cardPattern);
            if (bankCodeAndCardType == null) {
                continue;
            }
            CardArea cut = CardAreaCache.getInstance().getCutMap().get(bankCodeAndCardType);
            if (null == cut) {
                continue;
            }

            String str = cardNo.substring(cut.getBeginIndex() - 1, cut.getEndIndex());
            CardArea cardArea = CardAreaCache.getInstance().getCardAreaMap().get(str);
            if (cardArea != null) {
                return cardArea;
            }
        }
        return null;
    }
}
