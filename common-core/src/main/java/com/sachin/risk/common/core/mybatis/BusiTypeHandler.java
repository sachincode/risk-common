package com.sachin.risk.common.core.mybatis;

import com.sachin.risk.common.core.enums.BusiType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shicheng.zhang
 * @since 17-5-24 下午5:58
 */
public class BusiTypeHandler implements TypeHandler<BusiType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, BusiType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public BusiType getResult(ResultSet rs, String columnName) throws SQLException {
        return BusiType.codeOf(rs.getInt(columnName));
    }

    @Override
    public BusiType getResult(ResultSet rs, int columnIndex) throws SQLException {
        return BusiType.codeOf(rs.getInt(columnIndex));
    }

    @Override
    public BusiType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return BusiType.codeOf(cs.getInt(columnIndex));
    }
}
