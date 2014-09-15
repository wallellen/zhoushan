package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.SaleRecord;

public class SaleRecordMapper implements RowMapper<SaleRecord>{

	@Override
	public SaleRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SaleRecord sr = new SaleRecord();
		sr.setEnterpriseId(rs.getString("enterpriseId"));
		sr.setFreezerNumber(rs.getString("freezerNumber"));
		sr.setSaleMarket(rs.getString("saleMarket"));
		sr.setSaleNumber(rs.getString("saleNumber"));
		sr.setSalePlace(rs.getString("salePlace"));
		sr.setSaleTime(rs.getString("saleTime"));
		return sr;
	}

}
