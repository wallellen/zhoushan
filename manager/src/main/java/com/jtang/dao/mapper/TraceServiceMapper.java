package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.TraceService;

public class TraceServiceMapper implements RowMapper<TraceService> {

	@Override
	public TraceService mapRow(ResultSet res, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		TraceService tmp = new TraceService();
		tmp.setId(res.getInt("id"));
		tmp.setDataManager(res.getShort("dataManager"));
		tmp.setDataquery(res.getShort("dataquery"));
		tmp.setDatarecord(res.getShort("datarecord"));
		tmp.setMobileApp(res.getShort("mobileApp"));
		tmp.setProductionManager(res.getShort("productionManager"));
		tmp.setProductionStatistics(res.getShort("productionStatistics"));
		tmp.setRawmaterialManager(res.getShort("rawmaterialManager"));
	
		return tmp;
	}

}
