package com.sachin.risk.common.core.mybatis;

import com.sachin.risk.common.core.enums.DataType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shicheng.zhang
 * @since 17-5-24 下午5:56
 */
public class DataTypeHandler implements TypeHandler<DataType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, DataType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public DataType getResult(ResultSet rs, String columnName) throws SQLException {
        return DataType.codeOf(rs.getInt(columnName));
    }

    @Override
    public DataType getResult(ResultSet rs, int columnIndex) throws SQLException {
        return DataType.codeOf(rs.getInt(columnIndex));
    }

    @Override
    public DataType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return DataType.codeOf(cs.getInt(columnIndex));
    }
}
