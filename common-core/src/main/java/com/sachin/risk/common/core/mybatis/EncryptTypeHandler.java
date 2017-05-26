package com.sachin.risk.common.core.mybatis;

import com.sachin.risk.common.core.enums.EncryptType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shicheng.zhang
 * @since 17-5-25 下午5:01
 */
public class EncryptTypeHandler implements TypeHandler<EncryptType> {
    @Override
    public void setParameter(PreparedStatement ps, int i, EncryptType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public EncryptType getResult(ResultSet rs, String columnName) throws SQLException {
        return EncryptType.codeOf(rs.getInt(columnName));
    }

    @Override
    public EncryptType getResult(ResultSet rs, int columnIndex) throws SQLException {
        return EncryptType.codeOf(rs.getInt(columnIndex));
    }

    @Override
    public EncryptType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EncryptType.codeOf(cs.getInt(columnIndex));
    }
}
