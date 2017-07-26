package com.sachin.risk.common.data.model;

/**
 * @author shicheng.zhang
 * @since 17-7-24 下午4:46
 */
public class AirportCode {
    // 三字码
    private String airportCode;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;
    // 国家码
    private String countryCode;
    // 省份码
    private String provinceCode;

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public String toString() {
        return "AirportCode{" +
                "airportCode='" + airportCode + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                '}';
    }
}
