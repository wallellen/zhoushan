package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(tableName = "trace_service",primaryKeyName = "id")
public class TraceService {
	@TableColumn(columnName = "id",
			type = Types.INTEGER)
	private int id;
	@TableColumn(columnName = "rawmaterialManager",
			type = Types.INTEGER)
	private int rawmaterialManager;
	@TableColumn(columnName = "productionManager",
			type = Types.INTEGER)
	private int productionManager;
	@TableColumn(columnName = "dataManager",
			type = Types.INTEGER)
	private int dataManager;
	@TableColumn(columnName = "productionStatistics",
			type = Types.INTEGER)
	private int productionStatistics;
	@TableColumn(columnName = "datarecord",
			type = Types.INTEGER)
	private int datarecord;
	@TableColumn(columnName = "dataquery",
			type = Types.INTEGER)
	private int dataquery;
	@TableColumn(columnName = "mobileApp",
			type = Types.INTEGER)
	private int mobileApp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRawmaterialManager() {
		return rawmaterialManager;
	}
	public void setRawmaterialManager(int rawmaterialManager) {
		this.rawmaterialManager = rawmaterialManager;
	}
	public int getProductionManager() {
		return productionManager;
	}
	public void setProductionManager(int productionManager) {
		this.productionManager = productionManager;
	}
	public int getDataManager() {
		return dataManager;
	}
	public void setDataManager(int dataManager) {
		this.dataManager = dataManager;
	}
	public int getProductionStatistics() {
		return productionStatistics;
	}
	public void setProductionStatistics(int productionStatistics) {
		this.productionStatistics = productionStatistics;
	}
	public int getDatarecord() {
		return datarecord;
	}
	public void setDatarecord(int datarecord) {
		this.datarecord = datarecord;
	}
	public int getDataquery() {
		return dataquery;
	}
	public void setDataquery(int dataquery) {
		this.dataquery = dataquery;
	}
	public int getMobileApp() {
		return mobileApp;
	}
	public void setMobileApp(int mobileApp) {
		this.mobileApp = mobileApp;
	}
}
