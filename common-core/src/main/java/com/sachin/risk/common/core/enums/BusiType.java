package com.sachin.risk.common.core.enums;

/**
 * 业务数据类型
 * @author shicheng.zhang
 * @since 17-5-24 下午5:08
 */
public enum BusiType {

    NONE(0, "none", "无"),
    PHONE(1, "phone", "手机号"),
    EMAIL(2, "email", "邮箱"),
    BANK_NO(3, "bank_no", "银行卡号"),
    PERSONAL_ID(4, "personal_id", "身份证号"),
    PASSPORT(5, "passport", "护照"),
    IP(6, "ip", "ip"),
    GPS(7, "gps", "gps");

    private int code;
    private String name;
    private String description;

    BusiType(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static BusiType codeOf(int code) {
        switch (code) {
        case 0:
            return NONE;
        case 1:
            return PHONE;
        case 2:
            return EMAIL;
        case 3:
            return BANK_NO;
        case 4:
            return PERSONAL_ID;
        case 5:
            return PASSPORT;
        case 6:
            return IP;
        case 7:
            return GPS;
        default:
            return null;
        }
    }

    public static BusiType nameOf(String name) {
        for (BusiType busiType : values()) {
            if (busiType.name.equals(name)) {
                return busiType;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "BusiType{" + "code=" + code + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
