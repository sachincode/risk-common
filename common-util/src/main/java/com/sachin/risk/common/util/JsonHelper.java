package com.sachin.risk.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sachin.risk.common.util.cache.AbstractCache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author shicheng.zhang
 * @since 17-8-14 下午6:22
 */
public class JsonHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);

    private JsonHelper() {
    }

    private static ObjectMapper buildBaseMapper(JsonInclude.Include include) {
        return new ObjectMapper().configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .setSerializationInclusion(include).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 创建输出全部属性到Json字符串的ObjectMapper.
     */
    public static ObjectMapper buildNormalMapper() {
        return buildBaseMapper(JsonInclude.Include.ALWAYS);
    }

    /**
     * 创建只输出非空属性到Json字符串的ObjectMapper.
     */
    public static ObjectMapper buildNonNullMapper() {
        return buildBaseMapper(JsonInclude.Include.NON_NULL);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的ObjectMapper.
     */
    public static ObjectMapper buildNonDefaultMapper() {
        return buildBaseMapper(JsonInclude.Include.NON_DEFAULT);
    }

    private static final ObjectMapper OBJECT_MAPPER = JsonHelper.buildNormalMapper()
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final Joiner JOINER = Joiner.on(".").skipNulls();


    /**
     * 将对象平铺
     * @param object 非Collection类型的对象
     * @param excludeProperties 过滤去除的属性字段
     * @return
     * @throws IOException
     */
    public static Map<String, Object> parse(Object object, List<String> excludeProperties)
            throws IOException {
        return parse(object, excludeProperties, null);
    }

    /**
     * 将对象平铺，挂在父节点key下
     * @param object 非Collection类型的对象
     * @param excludeProperties 过滤去除的属性字段
     * @param key
     * @return
     * @throws IOException
     */
    public static Map<String, Object> parse(Object object, List<String> excludeProperties, String key)
            throws IOException {
        if (object == null) {
            return Maps.newHashMap();
        }
        if (object instanceof Collection) {
            throw new IllegalArgumentException("object must not be Collection type");
        }
        String json = OBJECT_MAPPER.writeValueAsString(object);
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);
        Map<String, Object> rawOrig = Maps.newHashMap();
        parsePojoJsonNode(jsonNode.fields(), key, rawOrig, excludeProperties);
        return rawOrig;
    }

    /**
     * 将json（List）平铺
     * 此工程正常调用来说是ObjectPojo的json字符串传入，或者ObjectPojo组成的List的json字符串传入
     * 输出格式如下：
     * [{"a":{"a":1,"b":"c"},"b":{"a":1,"b":"c"}},{"a":{"a":1,"b":"c"},"b":{"a":1,"b":"c"}}]
     * -->
     * [{b.a=1, b.b=c, a.b=c, a.a=1}, {b.a=1, b.b=c, a.b=c, a.a=1}]
     *
     * 但为了处理非本工程需要的特殊情况，Array组成的List的json字符串输入
     * 输出格式如下：
     * [[{"a":1,"b":"c"},{"a":1,"b":"c"}],[["c","d"],["c","d"]]]
     * -->
     * [{[0][1].a=1, [0][1].b=c, [0]=2, [0][0].a=1, [0][0].b=c}, {[1][0][0]=c, [1][1][1]=d, [1][1]=2, [1][0]=2, [1][0][1]=d, [1][1][0]=c, [1]=2}]
     * 
     * @param json 要平铺数据的json格式字符串
     * @param excludeProperties 过滤去除的属性字段
     * @param key 父节点
     * @return 平铺后的List结果
     */
    public static List<Map<String, Object>> parseJsonString(String json, List<String> excludeProperties, String key) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        try {
            JsonNode rootNode = OBJECT_MAPPER.readTree(json);
            if (rootNode.isArray()) {
                for (int i = 0; i < rootNode.size(); i++) {
                    JsonNode jsonNode = rootNode.get(i);
                    String tmpKey = key;
                    if (jsonNode.isArray()) {
                        tmpKey = (key == null) ? ("[" + i + "]") : (key + ".[" + i + "]");
                    }
                    Map<String, Object> rawOrig = Maps.newHashMap();
                    parseJsonNode(jsonNode, tmpKey, rawOrig, excludeProperties);
                    resultList.add(rawOrig);
                }
            } else {
                Map<String, Object> rawOrig = Maps.newHashMap();
                parsePojoJsonNode(rootNode.fields(), key, rawOrig, excludeProperties);
                resultList.add(rawOrig);
            }
        } catch (Exception e) {
            LOGGER.error("parse json string error. json: {}", json, e);
        }
        return resultList;
    }

    /**
     * 递归平铺方法，平铺Object
     * 
     * @param iterator JsonNode的tree的第一层iterator
     * @param parentName 父节点名字
     * @param rawOrig 存放平铺结果的Map
     * @param excludeProperties 过滤去除的属性字段
     */
    private static void parsePojoJsonNode(Iterator<Map.Entry<String, JsonNode>> iterator, String parentName,
            Map<String, Object> rawOrig, List<String> excludeProperties) {
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            String key = entry.getKey();
            if (CollectionUtils.isNotEmpty(excludeProperties) && excludeProperties.contains(key)) {
                continue;
            }

            if (StringUtils.isNotBlank(parentName)) {
                key = JOINER.join(parentName, key);
            }
            parseJsonNode(entry.getValue(), key, rawOrig, excludeProperties);
        }
    }

    /**
     * 递归平铺方法，平铺Array
     * @param iterator jsonNode的迭代器
     * @param parentName 父节点名字
     * @param rawOrig 存放平铺结果的Map
     * @param excludeProperties 过滤去除的属性字段
     */
    private static void parseArrayJsonNode(Iterator<JsonNode> iterator, String parentName, Map<String, Object> rawOrig,
            List<String> excludeProperties) {
        List<JsonNode> jsonNodes = Lists.newArrayList(iterator);
        rawOrig.put(parentName, jsonNodes.size());
        for (int i = 0; i < jsonNodes.size(); i++) {
            String key = String.format("%s[%s]", parentName, i);
            parseJsonNode(jsonNodes.get(i), key, rawOrig, excludeProperties);
        }
    }

    private static void parseJsonNode(JsonNode jsonNode, String key, Map<String, Object> rawOrig,
            List<String> excludeProperties) {
        try {
            if (jsonNode.isNull()) {
                return;
            }
            if (jsonNode.isObject()) {
                parsePojoJsonNode(jsonNode.fields(), key, rawOrig, excludeProperties);
            } else if (jsonNode.isArray()) {
                parseArrayJsonNode(jsonNode.iterator(), key, rawOrig, excludeProperties);
            } else if (isLikePojoString(jsonNode.asText())) {
                JsonNode subJsonNode = OBJECT_MAPPER.readTree(jsonNode.asText());
                parsePojoJsonNode(subJsonNode.fields(), key, rawOrig, excludeProperties);
            } else if (isLikeArrayString(jsonNode.asText())) {
                JsonNode subJsonNode = OBJECT_MAPPER.readTree(jsonNode.asText());
                parseArrayJsonNode(subJsonNode.iterator(), key, rawOrig, excludeProperties);
            } else {
                Object value = rawOrig.get(key);
                if (null != value) {
                    List<String> tmpList = Lists.newArrayList();
                    if (value instanceof List) {
                        tmpList.addAll((List) value);
                    } else {
                        tmpList.add((String) value);
                    }
                    tmpList.add(jsonNode.asText());
                    rawOrig.put(key, tmpList);
                } else {
                    rawOrig.put(key, jsonNode.asText());
                }
            }
        } catch (Exception e) {
            LOGGER.error("parse pojo json string error. jsonNode: {}", jsonNode, e);
            if (e instanceof JsonParseException) {
                rawOrig.put(key, jsonNode.asText());
            }
        }
    }

    public static boolean isLikeArrayString(String value) {
        return value != null && value.startsWith("[") && value.endsWith("]");
    }

    public static boolean isLikePojoString(String value) {
        return value != null && value.startsWith("{") && value.endsWith("}");
    }

    public static boolean isLikeJsonString(String value) {
        return isLikePojoString(value) || isLikeArrayString(value);
    }

    public static void main(String[] args) throws IOException {
        String s = "[[\"a\",\"b\"],[\"c\",\"d\"]]";
        //s = "[{\"a\":1,\"b\":\"c\"},{\"a\":1,\"b\":\"c\"}]";
        s = "[[{\"a\":1,\"b\":\"c\"},{\"a\":1,\"b\":\"c\"}],[[\"c\",\"d\"],[\"c\",\"d\"]]]";
        //s = "{\"a\":{\"a\":1,\"b\":\"c\"},\"b\":{\"a\":1,\"b\":\"c\"}}";
        //s = "[{\"a\":{\"a\":1,\"b\":\"c\"},\"b\":{\"a\":1,\"b\":\"c\"}},{\"a\":{\"a\":1,\"b\":\"c\"},\"b\":{\"a\":1,\"b\":\"c\"}}]";
        List<Map<String, Object>> maps = JsonHelper.parseJsonString(s, null, null);
        System.out.println(maps);

        AbstractCache cache = new AbstractCache() {
            @Override public Date getLastTime() {
                return new Date();
            }
        };
        Map<String, Object> parse = JsonHelper.parse(cache, null);
        System.out.println(parse);
    }

}
