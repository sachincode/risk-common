package com.sachin.risk.common.data.idnumber;

import com.sachin.risk.common.data.Constants;
import com.sachin.risk.common.data.Resolver;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 上午11:53
 * @Description:
 */
public class IdNumberResolver implements Resolver<IdNumberInfo> {

    private static final Logger logger = LoggerFactory.getLogger(IdNumberResolver.class);
    private static final Map<String, Resolver> idNumberResolverMap = new LinkedHashMap<String, Resolver>();

    private int[] weight = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; // 十七位数字本体码权重
    private char[] validate = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // mod11,对应校验码字符值

    static {
        idNumberResolverMap.put(Constants.K780, new IdNumberK780Resolver());
    }

    @Override
    public IdNumberInfo resolve(String param) {
        return getIdNumberInfo(param);
    }

    private IdNumberInfo getIdNumberInfo(String prefix) {
        String idNumber = getRandomIdNumber(prefix);
        for (Map.Entry<String, Resolver> entry : idNumberResolverMap.entrySet()) {
            Object data = entry.getValue().resolve(idNumber);
            if (data != null) {
                IdNumberInfo idNumberInfo = (IdNumberInfo) data;
                logger.info("resolve id number info success. prefix: {}, result: {}", prefix, idNumberInfo);
                return idNumberInfo;
            }
        }
        return null;
    }

    private String getRandomIdNumber(String prefix) {
        if (prefix == null || prefix.length() != 6) {
            throw new IllegalArgumentException("错误的身份证号码前缀. prefix: " + prefix);
        }
        String id17 = prefix + getRandomPostfixNumber();
        return id17 + getValidateCode(id17);
    }

    private char getValidateCode(String id17) {
        int sum = 0;
        int mode = 0;
        for (int i = 0; i < id17.length(); i++) {
            sum = sum + Integer.parseInt(String.valueOf(id17.charAt(i))) * weight[i];
        }
        mode = sum % 11;
        return validate[mode];
    }

    private String getRandomPostfixNumber() {
        String birthday = getRandomBirthday();
        Random random = new Random();
        int order = random.nextInt(900) + 100;
        return birthday + order;
    }

    private String getRandomBirthday() {
        Random random = new Random();
        DateTime now = new DateTime();
        return now.minusDays(random.nextInt(10000) + 1).toString("yyyyMMdd");
    }

    public static void main(String[] args) {
        IdNumberResolver resolver = new IdNumberResolver();
        for (int i = 0; i < 100; i++) {
            System.out.println(resolver.getRandomIdNumber("371424"));
        }
        System.out.println();
        System.out.println(resolver.getValidateCode("37142419901207153"));

        IdNumberInfo idNumberInfo = resolver.resolve("371424");
        System.out.println(idNumberInfo);
    }
}
