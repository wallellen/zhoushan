package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Enterprise;

public class EnterpriseMapper implements RowMapper<Enterprise> {

	@Override
	public Enterprise mapRow(ResultSet res, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		Enterprise enter = new Enterprise();
		enter.setAddress(res.getString("address"));
		enter.setDescription(res.getString("description"));
		enter.setEmail(res.getString("email"));
		enter.setId(res.getString("id"));
		enter.setName(res.getString("name"));
		enter.setPhone(res.getString("phone"));
		enter.setStorageService(res.getInt("storageService"));
		enter.setTraceService(res.getInt("traceService"));
		enter.setTempService(res.getInt("tempService"));
		enter.setRegisterTime(res.getString("registerTime"));
		enter.setWithdrawTime(res.getString("withdrawTime"));
		enter.setIsDue(res.getInt("isDue"));
		enter.setIsValid(res.getInt("isValid"));
		return enter;
	}

}
