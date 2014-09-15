package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.InOut;

/**
 * 商品出入库信息
 * 联表inventory 附加产品名
 * @author yyj
 *
 */
public class InOutWithProNameMapper implements RowMapper<InOut>{

	public InOut mapRow(ResultSet rs, int rowNum) throws SQLException {
    	InOut m = new InOut();
        m.setCardNum(rs.getString("cardNum"));
        m.setAction(rs.getInt("action"));
        m.setPersonId(rs.getString("personId"));
        m.setBindCount(rs.getInt("bindCount"));
        m.setTime(rs.getString("time"));
        //m.setBarCode(rs.getString("barCode"));
       //m.setProId(rs.getInt("proId"));
        m.setProName(rs.getString("proName"));
        m.setStorageId(rs.getInt("storageId"));
        return m;
    }
}
