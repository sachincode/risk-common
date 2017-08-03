package com.sachin.risk.common.core.enums;

/**
 * 银行卡类型
 * @author shicheng.zhang
 * @since 17-8-3 下午8:08
 */
public enum CardType {

    DEBIT(0, "借记卡"),
    CREDIT(1, "贷记卡"),
    SEMI_CREDIT(2, "准贷记卡"),
    PREPAID(3, "预付费卡");

    private int code;
    private String name;

    CardType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static CardType codeOf(int code) {
        switch (code) {
        case 0:
            return DEBIT;
        case 1:
            return CREDIT;
        case 2:
            return SEMI_CREDIT;
        case 3:
            return PREPAID;
        default:
            return null;
        }
    }

    @Override
    public String toString() {
        return "CardType{" + "code=" + code + ", name='" + name + '\'' + '}';
    }
}
