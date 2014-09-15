package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(tableName = "sensor" , primaryKeyName = "extAddr")
public class Sensor {
	@TableColumn(columnName = "extAddr", type =  Types.VARCHAR)
	private String extAddr;
	
	@TableColumn(columnName = "shortAddr", type =  Types.VARCHAR)
	private String shortAddr;
	
	@TableColumn(columnName = "nodeTypes", type =  Types.INTEGER)
	private int nodeTypes; 
	
	@TableColumn(columnName = "workStatus", type =  Types.INTEGER)
	private int workStatus;
	
	@TableColumn(columnName = "fatherNode", type =  Types.VARCHAR)
	private String fatherNode;
	
	@TableColumn(columnName = "position", type =  Types.VARCHAR)
	private String position;
	
	@TableColumn(columnName = "creator", type =  Types.VARCHAR)
	private String creator;
	
	@TableColumn(columnName = "createTime", type =  Types.VARCHAR)
	private String createTime;
	
	@TableColumn(columnName = "mender", type =  Types.VARCHAR)
	private String mender;
	
	@TableColumn(columnName = "mendTime", type =  Types.VARCHAR)
	private String mendTime;
	
	@TableColumn(columnName = "workTime", type =  Types.INTEGER)
	private int workTime;
	
	@TableColumn(columnName = "storageId", type =  Types.INTEGER)
	private int storageId;
	
	@TableColumn(columnName = "name", type =  Types.VARCHAR)
	private String name;
	
	
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the storageId
	 */
	public int getStorageId() {
		return storageId;
	}
	/**
	 * @param storageId the storageId to set
	 */
	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}
	public int getWorkTime() {
		return workTime;
	}
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMender() {
		return mender;
	}
	public void setMender(String mender) {
		this.mender = mender;
	}
	public String getMendTime() {
		return mendTime;
	}
	public void setMendTime(String mendTime) {
		this.mendTime = mendTime;
	}
	public String getExtAddr() {
		return extAddr;
	}
	public void setExtAddr(String extAddr) {
		this.extAddr = extAddr;
	}
	public String getShortAddr() {
		return shortAddr;
	}
	public void setShortAddr(String shortAddr) {
		this.shortAddr = shortAddr;
	}
	public int getNodeTypes() {
		return nodeTypes;
	}
	public void setNodeTypes(int nodeTypes) {
		this.nodeTypes = nodeTypes;
	}
	public int getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
	}
	public String getFatherNode() {
		return fatherNode;
	}
	public void setFatherNode(String fatherNode) {
		this.fatherNode = fatherNode;
	}
	
	
	
}
