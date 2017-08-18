package com.sachin.risk.common.core.enums;

/**
 * java数据类型
 * @author shicheng.zhang
 * @since 17-5-24 下午5:08
 */
public enum DataType {

    STRING(0, "字符串"),
    LONG(1, "整型"),
    DOUBLE(2, "浮点型"),
    BOOLEAN(3, "布尔型"),
    DATE(4, "日期");

    private int code;
    private String name;

    DataType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DataType codeOf(int code) {
        switch (code) {
        case 0:
            return STRING;
        case 1:
            return LONG;
        case 2:
            return DOUBLE;
        case 3:
            return BOOLEAN;
        case 4:
            return DATE;
        default:
            return null;
        }
    }

    @Override
    public String toString() {
        return "DataType{" + "code=" + code + ", name='" + name + '\'' + '}';
    }
}
