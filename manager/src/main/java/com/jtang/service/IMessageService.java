package com.jtang.service;

import java.util.List;

import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.model.Message;
import com.jtang.model.PageInfo;

public interface IMessageService {
	//limit 为0 即表示无限制
	public List<Message> getMsgListUnRead(String userId,int limit);
	public List<Message> getMsgListRead(String userId);
	public List<Message> getMsgListByCon(Conditionor con,String userId);
	public List<Message> getMsgListSendedByMe(String userId);
	public List<Message> getMsgListSendedByCon(Conditionor con,String userId);
	
	public Message getMsgById(String messageId);
	public PageInfo<Message> getMsgByPage(PageInfo<Message> pageInfo,Conditionor con, String userId);
	public PageInfo<Message> getSendedMsgByPage(PageInfo<Message> pageInfo,Conditionor con, String userId);
	
	public int readMsg(String messageId);
	public int flagMsg(String messageId);
	public int cancelFlagMsg(String messageId);
	public int unReadMsg(String messageId);
	
	public int deleteMsg(String messageId);
	public int addMsg(Message msg);
	
	public int totalUnReadMsg(String userId);
	
	public int readAll(String userId);
}
