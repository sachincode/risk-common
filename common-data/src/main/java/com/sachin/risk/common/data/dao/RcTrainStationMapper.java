package com.sachin.risk.common.data.dao;

import java.util.Date;
import java.util.List;

import com.sachin.risk.common.data.model.TrainStation;

/**
 * @author shicheng.zhang
 * @since 17-7-25 下午6:18
 */
public interface RcTrainStationMapper {

    public List<TrainStation> queryAllTrainStation();

    public Date queryMaxUpdateTime();
}
