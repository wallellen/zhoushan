package com.jtang.service;

import java.util.List;
import com.jtang.model.StoreRecord;

public interface IStoreRecordService {
	/**
	 * 获取数据库中所有存储记录
	 * @return
	 */
	public List<StoreRecord > getAllStoreRecords();
	
	/**
	 * 向数据库中添加一条存储记录
	 * 成功则返回1，失败则返回0
	 */
	public int addAStoreRecord(StoreRecord storeRecord);
	
	/**
	 * 修改存储记录
	 */
	public int updateAStoreRecord(StoreRecord storeRecord);
	
	/**
	 * 删除一条指定的StoreRecord
	 */
	public int deleteAStoreRecord(String extAddr);
	
	/**
	 * 找出存储记录最大Id值
	 */
	public int findStoreRecordId();
	
	/**
	 * 根据recorderId查询记录
	 * @param recorderId
	 * @return
	 */
	public List<StoreRecord> getAStoreRecordById(int recorderId);
	
	
}
