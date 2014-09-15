package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(primaryKeyName = "messageId", tableName = "Message")
public class Message {
	
	@TableColumn(columnName = "messageId")
	private String messageId;
	
	/**
	 * 1..代表通知，上级管理员通知下级管理员xxxx
	 * 2..代表消息，平级用户互相交流等
	 * 3..代表任务，提醒管理员处理提权申请等
	 */
	
	@TableColumn(columnName = "level", type=Types.INTEGER)
	private int level = 1;
	
	@TableColumn(columnName = "fromWho")
	private String fromWho;
	
	@TableColumn(columnName = "toWho")
	private String toWho;
	
	@TableColumn(columnName = "content")
	private String content;
	
	@TableColumn(columnName = "title")
	private String title;
	
	@TableColumn(columnName = "time")
	private String time;
	
	/**
	 * 标记置顶
	 * 1置顶
	 * 0不置顶
	 */
	@TableColumn(columnName = "flag", type=Types.INTEGER)
	private int flag = 0;
	
	/**
	 * 1未读
	 * 0已读
	 */
	@TableColumn(columnName = "status", type=Types.INTEGER)
	private int status = 1;

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	

	/**
	 * @return the fromWho
	 */
	public String getFromWho() {
		return fromWho;
	}

	/**
	 * @param fromWho the fromWho to set
	 */
	public void setFromWho(String fromWho) {
		this.fromWho = fromWho;
	}

	/**
	 * @return the toWho
	 */
	public String getToWho() {
		return toWho;
	}

	/**
	 * @param toWho the toWho to set
	 */
	public void setToWho(String toWho) {
		this.toWho = toWho;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
