package com.sachin.risk.common.core.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.sachin.risk.common.core.enums.CardType;

/**
 * @author shicheng.zhang
 * @since 17-8-3 下午8:56
 */
public class CardTypeHandler implements TypeHandler<CardType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, CardType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public CardType getResult(ResultSet rs, String columnName) throws SQLException {
        return CardType.codeOf(rs.getInt(columnName));
    }

    @Override
    public CardType getResult(ResultSet rs, int columnIndex) throws SQLException {
        return CardType.codeOf(rs.getInt(columnIndex));
    }

    @Override
    public CardType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CardType.codeOf(cs.getInt(columnIndex));
    }
}
