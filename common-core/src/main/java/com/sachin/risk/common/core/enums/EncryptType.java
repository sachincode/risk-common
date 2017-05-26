package com.sachin.risk.common.core.enums;

/**
 * @author shicheng.zhang
 * @since 17-5-25 下午4:57
 */
public enum EncryptType {

    NONE(0, "none", "无"),
    PHONE(1, "phone", "手机号"),
    EMAIL(2, "email", "邮箱"),
    BANK_NO(3, "bank_no", "银行卡号"),
    PERSONAL_ID(4, "personal_id", "身份证号"),
    PASSPORT(5, "passport", "护照");

    private int code;
    private String name;
    private String description;

    EncryptType(int code, String name, String description) {
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

    public static EncryptType codeOf(int code) {
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
        default:
            return null;
        }
    }

    public static EncryptType nameOf(String name) {
        for (EncryptType encryptType : values()) {
            if (encryptType.name.equals(name)) {
                return encryptType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EncryptType{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
