package com.sachin.risk.common.data.dao;

import java.util.Date;
import java.util.List;

import com.sachin.risk.common.data.model.IpData;
import org.apache.ibatis.annotations.Param;

/**
 * @author shicheng.zhang
 * @since 17-7-26 下午3:18
 */
public interface RcIpDataMapper {

    public List<IpData> queryByLimit(@Param("id") Long id, @Param("pageSize") Integer pageSize);

    public List<IpData> queryGteUpdateTime(@Param("updateTime") Date updateTime);

    public Date queryMaxUpdateTime();
}
