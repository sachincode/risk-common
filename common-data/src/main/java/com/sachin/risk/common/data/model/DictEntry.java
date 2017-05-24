package com.sachin.risk.common.data.model;

import java.util.Date;

/**
 * @author shicheng.zhang
 * @date 17-3-23
 * @time 下午1:13
 * @Description:
 */
public class DictEntry {

    /** 主键id **/
    private long id;
    /** 字典表id **/
    private long tableId;
    /** 编码 **/
    private String dictCode;
    /** 名称 **/
    private String dictName;
    /** 描述 **/
    private String dictDesc;
    /** 排序 **/
    private int dictSort;
    /** 创建人 **/
    private String createBy;
    /** 最后修改人 **/
    private String updateBy;
    /** 创建时间 **/
    private Date createTime;
    /** 更新时间 **/
    private Date updateTime;
    /** 扩展字段1 **/
    private String ext1;
    /** 扩展字段2 **/
    private String ext2;
    /** 扩展字段3 **/
    private String ext3;
    /** 扩展字段4 **/
    private String ext4;
    /** 扩展字段5 **/
    private String ext5;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public int getDictSort() {
        return dictSort;
    }

    public void setDictSort(int dictSort) {
        this.dictSort = dictSort;
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

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getExt5() {
        return ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }

    @Override
    public String toString() {
        return "DictEntry{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", dictCode='" + dictCode + '\'' +
                ", dictName='" + dictName + '\'' +
                ", dictDesc='" + dictDesc + '\'' +
                ", dictSort=" + dictSort +
                ", ext1='" + ext1 + '\'' +
                ", ext2='" + ext2 + '\'' +
                ", ext3='" + ext3 + '\'' +
                ", ext4='" + ext4 + '\'' +
                ", ext5='" + ext5 + '\'' +
                '}';
    }
}
