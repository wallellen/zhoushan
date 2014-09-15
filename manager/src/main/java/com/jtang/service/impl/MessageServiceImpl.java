package com.jtang.service.impl;

import java.util.Date;
import java.util.List;

import jt.sensordata.bean.GlobalVariable;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.DaoUtil;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Message;
import com.jtang.model.PageInfo;
import com.jtang.service.IMessageService;

public class MessageServiceImpl implements IMessageService {

	private BasicManagerDao manager;
	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	
	@Override
	public List<Message> getMsgListUnRead(String userId, int limit) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("toWho", "=", userId)
					.and(Zql.condition("status", "=", 1));
		Selector sel = Zql.select("*").from("Message").where(con)
							.orderby("flag desc","time desc");
		if(limit != 0) sel.limit(limit);
		return manager.query(sel, RowMapperEnum.MESSAGE.getMapperName());
	}

	@Override
	public List<Message> getMsgListRead(String userId) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("toWho", "=", userId)
					.and(Zql.condition("status", "=", 0));
		Selector sel = Zql.select("*").from("Message").where(con)
							.orderby("flag desc","time desc");
		return manager.query(sel, RowMapperEnum.MESSAGE.getMapperName());
	}

	@Override
	public List<Message> getMsgListByCon(Conditionor con,String userId) {
		// TODO Auto-generated method stub
		con.brackets().and(Zql.condition("toWho", "=", userId));
		Selector sel = Zql.select("*").from("Message").where(con)
				.orderby("status desc","flag desc","time desc");
		return manager.query(sel, RowMapperEnum.MESSAGE.getMapperName());
	}

	@Override
	public int readMsg(String messageId) {
		// TODO Auto-generated method stub
		return manager
				.update(Zql.update("Message")
						   .set("status", "=", 0)
						   .where(Zql.condition("messageId", "=", messageId)));
	}
	
	@Override
	public int readAll(String userId) {
		// TODO Auto-generated method stub
		return manager.update(Zql.update("Message").set("status", "=", 0)
								.where(Zql.condition("toWho", "=", userId)));
	}

	@Override
	public int flagMsg(String messageId) {
		// TODO Auto-generated method stub
		return manager
				.update(Zql.update("Message")
						   .set("flag", "=", 1)
						   .where(Zql.condition("messageId", "=", messageId)));
	}

	@Override
	public int cancelFlagMsg(String messageId) {
		// TODO Auto-generated method stub
		return manager
				.update(Zql.update("Message")
						   .set("flag", "=", 0)
						   .where(Zql.condition("messageId", "=", messageId)));
	}

	@Override
	public int unReadMsg(String messageId) {
		// TODO Auto-generated method stub
		return manager
				.update(Zql.update("Message")
						   .set("status", "=", 1)
						   .where(Zql.condition("messageId", "=", messageId)));
	}

	@Override
	public int deleteMsg(String messageId) {
		// TODO Auto-generated method stub
		return manager.delete(Zql.deleteFrom("Message")
				.where(Zql.condition("messageId", "=", messageId)));
	}

	@Override
	public int addMsg(Message msg) {
		// TODO Auto-generated method stub
		msg.setTime(GlobalVariable.formatter.format(new Date()));
		return manager.addAuto(msg);
	}

	@Override
	public List<Message> getMsgListSendedByMe(String userId) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("fromWho", "=", userId);
		Selector sel = Zql.select("*").from("Message").where(con)
						.orderby("time desc");
		return manager.query(sel, RowMapperEnum.MESSAGE.getMapperName());
	}

	@Override
	public List<Message> getMsgListSendedByCon(Conditionor con,String userId) {
		// TODO Auto-generated method stub
		con.brackets().and(Zql.condition("fromWho", "=", userId));
		Selector sel = Zql.select("*").from("Message").where(con)
						.orderby("time desc");
		return manager.query(sel, RowMapperEnum.MESSAGE.getMapperName());
	}

	@Override
	public Message getMsgById(String messageId) {
		// TODO Auto-generated method stub
		List<Message> msgList = manager.query(Zql.select("*")
				.from("Message").where(
				Zql.condition("messageId", "=", messageId)), RowMapperEnum.MESSAGE.getMapperName());
		return (msgList == null || msgList.size() == 0)?null:msgList.get(0);
	}

	@Override
	public PageInfo<Message> getMsgByPage(PageInfo<Message> pageInfo,
			Conditionor con, String userId) {
		// TODO Auto-generated method stub
		Conditionor newCon = Zql.condition("toWho", "=", userId)
				.and((con == null?null:con.brackets()));
		List<Message> msgList = manager.query(
						DaoUtil.getSelectorByPage(pageInfo, "Message", newCon, manager)
						.orderby("status desc","flag desc","time desc")
							, RowMapperEnum.MESSAGE.getMapperName());
		pageInfo.setDataList(msgList);
		return pageInfo;
	}

	@Override
	public PageInfo<Message> getSendedMsgByPage(PageInfo<Message> pageInfo,
			Conditionor con, String userId) {
		// TODO Auto-generated method stub
		Conditionor newCon = Zql.condition("fromWho", "=", userId)
				.and((con == null?null:con.brackets()));
		List<Message> msgList = manager.query(
						DaoUtil.getSelectorByPage(pageInfo, "Message", newCon, manager)
						.orderby("status desc","flag desc","time desc")
							, RowMapperEnum.MESSAGE.getMapperName());
		pageInfo.setDataList(msgList);
		return pageInfo;
	}

	@Override
	public int totalUnReadMsg(String userId) {
		// TODO Auto-generated method stub
		Selector sel = Zql.select("count(*)").from("Message")
							.where(
									Zql.condition("toWho", "=", userId)
									.and(Zql.condition("status", "=", 1)));
		return manager.queryInt(sel);
	}


}
