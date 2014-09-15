package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Rawmaterial;

public class RawmaterialMapper implements RowMapper<Rawmaterial> {

	 public Rawmaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
     	Rawmaterial m = new Rawmaterial();
     	m.setRawId(rs.getString("rawId"));
     	m.setName(rs.getString("Name"));
        m.setManufacturer(rs.getString("Manufacturer"));
        m.setProductionPlace(rs.getString("ProductionPlace"));
        m.setProductionDate(rs.getString("ProductionDate"));
        m.setQuality(rs.getString("QualityEvaluation"));
        m.setAmount(rs.getInt("amount"));
        m.setSpare(rs.getInt("spare"));
        m.setEnterpriseId(rs.getString("enterpriseId"));
   
        return m;
     }
}
