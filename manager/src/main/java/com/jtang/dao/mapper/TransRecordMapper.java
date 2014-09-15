package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.TransRecord;

public class TransRecordMapper implements RowMapper<TransRecord> {

	public TransRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
    	TransRecord m = new TransRecord();
    	m.setEndPlace(rs.getString("endPlace"));
    	m.setEndTime(rs.getString("endTime"));
    	m.setEnterpriseId(rs.getString("enterpriseId"));
    	m.setStartPlace(rs.getString("startPlace"));
    	m.setStartTime(rs.getString("startTime"));
    	m.setStatus(rs.getInt("status"));
    	m.setTransportNumber(rs.getString("transportNumber"));
    	m.setLicense(rs.getString("license"));
    	m.setDriver(rs.getString("driver"));
        return m;
    }
}
