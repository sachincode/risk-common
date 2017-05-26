package com.sachin.risk.common.core.model;

import com.sachin.risk.common.core.enums.EncryptType;

import java.util.Date;

/**
 * @author shicheng.zhang
 * @since 17-5-26 下午5:10
 */
public class EventTypeProperty {
    /** 主键id **/
    private long id;
    /** 事件类型id **/
    private long eventTypeId;
    /** 事件属性id **/
    private long eventPropId;
    /** 加密类型0无1phone2mail3bankno4personalid5passport **/
    private EncryptType encryptType;
    /** 排序序号 **/
    private int sortNumber;
    /** 字典表解码表名 **/
    private String dictTableName;
    /** 状态:1上线2下线 **/
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

    public long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public long getEventPropId() {
        return eventPropId;
    }

    public void setEventPropId(long eventPropId) {
        this.eventPropId = eventPropId;
    }

    public EncryptType getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(EncryptType encryptType) {
        this.encryptType = encryptType;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getDictTableName() {
        return dictTableName;
    }

    public void setDictTableName(String dictTableName) {
        this.dictTableName = dictTableName;
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
        return "EventTypeProperty{" +
                "status=" + status +
                ", id=" + id +
                ", eventTypeId=" + eventTypeId +
                ", eventPropId=" + eventPropId +
                ", encryptType=" + encryptType +
                ", sortNumber=" + sortNumber +
                ", dictTableName='" + dictTableName + '\'' +
                '}';
    }
}
