package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;
@DBTable(primaryKeyName = "proName", tableName = "inventory")
public class Inventory {
	@TableColumn(columnName = "proName", type = Types.VARCHAR)
	private String name;
	@TableColumn(columnName = "proCount", type = Types.INTEGER)
	private int count;
	@TableColumn(columnName = "proMsg", type = Types.VARCHAR)
	private String msg;
	@TableColumn(columnName = "storageId", type = Types.INTEGER)
	private int storageId;
	
	public Inventory (){};
	public Inventory (String name,String msg , int storageId, int count){
		this.msg = msg;
		this.count = count;
		this.storageId = storageId;
		this.name = name;
	}
	
	public int getStorageId() {
		return storageId;
	}
	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
