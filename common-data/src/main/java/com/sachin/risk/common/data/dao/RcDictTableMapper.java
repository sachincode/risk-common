package com.sachin.risk.common.data.dao;

import com.sachin.risk.common.data.model.DictTable;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author shicheng.zhang
 * @since 17-4-27 上午11:11
 */
public interface RcDictTableMapper {

    public Date queryMaxUpdateTime();

    public List<DictTable> queryAll();

    public List<DictTable> queryGTEUpdateTime(@Param("updateTime")Date updateTime);
}
