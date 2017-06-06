package com.sachin.risk.common.data.dao;

import com.sachin.risk.common.data.model.DictEntry;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author shicheng.zhang
 * @since 17-4-27 上午11:12
 */
public interface RcDictEntryMapper {

    public Date queryMaxUpdateTime();

    public List<DictEntry> queryAll();

    public List<DictEntry> queryByTableId(@Param("tableId")long tableId);
}
