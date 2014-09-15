package com.jtang.service.impl;

import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.TransRecord;
import com.jtang.service.ITransRecordService;

/**
 * @author Administartor
 *
 */
public class TransRecordServiceImpl implements ITransRecordService {
	
    private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	public List<TransRecord> getAllTransRecords(String enterpriseId) {
		// TODO Auto-generated method stub
		
		return manager.query(Zql.select("*").from("transportation_record")
				.where(Zql.condition("enterpriseId", "=", enterpriseId)), RowMapperEnum.TRANSRECORD.getMapperName());
	}

	public int addATransRecord(TransRecord transRecord) {
		// TODO Auto-generated method stub
		return manager.addAuto(transRecord);
	}

	public int updateATransRecord(TransRecord transRecord) {
		// TODO Auto-generated method stub
		return manager.updateAuto(transRecord);
	}

	public int deleteATransRecord(String transportNumber) {
		// TODO Auto-generated method stub
		return manager.delete(Zql.deleteFrom("transportation_record")
				.where(Zql.condition("transportNumber", "=", transportNumber)));
	}

	public TransRecord getTransRecordByPK(String transportNumber) {
		// TODO Auto-generated method stub
		Selector sel = Zql.select("*").from("transportation_record")
				.where(Zql.condition("transportNumber", "=", transportNumber));
		List<TransRecord> dataList = manager.query(sel, RowMapperEnum.TRANSRECORD.toString());
		return dataList == null?null:dataList.get(0);
	}

	@Override
	public List<TransRecord> getTransByCon(Conditionor con) {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from("transportation_record")
								.where(con), RowMapperEnum.TRANSRECORD.getMapperName());
	}


}
