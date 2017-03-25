package com.sachin.risk.common.data.model;

import java.util.Date;

/**
 * @author shicheng.zhang
 * @date 17-3-23
 * @time 下午12:16
 * @Description:
 */
public class DictTable {

    /** 字典表id **/
    private long id;
    /** 字典表名 **/
    private String tableName;
    /** 字典表描述 **/
    private String tableDesc;
    /** 字典表类型:1.通用字典表2.列表字典表 **/
    private int tableType;
    /** 创建人 **/
    private String createBy;
    /** 最后修改人 **/
    private String updateBy;
    /** 创建时间 **/
    private Date createTime;
    /** 最后更新时间 **/
    private Date updateTime;
    /** 字典表中文名 **/
    private String tableNameCn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public int getTableType() {
        return tableType;
    }

    public void setTableType(int tableType) {
        this.tableType = tableType;
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

    public String getTableNameCn() {
        return tableNameCn;
    }

    public void setTableNameCn(String tableNameCn) {
        this.tableNameCn = tableNameCn;
    }

    @Override
    public String toString() {
        return "DictTable{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", tableType=" + tableType +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", tableNameCn='" + tableNameCn + '\'' +
                '}';
    }
}
