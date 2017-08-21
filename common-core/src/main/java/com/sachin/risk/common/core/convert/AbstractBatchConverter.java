package com.sachin.risk.common.core.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:20
 */
public abstract class AbstractBatchConverter<T> implements Converter<T> {

    public List<T> convertBatch(Collection<Object> sourceList) {
        if (sourceList == null) {
            return null;
        }
        List<T> resultList = new ArrayList<>();
        for (Object s : sourceList) {
            resultList.add(convert(s));
        }
        return resultList;
    }
}
