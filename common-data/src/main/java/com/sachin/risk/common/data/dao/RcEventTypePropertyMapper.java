package com.sachin.risk.common.data.dao;

import com.sachin.risk.common.data.model.EventTypeProperty;

import java.util.Date;
import java.util.List;

/**
 * @author shicheng.zhang
 * @since 17-7-18 下午8:17
 */
public interface RcEventTypePropertyMapper {

    public Date queryMaxUpdateTime();

    public List<EventTypeProperty> queryAll();
}
