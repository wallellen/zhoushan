package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.TempService;

public class TempServiceMapper implements RowMapper<TempService> {

	@Override
	public TempService mapRow(ResultSet res, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		TempService tmp = new TempService();
		tmp.setId(res.getInt("id"));
		tmp.setExceptionData(res.getShort("exceptionData"));
		tmp.setHistoryData(res.getShort("historyData"));
		tmp.setRealTimeData(res.getShort("realTimeData"));
		tmp.setStorageManager(res.getShort("realTimeData"));
		tmp.setSensorManager(res.getShort("sensorManager"));
		tmp.setTmDownload(res.getShort("tmDownload"));
	
		return tmp;
	}

}
