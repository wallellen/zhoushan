package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.StorageService;

public class StorageServiceMapper implements RowMapper<StorageService> {

	@Override
	public StorageService mapRow(ResultSet res, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		StorageService tmp = new StorageService();
		tmp.setId(res.getInt("id"));
		tmp.setInoutService(res.getShort("inoutService"));
		tmp.setInventoryService(res.getShort("inventoryService"));
		tmp.setRepPreviwService(res.getShort("repPreviwService"));
		tmp.setRfidService(res.getShort("rfidService"));
	
		return tmp;
	}

}
