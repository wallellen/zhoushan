package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(tableName = "temp_service",primaryKeyName = "id")
public class TempService {

	@TableColumn(columnName = "id",
			type = Types.INTEGER)
	private int id;
	@TableColumn(columnName = "realTimeData",
			type = Types.INTEGER)
	private int realTimeData;
	@TableColumn(columnName = "historyData",
			type = Types.INTEGER)
	private int historyData;
	@TableColumn(columnName = "exceptionData",
			type = Types.INTEGER)
	private int exceptionData;
	@TableColumn(columnName = "sensorManager",
			type = Types.INTEGER)
	private int sensorManager;
	@TableColumn(columnName = "storageManager",
			type = Types.INTEGER)
	private int storageManager;
	@TableColumn(columnName = "tmDownload",
			type = Types.INTEGER)
	private int tmDownload;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRealTimeData() {
		return realTimeData;
	}
	public void setRealTimeData(int realTimeData) {
		this.realTimeData = realTimeData;
	}
	public int getHistoryData() {
		return historyData;
	}
	public void setHistoryData(int historyData) {
		this.historyData = historyData;
	}
	public int getExceptionData() {
		return exceptionData;
	}
	public void setExceptionData(int exceptionData) {
		this.exceptionData = exceptionData;
	}
	public int getSensorManager() {
		return sensorManager;
	}
	public void setSensorManager(int sensorManager) {
		this.sensorManager = sensorManager;
	}
	public int getStorageManager() {
		return storageManager;
	}
	public void setStorageManager(int storageManager) {
		this.storageManager = storageManager;
	}
	public int getTmDownload() {
		return tmDownload;
	}
	public void setTmDownload(int tmDownload) {
		this.tmDownload = tmDownload;
	}
}
