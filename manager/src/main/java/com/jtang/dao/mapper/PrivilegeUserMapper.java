package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.PrivilegeUser;

public class PrivilegeUserMapper implements RowMapper<PrivilegeUser>{

	public PrivilegeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		PrivilegeUser pu = new PrivilegeUser();
		pu.setPrivilegeName(rs.getString("privilege_name"));
		pu.setPrivilegeUser(rs.getString("user_id"));
		return pu;
	}
	
}
