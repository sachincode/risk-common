package com.sachin.risk.common.data.model;

import com.sachin.risk.common.core.enums.BusiType;
import com.sachin.risk.common.core.enums.DataType;
import com.sachin.risk.common.core.enums.EncryptType;

/**
 * @author shicheng.zhang
 * @since 17-7-12 下午5:00
 */
public class EventTypeProperty {

    /** 事件类型id **/
    private long eventTypeId;

    /** 事件属性id **/
    private long eventPropId;
    /** 属性key **/
    private String eventPropKey;
    /** 属性名 **/
    private String eventPropName;
    /** 数据类型 **/
    private DataType dataType;
    /** 业务类型 **/
    private BusiType busiType;

    private long id;
    /** 加密类型0无1phone2mail3bankno4personalid5passport **/
    private EncryptType encryptType;
    /** 排序序号 **/
    private int sortNumber;
    /** 字典表解码表名 **/
    private String dictTableName;
    /** 状态:1上线2下线 **/
    private int status;

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

    public String getEventPropKey() {
        return eventPropKey;
    }

    public void setEventPropKey(String eventPropKey) {
        this.eventPropKey = eventPropKey;
    }

    public String getEventPropName() {
        return eventPropName;
    }

    public void setEventPropName(String eventPropName) {
        this.eventPropName = eventPropName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public BusiType getBusiType() {
        return busiType;
    }

    public void setBusiType(BusiType busiType) {
        this.busiType = busiType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
