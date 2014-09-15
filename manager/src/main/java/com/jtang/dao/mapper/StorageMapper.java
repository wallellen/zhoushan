package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Storage;

public class StorageMapper implements RowMapper<Storage> {

	public Storage mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Storage s = new Storage();
    	s.setId(rs.getInt("id")); 
    	s.setLength(rs.getInt("length"));
    	s.setWidth(rs.getInt("width"));
    	s.setHigh(rs.getInt("high"));
    	s.setCapacity(rs.getInt("capacity"));
    	s.setDescription(rs.getString("description"));
    	s.setCreator(rs.getString("creator"));
    	s.setCreateTime(rs.getString("createTime"));
    	s.setName(rs.getString("name"));
    	s.setEnterpriseId(rs.getString("enterpriseId"));
    	s.setUsed(rs.getInt("used"));
    	s.setMaxTemp(rs.getFloat("maxTemp"));
    	s.setMinTemp(rs.getFloat("minTemp"));
        return s;
    }
	
}
