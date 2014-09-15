package com.jtang.service;

import java.util.List;

import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.model.ProcessRecord;

public interface IProcessRecordService {
	
	/**
	 * 向数据库中添加一条加工记录,若添加成功返回1,否则返回0
	 * @param pr
	 * @return
	 */
	public int addAProcessRecord(ProcessRecord pr);
	
	
	/**
	 * 根据主键更新一条加工记录,传入对象的所有字段都会更新
	 */
	public int updateAProcessRecord(ProcessRecord pr);
	
	/**
	 * 根据企业id返回所有的加工记录,只能传入0个或者1个企业id
	 * @return 查询结果
	 * @exception 传入过多的企业Id会出发RuntimeException
	 */
	public List<ProcessRecord> getAllProcessRecord(String... enterpriseId);
	
	/**
	 * 根据指定的条件以及企业id返回所有的加工记录,只能传入0个或者1个企业id
	 * @return 查询结果
	 * @exception 传入过多的企业Id会出发RuntimeException
	 */
	public List<ProcessRecord> getProcessRecordBy(Conditionor con,String... enterpriseId);
	
	public ProcessRecord getProcessRecordByProcessNumber(String processNumber);
	
}
