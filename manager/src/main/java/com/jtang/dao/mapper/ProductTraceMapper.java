package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Pro_Trace;

/**
 * 
 * 溯源查询的商品追踪
 * @author cml
 *
 */
public class ProductTraceMapper implements RowMapper<Pro_Trace> {

	 public Pro_Trace mapRow(ResultSet rs, int rowNum) throws SQLException {
     	Pro_Trace m = new Pro_Trace();
     	m.setId(rs.getInt("Id"));
     	m.setName(rs.getString("Name"));
         m.setBarcode(rs.getString("Barcode"));
         m.setProductionDate(rs.getDate("ProductionDate"));
         m.setStorageRecordId(rs.getInt("StorageRecordId"));
         m.setTransportationRecordId(rs.getInt("TransportationRecordId"));
         m.setRawmaterialRecordId(rs.getInt("RawmaterialRecordId"));
         m.setRecorderId(rs.getInt("RecorderId"));
         m.setRecordDate(rs.getDate("RecordDate"));
         return m;
     }
}
