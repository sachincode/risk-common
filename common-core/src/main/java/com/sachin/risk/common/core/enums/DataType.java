package com.sachin.risk.common.core.enums;

/**
 * java数据类型
 * @author shicheng.zhang
 * @since 17-5-24 下午5:08
 */
public enum DataType {

    STRING(0, "字符串"),
    INTEGER(1, "整形"),
    LONG(2, "长整型"),
    DOUBLE(3, "浮点型"),
    BOOLEAN(4, "布尔型"),
    DATE(5, "日期");

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
            return INTEGER;
        case 2:
            return LONG;
        case 3:
            return DOUBLE;
        case 4:
            return BOOLEAN;
        case 5:
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
