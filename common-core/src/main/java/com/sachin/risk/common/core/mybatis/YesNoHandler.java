package com.sachin.risk.common.core.mybatis;

import com.sachin.risk.common.core.enums.YesNo;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shicheng.zhang
 * @since 17-5-25 下午4:20
 */
public class YesNoHandler implements TypeHandler<YesNo> {

    @Override
    public void setParameter(PreparedStatement ps, int i, YesNo parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public YesNo getResult(ResultSet rs, String columnName) throws SQLException {
        return YesNo.codeOf(rs.getInt(columnName));
    }

    @Override
    public YesNo getResult(ResultSet rs, int columnIndex) throws SQLException {
        return YesNo.codeOf(rs.getInt(columnIndex));
    }

    @Override
    public YesNo getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return YesNo.codeOf(cs.getInt(columnIndex));
    }
}
