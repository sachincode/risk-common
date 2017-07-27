package com.sachin.risk.common.data.dao;

import java.util.Date;
import java.util.List;

import com.sachin.risk.common.data.model.PersonalIdData;
import org.apache.ibatis.annotations.Param;


/**
 * @author shicheng.zhang
 * @since 17-7-26 下午3:18
 */
public interface RcPersonalIdDataMapper {

    public List<PersonalIdData> queryAllPersonalIdData();

    public List<PersonalIdData> queryGteUpdateTime(@Param("updateTime") Date updateTime);

    public Date queryMaxUpdateTime();
}
