package com.john.mybatis.config;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.john.mybatis.model.AddressEnum;

public class AddressEnumTypeHandle extends BaseTypeHandler<AddressEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, AddressEnum parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getIntValue());
	}

	@Override
	public AddressEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
	    int i = rs.getInt(columnName);
	    if (rs.wasNull()) {
	      return null;
	    } else {
	      try {
	        return AddressEnum.valueOf(i);
	      } catch (Exception ex) {
	        throw new IllegalArgumentException("Cannot convert " + i + " to " + columnName + " by ordinal value.", ex);
	      }
	    }
	}

	@Override
	public AddressEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int i = rs.getInt(columnIndex);
	    if (rs.wasNull()) {
	      return null;
	    } else {
	      try {
	    	  return AddressEnum.valueOf(i);
	      } catch (Exception ex) {
	        throw new IllegalArgumentException("Cannot convert " + i + " to "  + " by ordinal value.", ex);
	      }
	    }
	}

	@Override
	public AddressEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
	    if (cs.wasNull()) {
	      return null;
	    } else {
	      try {
	    	return AddressEnum.valueOf(i);
	      } catch (Exception ex) {
	        throw new IllegalArgumentException("Cannot convert " + i + " to " + " by ordinal value.", ex);
	      }
	    }
	  }

}
