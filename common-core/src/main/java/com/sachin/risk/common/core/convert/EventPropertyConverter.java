package com.sachin.risk.common.core.convert;

import com.sachin.risk.common.core.enums.BusiType;
import com.sachin.risk.common.core.enums.DataType;
import com.sachin.risk.common.core.exception.ConvertException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shicheng.zhang
 * @since 17-8-18 下午3:31
 */
public class EventPropertyConverter {

    private static final Map<DataType, AbstractBatchConverter> dataTypeConverters = new HashMap<>();
    private static final Map<BusiType, AbstractBatchConverter> busiTypeConverters = new HashMap<>();

    static {
        dataTypeConverters.put(DataType.STRING, new StringConverter());
        dataTypeConverters.put(DataType.LONG, new LongConverter());
        dataTypeConverters.put(DataType.DOUBLE, new DoubleConverter());
        dataTypeConverters.put(DataType.BOOLEAN, new BooleanConverter());
        dataTypeConverters.put(DataType.DATE, new DateConverter());

        busiTypeConverters.put(BusiType.IP, new IpLongConverter());
    }

    /**
     * 事件属性转换，json平铺后数据一般为String或List<String>的类型，
     * 需要根据事件属性的配置将其转化为真正的类型，如Long, Double等
     *
     * @param source
     * @param dataType
     * @param busiType
     * @return
     * @throws ConvertException 类型转换过程中可能会抛出异常，需要捕获
     */
    public static Object converter(Object source, DataType dataType, BusiType busiType) throws ConvertException {
        if (source == null) {
            return null;
        }
        AbstractBatchConverter converter = busiTypeConverters.get(busiType);
        if (converter == null) {
            converter = dataTypeConverters.get(dataType);
            if (converter == null) {
                throw new ConvertException("Error DataType " + dataType.getName() + "and BusiType " + busiType.getName());
            }
        }

        try {
            if (source instanceof Collection) {
                return converter.convertBatch((Collection) source);
            } else {
                return converter.convert(source);
            }
        } catch (Exception e) {
            throw new ConvertException("convert property error.", e);
        }
    }
}
