package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(tableName = "storage_service",primaryKeyName = "id")
public class StorageService {

	@TableColumn(columnName = "id",
			type = Types.INTEGER)
	private int id;
	@TableColumn(columnName = "inoutService",
			type = Types.INTEGER)
	private int inoutService;
	@TableColumn(columnName = "inventoryService",
			type = Types.INTEGER)
	private int inventoryService;
	@TableColumn(columnName = "repPreviwService",
			type = Types.INTEGER)
	private int repPreviwService;
	@TableColumn(columnName = "rfidService",
			type = Types.INTEGER)
	private int rfidService;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInoutService() {
		return inoutService;
	}
	public void setInoutService(int inoutService) {
		this.inoutService = inoutService;
	}
	public int getInventoryService() {
		return inventoryService;
	}
	public void setInventoryService(int inventoryService) {
		this.inventoryService = inventoryService;
	}
	public int getRepPreviwService() {
		return repPreviwService;
	}
	public void setRepPreviwService(int repPreviwService) {
		this.repPreviwService = repPreviwService;
	}
	public int getRfidService() {
		return rfidService;
	}
	public void setRfidService(int rfidService) {
		this.rfidService = rfidService;
	}
	
}
