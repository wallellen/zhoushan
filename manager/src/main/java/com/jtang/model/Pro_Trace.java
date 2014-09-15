package com.jtang.model;

import java.util.Date;

public class Pro_Trace {
	private int Id;
	private String Name;
	private String Barcode;
	private Date ProductionDate;
	private int StorageRecordId; //存储数据记录
	private int TransportationRecordId;//运输数据记录
	private int RawmaterialRecordId;//原材料记录
	
	private int RecorderId;//记录员Id
	private Date RecordDate;//记录日期时间
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getBarcode() {
		return Barcode;
	}
	public void setBarcode(String barcode) {
		Barcode = barcode;
	}
	public Date getProductionDate() {
		return ProductionDate;
	}
	public void setProductionDate(Date productionDate) {
		ProductionDate = productionDate;
	}
	public int getStorageRecordId() {
		return StorageRecordId;
	}
	public void setStorageRecordId(int storageRecordId) {
		StorageRecordId = storageRecordId;
	}
	public int getTransportationRecordId() {
		return TransportationRecordId;
	}
	public void setTransportationRecordId(int transportationRecordId) {
		TransportationRecordId = transportationRecordId;
	}
	public int getRawmaterialRecordId() {
		return RawmaterialRecordId;
	}
	public void setRawmaterialRecordId(int rawmaterialRecordId) {
		RawmaterialRecordId = rawmaterialRecordId;
	}
	public int getRecorderId() {
		return RecorderId;
	}
	public void setRecorderId(int recorderId) {
		RecorderId = recorderId;
	}
	public Date getRecordDate() {
		return RecordDate;
	}
	public void setRecordDate(Date recordDate) {
		RecordDate = recordDate;
	}
	
	
	
}
