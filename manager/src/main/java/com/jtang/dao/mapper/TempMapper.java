package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Temperature;

public class TempMapper implements RowMapper<Temperature> {

	public Temperature mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Temperature t = new Temperature();
    	t.setSensorExtAddr(rs.getString("sensorExtAddr"));
    	t.setRecordTime(rs.getString("recordTime"));
    	t.setTemperature(rs.getFloat("temperature"));        	
        return t;
    }
}
