package com.jtang.service;

import java.util.List;

import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.model.SaleRecord;

public interface ISaleRecordService {
	public int addASaleRecord(SaleRecord sr);
	public int deleteASaleRecord(String saleNumber);
	public SaleRecord getSaleRecordByPK(String saleNumber);
	public List<SaleRecord> getAllSaleRecords(String enterpriseId);
	public int updateASaleRecord(SaleRecord sr);
	public List<SaleRecord> getSaleRecordsByCon(Conditionor con);
}
