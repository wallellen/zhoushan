package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.jtang.model.Privilege;

public class PrivilegeMapper implements RowMapper<Privilege> {

	public Privilege mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		Privilege m = new Privilege();
		m.setName(rs.getString("privilege_name"));
		m.setLevel(rs.getInt("level"));
		m.setComment(rs.getString("comment"));
		m.setParent(rs.getString("parent"));
		return m;
	}

}
