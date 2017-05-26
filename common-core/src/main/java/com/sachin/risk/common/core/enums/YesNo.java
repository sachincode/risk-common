package com.sachin.risk.common.core.enums;

/**
 * @author shicheng.zhang
 * @since 17-5-25 下午4:18
 */
public enum YesNo {

    NO(0, "否"), YES(1, "是");

    private int code;
    private String name;

    YesNo(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static YesNo codeOf(int code) {
        switch (code) {
        case 0:
            return NO;
        case 1:
            return YES;
        default:
            return null;
        }
    }

    @Override
    public String toString() {
        return "YesNo{" + "code=" + code + ", name='" + name + '\'' + '}';
    }
}
