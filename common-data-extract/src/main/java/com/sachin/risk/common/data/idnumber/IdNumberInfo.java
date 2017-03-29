package com.sachin.risk.common.data.idnumber;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 下午8:38
 * @Description:
 */
public class IdNumberInfo {

    private String prefix;
    private String province;
    private String city;

    private String source;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "IdNumberInfo{" +
                "prefix='" + prefix + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
