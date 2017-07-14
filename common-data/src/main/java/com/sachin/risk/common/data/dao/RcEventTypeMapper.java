package com.sachin.risk.common.data.dao;

import com.sachin.risk.common.core.model.EventType;

import java.util.Date;
import java.util.List;

/**
 * @author shicheng.zhang
 * @since 17-7-14 下午6:18
 */
public interface RcEventTypeMapper {

    public List<EventType> queryAllEventType();

    public Date queryMaxUpdateTime();
}
