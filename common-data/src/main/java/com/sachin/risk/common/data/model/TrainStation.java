package com.sachin.risk.common.data.model;

/**
 * @author shicheng.zhang
 * @since 17-7-24 下午4:47
 */
public class TrainStation {
    // 车站
    private String station;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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
        return "TrainStation{" +
                "station='" + station + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
