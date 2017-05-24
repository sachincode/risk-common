package com.sachin.risk.common.core.model;

import com.sachin.risk.common.core.enums.BusiType;
import com.sachin.risk.common.core.enums.DataType;

import java.util.Date;

/**
 * @author shicheng.zhang
 * @since 17-5-24 下午4:55
 */
public class EventProperty {
    /** 主键id **/
    private long id;
    /** 属性key **/
    private String propKey;
    /** 属性名 **/
    private String propName;
    /** 描述 **/
    private String description;
    /** 数据类型 **/
    private DataType dataType;
    /** 业务类型 **/
    private BusiType busiType;
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

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "EventProperty{" +
                "status=" + status +
                ", id=" + id +
                ", propKey='" + propKey + '\'' +
                ", propName='" + propName + '\'' +
                ", description='" + description + '\'' +
                ", dataType=" + dataType +
                ", busiType=" + busiType +
                '}';
    }
}
