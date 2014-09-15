package com.jtang.model;

import java.util.Date;

public class StoreRecord {
	private int Id;
	private int StorageId;
	private Date StartTime;	
	private Date EndTime;
	private float Temperature;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getStorageId() {
		return StorageId;
	}
	public void setStorageId(int storageId) {
		StorageId = storageId;
	}
	public Date getStartTime() {
		return StartTime;
	}
	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}
	public Date getEndTime() {
		return EndTime;
	}
	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}
	public float getTemperature() {
		return Temperature;
	}
	public void setTemperature(float temperature) {
		Temperature = temperature;
	}

}
