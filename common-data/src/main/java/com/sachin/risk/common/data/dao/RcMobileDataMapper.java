package com.sachin.risk.common.data.dao;

import java.util.Date;
import java.util.List;

import com.sachin.risk.common.data.model.MobileData;
import org.apache.ibatis.annotations.Param;

/**
 * @author shicheng.zhang
 * @since 17-7-26 下午3:18
 */
public interface RcMobileDataMapper {

    public List<MobileData> queryByLimit(@Param("id") Long id, @Param("pageSize") Integer pageSize);

    public List<MobileData> queryGteUpdateTime(@Param("updateTime") Date updateTime);

    public Date queryMaxUpdateTime();
}
