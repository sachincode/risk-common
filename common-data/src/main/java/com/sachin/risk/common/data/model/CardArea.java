package com.sachin.risk.common.data.model;

import com.sachin.risk.common.core.enums.CardType;

/**
 * @author shicheng.zhang
 * @since 17-8-3 下午8:39
 */
public class CardArea {
    private Long id;
    /** 卡号地区串 **/
    private String cardAreaCode;
    /** 卡号地区串截取开始位 **/
    private Integer beginIndex;
    /** 卡号地区串截取结束位 **/
    private Integer endIndex;
    /** 银行卡类型 **/
    private CardType cardType;
    /** 银行代码 **/
    private String bankCode;
    /** 银行名称 **/
    private String bankName;
    /** 省 **/
    private String province;
    /** 市 **/
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardAreaCode() {
        return cardAreaCode;
    }

    public void setCardAreaCode(String cardAreaCode) {
        this.cardAreaCode = cardAreaCode;
    }

    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
        return "CardArea{" +
                "id=" + id +
                ", cardAreaCode='" + cardAreaCode + '\'' +
                ", beginIndex=" + beginIndex +
                ", endIndex=" + endIndex +
                ", cardType=" + cardType +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
