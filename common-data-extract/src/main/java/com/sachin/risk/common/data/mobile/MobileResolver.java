package com.sachin.risk.common.data.mobile;

import com.sachin.risk.common.data.Constants;
import com.sachin.risk.common.data.Resolver;
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
public class MobileResolver implements Resolver<MobileInfo> {

    private static final Logger logger = LoggerFactory.getLogger(MobileResolver.class);
    private static final Map<String, Resolver> mobileResolverMap = new LinkedHashMap<String, Resolver>();

    static {
        mobileResolverMap.put(Constants.C360, new Mobile360Resolver());
        mobileResolverMap.put(Constants.IP138, new MobileIp138Resolver());
    }

    /**
     * 获取手机号归属地等信息
     * 
     * @param mobilePrefix 手机号前缀前7位
     * @return
     */
    @Override
    public MobileInfo resolve(String mobilePrefix) {
        if (mobilePrefix == null || mobilePrefix.length() != 7) {
            throw new IllegalArgumentException("手机号错误: " + mobilePrefix);
        }
        String mockMobile = mobilePrefix + getRandomPostfixNumber();
        return getMobileInfo(mockMobile);
    }

    private MobileInfo getMobileInfo(String mobile) {
        for (Map.Entry<String, Resolver> entry : mobileResolverMap.entrySet()) {
            Object data = entry.getValue().resolve(mobile);
            if (data != null) {
                MobileInfo mobileInfo = (MobileInfo) data;
                logger.info("resolve mobile info success. mobile: {}, result: {}", mobile, mobileInfo);
                mobileInfo.setPrefix(mobile.substring(0, 7));
                return postProcessMobileInfo(mobileInfo);
            }
        }
        return null;
    }

    private MobileInfo postProcessMobileInfo(MobileInfo mobileInfo) {
        if (mobileInfo == null) {
            return null;
        }
        if (Constants.DIRECT_CITY_SET.contains(mobileInfo.getProvince())) {
            if (mobileInfo.getCity() == null || mobileInfo.getCity().length() == 0) {
                mobileInfo.setCity(mobileInfo.getProvince());
            }
        }
        if (Constants.SP_SET.contains(mobileInfo.getSp())) {
            mobileInfo.setSp("中国" + mobileInfo.getSp());
        }
        return mobileInfo;
    }

    /**
     * 随机生成四位数字字符串
     * 
     * @return
     */
    private String getRandomPostfixNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
