package com.sachin.risk.common.data.mobile;

/**
 * @author shicheng.zhang
 * @date 17-3-28
 * @time 下午3:24
 * @Description:
 */
public class MobileInfo {

    private String prefix;
    /** 省 **/
    private String province;
    /** 市 **/
    private String city;
    /** 运营商 **/
    private String sp;
    /** 卡类型 **/
    private String card;
    /** 解析来源 **/
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

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "MobileInfo{" +
                "prefix='" + prefix + '\'' +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", sp='" + sp + '\'' +
                ", card='" + card + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
