package com.jtang.service.impl;

import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.SaleRecord;
import com.jtang.service.ISaleRecordService;

public class SaleRecordServiceImpl implements ISaleRecordService {
	
	private BasicManagerDao manager;
		
	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	@Override
	public int addASaleRecord(SaleRecord sr) {
		// TODO Auto-generated method stub
		return manager.addAuto(sr);
	}

	@Override
	public int deleteASaleRecord(String saleNumber) {
		// TODO Auto-generated method stub
		return manager.delete(Zql.deleteFrom("sale_record")
								 .where(Zql.condition("saleNumber", "=", saleNumber)));
	}

	@Override
	public SaleRecord getSaleRecordByPK(String saleNumber) {
		// TODO Auto-generated method stub
		Selector sel = Zql.select("*").from("sale_record")
				.where(Zql.condition("saleNumber", "=", saleNumber));
		List<SaleRecord> dataList = manager.query(sel, RowMapperEnum.SALERECORD.toString());
		
		return dataList == null ? null : dataList.get(0);
	}

	@Override
	public List<SaleRecord> getAllSaleRecords(String enterpriseId) {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from("sale_record")
								.where(Zql.condition("enterpriseId", "=", enterpriseId)), RowMapperEnum.SALERECORD.toString());
	}

	@Override
	public int updateASaleRecord(SaleRecord sr) {
		// TODO Auto-generated method stub
		return manager.updateAuto(sr);
	}

	@Override
	public List<SaleRecord> getSaleRecordsByCon(Conditionor con) {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from("sale_record")
				.where(con), RowMapperEnum.SALERECORD.toString());
	}

}
