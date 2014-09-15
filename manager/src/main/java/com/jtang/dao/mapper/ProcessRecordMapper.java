package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.ProcessRecord;

public class ProcessRecordMapper implements RowMapper<ProcessRecord> {

	public ProcessRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ProcessRecord pr = new ProcessRecord();
		pr.setProcessEnv(rs.getString("processEnv"));
		pr.setProcessLoc(rs.getString("processLoc"));
		pr.setProcessNumber(rs.getString("processNumber"));
		pr.setProcessTime(rs.getString("processTime"));
		pr.setProcessUser(rs.getString("processUser"));
		pr.setRawIdList(rs.getString("rawIdList"));
		pr.setEnterpriseId(rs.getString("enterpriseId"));
		pr.setRawCountList(rs.getString("rawCountList"));
		return pr;
	}

}
