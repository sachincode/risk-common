package com.sachin.risk.common.data.dao;

import java.util.Date;
import java.util.List;

import com.sachin.risk.common.data.model.CardArea;
import org.apache.ibatis.annotations.Param;


/**
 * @author shicheng.zhang
 * @since 17-8-3 下午9:18
 */
public interface RcCardAreaMapper {

    public List<CardArea> queryByLimit(@Param("id") Long id, @Param("pageSize") Integer pageSize);

    public List<CardArea> queryGteUpdateTime(@Param("updateTime") Date updateTime);

    public Date queryMaxUpdateTime();
}
