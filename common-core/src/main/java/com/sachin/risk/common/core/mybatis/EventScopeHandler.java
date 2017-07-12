package com.sachin.risk.common.core.mybatis;

import com.sachin.risk.common.core.enums.EventScope;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shicheng.zhang
 * @since 17-7-12 下午4:17
 */
public class EventScopeHandler implements TypeHandler<EventScope> {

    @Override
    public void setParameter(PreparedStatement ps, int i, EventScope parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public EventScope getResult(ResultSet rs, String columnName) throws SQLException {
        return EventScope.codeOf(rs.getInt(columnName));
    }

    @Override
    public EventScope getResult(ResultSet rs, int columnIndex) throws SQLException {
        return EventScope.codeOf(rs.getInt(columnIndex));
    }

    @Override
    public EventScope getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EventScope.codeOf(cs.getInt(columnIndex));
    }
}
