package com.sachin.risk.common.core.enums;

/**
 * @author shicheng.zhang
 * @since 17-7-12 下午4:04
 */
public enum EventScope {

    NONE(0, null, "非法"),
    SYNC(1, "sync", "同步事件"),
    ASYNC(2, "async", "异步事件");

    private int code;
    private String name;
    private String description;

    EventScope(int code, String name, String description) {
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

    public static EventScope codeOf(int code) {
        switch (code) {
        case 1:
            return SYNC;
        case 2:
            return ASYNC;
        default:
            return NONE;
        }
    }

    @Override
    public String toString() {
        return "EventScope{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
