package com.sachin.risk.common.data.model;

/**
 * @author shicheng.zhang
 * @since 17-7-24 下午4:40
 */
public class MobileData {
    private Long id;
    // 前缀7位
    private String prefix;
    // 省份
    private String province;
    // 城市
    private String city;
    // 卡类型
    private String cardType;
    // 运营商
    private String ownership;

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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MobileData{" +
                "id=" + id +
                ", prefix='" + prefix + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", cardType='" + cardType + '\'' +
                ", ownership='" + ownership + '\'' +
                '}';
    }
}
