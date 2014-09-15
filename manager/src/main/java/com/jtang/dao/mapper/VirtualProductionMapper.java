package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.VirtualProduction;


public class VirtualProductionMapper implements RowMapper<VirtualProduction>{

	public VirtualProduction mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Auto-generated method stub
		VirtualProduction vp = new VirtualProduction();
		vp.setEnterpriseId(rs.getString("enterpriseId"));
		vp.setProcessNumber(rs.getString("processNumber"));
		vp.setProductionCount(rs.getInt("productionCount"));
		vp.setProductionName(rs.getString("productionName"));
		vp.setQrCode(rs.getString("qrCode"));
		vp.setRfid(rs.getString("rfid"));
		vp.setSaleNumber(rs.getString("saleNumber"));
		vp.setStatus(rs.getInt("status"));
		vp.setTransNumber(rs.getString("transNumber"));
		return vp;
	}

}
