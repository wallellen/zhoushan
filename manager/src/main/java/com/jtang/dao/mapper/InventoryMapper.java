package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Inventory;

/**
 * 仓库的商品库存
 * @author yyj
 *
 */
public class InventoryMapper implements RowMapper<Inventory>{

	public Inventory mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		Inventory m = new Inventory();
		m.setCount(rs.getInt("proCount"));
    	m.setName(rs.getString("proName"));
    	m.setMsg(rs.getString("proMsg"));
    	m.setStorageId(rs.getInt("storageId"));
        return m;
	}

}
