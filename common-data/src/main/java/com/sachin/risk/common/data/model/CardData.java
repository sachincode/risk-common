package com.sachin.risk.common.data.model;

import com.sachin.risk.common.core.enums.CardType;

/**
 * @author shicheng.zhang
 * @since 17-8-4 上午9:39
 */
public class CardData {
    private Long id;
    /** 卡号前缀(包含地区码) **/
    private Long cardPrefix;
    /** 卡号长度 **/
    private Integer cardLength;
    /** 卡名 **/
    private String cardName;
    /** 银行卡类型 **/
    private CardType cardType;
    /** 主账号 **/
    private String binCode;
    /** 发卡行机构代码 **/
    private String bankNo;
    /** 银行代码 **/
    private String bankCode;
    /** 银行名称 **/
    private String bankName;
    /** 起始格式(不包含地区码) **/
    private String pattern;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardPrefix() {
        return cardPrefix;
    }

    public void setCardPrefix(Long cardPrefix) {
        this.cardPrefix = cardPrefix;
    }

    public Integer getCardLength() {
        return cardLength;
    }

    public void setCardLength(Integer cardLength) {
        this.cardLength = cardLength;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "CardData{" +
                "id=" + id +
                ", cardPrefix=" + cardPrefix +
                ", cardLength=" + cardLength +
                ", cardName='" + cardName + '\'' +
                ", cardType=" + cardType +
                ", binCode='" + binCode + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}
