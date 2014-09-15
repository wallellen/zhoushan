package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.StoreRecord;

public class StoreRecordMapper implements RowMapper<StoreRecord> {

	 public StoreRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
     	StoreRecord m = new StoreRecord();
     	m.setId(rs.getInt("Id"));
     	m.setStorageId(rs.getInt("StorageId"));
     	m.setStartTime(rs.getDate("StartTime"));
     	m.setEndTime(rs.getDate("EndTime"));
     	m.setTemperature(rs.getFloat("Temperature"));
         return m;
     }
}
