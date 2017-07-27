package com.sachin.risk.common.data.model;

/**
 * @author shicheng.zhang
 * @since 17-7-24 下午4:45
 */
public class PersonalIdData {

    // 前缀6位
    private String prefix;
    // 省份
    private String province;
    // 城市
    private String city;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "PersonalIdData{" +
                "prefix='" + prefix + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
