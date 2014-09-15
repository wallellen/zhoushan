package com.jtang.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jtang.model.Message;

public class MessageMapper implements RowMapper<Message>{

	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.setContent(rs.getString("content"));
		msg.setFlag(rs.getInt("flag"));
		msg.setFromWho(rs.getString("fromWho"));
		msg.setLevel(rs.getInt("level"));
		msg.setMessageId(rs.getString("messageId"));
		msg.setStatus(rs.getInt("status"));
		msg.setTime(rs.getString("time"));
		msg.setTitle(rs.getString("title"));
		msg.setToWho(rs.getString("toWho"));
		return msg;
	}

}
