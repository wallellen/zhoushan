package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(primaryKeyName = "cardNum", tableName = "In_Out")
public class InOut {
	@TableColumn(columnName = "cardNum", type = Types.VARCHAR)
	private String cardNum;
	
	@TableColumn(columnName = "action", type = Types.INTEGER)
	private int action;
	
	@TableColumn(columnName = "personId", type = Types.VARCHAR)
	private String personId;
	
	@TableColumn(columnName = "time", type = Types.VARCHAR)
	private String time;
	
	@TableColumn(columnName = "proId", type = Types.INTEGER)
	private int proId;
	
	@TableColumn(columnName = "barCode", type = Types.VARCHAR)
	private String barCode;
	
	@TableColumn(columnName = "storageId", type = Types.INTEGER)
	private int storageId;
	
	@TableColumn(columnName = "proName", type = Types.VARCHAR)
	private String proName;
	
	@TableColumn(columnName = "bindCount", type = Types.INTEGER)
	private int bindCount;
	
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public int getStorageId() {
		return storageId;
	}
	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}
	public int getProId() {
		return proId;
	}
	public void setProId(int proId) {
		this.proId = proId;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getBindCount() {
		return bindCount;
	}
	public void setBindCount(int bindCount) {
		this.bindCount = bindCount;
	}
	
}
