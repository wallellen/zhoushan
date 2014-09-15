package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.InOut;

/**
 * 出入库
 * @author yyj
 *
 */
public class InOutMapper implements RowMapper<InOut> {

	public InOut mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		InOut m = new InOut();
	    m.setCardNum(rs.getString("cardNum"));
        m.setAction(rs.getInt("action"));
        m.setPersonId(rs.getString("personId"));
        m.setBindCount(rs.getInt("bindCount"));
        m.setTime(rs.getString("time"));
        m.setStorageId(rs.getInt("storageId"));
        return m;
	}

}
