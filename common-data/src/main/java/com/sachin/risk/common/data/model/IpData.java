package com.sachin.risk.common.data.model;

/**
 * @author shicheng.zhang
 * @since 17-7-24 下午4:42
 */
public class IpData {
    private Long id;
    // 起始ip
    private Long startIp;
    // 结束ip
    private Long endIp;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartIp() {
        return startIp;
    }

    public void setStartIp(Long startIp) {
        this.startIp = startIp;
    }

    public Long getEndIp() {
        return endIp;
    }

    public void setEndIp(Long endIp) {
        this.endIp = endIp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        return "IpData{" +
                "id=" + id +
                ", startIp=" + startIp +
                ", endIp=" + endIp +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
