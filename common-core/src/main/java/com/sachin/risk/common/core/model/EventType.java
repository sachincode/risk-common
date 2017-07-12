package com.sachin.risk.common.core.model;

import com.sachin.risk.common.core.enums.EventScope;

import java.util.Date;

/**
 * @author shicheng.zhang
 * @since 17-4-16 下午10:22
 */
public class EventType {
    /** 主键id **/
    private long id;
    /** 事件类型编码 **/
    private String code;
    /** 事件类型名称 **/
    private String name;
    /** 描述 **/
    private String description;
    /** 事件类型:1同步2异步 **/
    private EventScope type;
    /** 模块 **/
    private String module;
    /** 事件类型:1上线2下线 **/
    private int status;
    /** 创建人 **/
    private String createBy;
    /** 最后修改人 **/
    private String updateBy;
    /** 创建时间 **/
    private Date createTime;
    /** 最后更新时间 **/
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventScope getType() {
        return type;
    }

    public void setType(EventScope type) {
        this.type = type;
    }

    public boolean isSync() {
        return type == EventScope.SYNC;
    }

    public boolean isAsync() {
        return type == EventScope.ASYNC;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type.getName() +
                ", module='" + module + '\'' +
                ", status=" + status +
                '}';
    }
}
