package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.ProductQuery;

public class ProductQueryMapper implements RowMapper<ProductQuery> {

	 public ProductQuery mapRow(ResultSet rs, int rowNum) throws SQLException {
     	ProductQuery m = new ProductQuery();
     	m.setId(rs.getInt("Id"));
     	m.setProName(rs.getString(2));
     	m.setProBarcode(rs.getString(3));
     	m.setProDate(rs.getDate(4));
     	
     	m.setStoName(rs.getString(5));
     	m.setStoStime(rs.getDate(6));
     	m.setStoEtime(rs.getDate(7));
     	m.setStoTemp(rs.getFloat(8));
     	
     	m.setTranSplace(rs.getString(9));
     	m.setTranEplace(rs.getString(10));
     	m.setTranStime(rs.getDate(11));
     	m.setTranEtime(rs.getDate(12));
     	m.setTranTemp(rs.getFloat(13));
     	
     	m.setRmName(rs.getString(14));
     	m.setRmManu(rs.getString(15));
     	m.setRmPlace(rs.getString(16));
     	m.setRmDate(rs.getDate(17));
     	//m.setRmDate(rs.getString(17));
     	//也可以，此时转换为JSON串用JSON.toJSONString即可
     	
     	m.setRecorderName(rs.getString(18));
         return m;
     }
}
