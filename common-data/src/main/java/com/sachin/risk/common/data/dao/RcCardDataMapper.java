package com.sachin.risk.common.data.dao;

import java.util.Date;
import java.util.List;

import com.sachin.risk.common.core.enums.CardType;
import com.sachin.risk.common.data.model.CardData;
import org.apache.ibatis.annotations.Param;


/**
 * @author shicheng.zhang
 * @since 17-8-4 上午10:18
 */
public interface RcCardDataMapper {

    public List<CardData> queryByLimit(@Param("id") Long id, @Param("pageSize") Integer pageSize);

    public List<CardData> queryGteUpdateTime(@Param("updateTime") Date updateTime);

    public Date queryMaxUpdateTime();

    public List<String> queryDistinctPattern(@Param("bankCode") String bankCode, @Param("cardType") CardType cardType);

    public List<CardData> queryDistinctGteUpdateTime(@Param("updateTime") Date updateTime);
}
