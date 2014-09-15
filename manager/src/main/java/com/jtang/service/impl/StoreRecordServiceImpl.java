package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.StoreRecord;
import com.jtang.service.IStoreRecordService;

/**
 * @author chenminglong
 *
 */
public class StoreRecordServiceImpl implements IStoreRecordService {
	
    private BasicManagerDao manager;  //由Spring注入实体类


	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	public List<StoreRecord> getAllStoreRecords() {
		// TODO Auto-generated method stub
		String sql = "select * from storage_record";
		List<StoreRecord> AllStoreRecords = manager.query(sql, null,null,RowMapperEnum.STORERECORD.getMapperName());
		return AllStoreRecords;
	}
	
	public List<StoreRecord> getAStoreRecordById(int recorderId){
		String sql = " select * from storage_record where Id = ?";
		Object[] args = {recorderId};
		int [] argTypes = {Types.INTEGER};
		return manager.query(sql, args, argTypes, RowMapperEnum.STORERECORD.getMapperName());
	}

	public int addAStoreRecord(StoreRecord storeRecord) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO storage_record(StorageId,StartTime,EndTime,Temperature)"
				+ "VALUES(?, ?, ?, ?)";
		/*
		Object[] args = {product.getName(),product.getBarcode(),product.getProductionDate(),product.getStorageRecordId()
				,product.getTransportationRecordId(),product.getRawmaterialRecordId(),product.getRecorderId(),
				product.getRecordDate()};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
				*/
		Object[] args = {storeRecord.getStorageId(),storeRecord.getStartTime(),storeRecord.getEndTime(),storeRecord.getTemperature()};
		int [] argTypes = {Types.INTEGER,Types.TIMESTAMP,Types.TIMESTAMP,Types.FLOAT};
		return manager.add(sql, args, argTypes);
	}

	public int updateAStoreRecord(StoreRecord storeRecord) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteAStoreRecord(String extAddr) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int findStoreRecordId() {
		// TODO Auto-generated method stub
		String sql="SELECT MAX(Id) AS LargestId FROM storage_record";
		return manager.queryInt(sql, null, null);
	}

}
