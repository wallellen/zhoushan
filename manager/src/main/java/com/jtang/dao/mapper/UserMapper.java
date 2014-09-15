package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.User;

public class UserMapper implements RowMapper<User> {

	 public User mapRow(ResultSet rs, int rowNum) throws SQLException {
         User m = new User();
         m.setId(rs.getString("id"));
         m.setName(rs.getString("name"));
         m.setPasswd(rs.getString("password"));
         m.setPositionId(rs.getInt("positionId"));
         m.setSex(rs.getInt("sex"));
         m.setEnterpriseId(rs.getString("enterpriseId"));
         m.setStorageList(rs.getString("storageList"));
         m.setDefaultStorageId(rs.getInt("defaultStorage"));
         m.setPrivilege(rs.getInt("privilege"));
         return m;
     }
}
