package com.jtang.service.impl;

import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.ProcessRecord;
import com.jtang.service.IProcessRecordService;

public class ProcessRecordServiceImpl implements IProcessRecordService {

	private BasicManagerDao manager;
	
	public int addAProcessRecord(ProcessRecord pr) {
		// TODO Auto-generated method stub
		return manager.addAuto(pr);
	}

	public int updateAProcessRecord(ProcessRecord pr) {
		// TODO Auto-generated method stub
		return manager.updateAuto(pr);
	}

	public List<ProcessRecord> getAllProcessRecord(String... enterpriseId) {
		// TODO Auto-generated method stub
		if(enterpriseId.length > 1) throw new RuntimeException("Just need one enterpriseId");
		Selector sel = Zql.select("*").from("process_record");
		if(enterpriseId.length == 1){
			sel.where(Zql.condition("enterpriseId", "=", enterpriseId[0]));
		}
		return manager.query(sel, RowMapperEnum.PROCESSRECORD.toString());
	}

	public List<ProcessRecord> getProcessRecordBy(Conditionor con,
			String... enterpriseId) {
		// TODO Auto-generated method stub
		if(enterpriseId.length > 1) throw new RuntimeException("Just need one enterpriseId");
		if(enterpriseId.length == 1){
			con.brackets().and(Zql.condition("enterpriseId", "=", enterpriseId[0]));
		}
		Selector sel = Zql.select("*").from("process_record");
		return manager.query(sel.where(con), RowMapperEnum.PROCESSRECORD.toString());
	}
	public ProcessRecord getProcessRecordByProcessNumber(String processNumber) {
		// TODO Auto-generated method stub
		Selector sel = Zql.select("*").from("process_record")
						.where(Zql.condition("processNumber", "=", processNumber));
		List<ProcessRecord> dataList = manager.query(sel, RowMapperEnum.PROCESSRECORD.toString());
		return dataList == null?null:dataList.get(0);
	}
	/**
	 * @return the manager
	 */
	public BasicManagerDao getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	

}
