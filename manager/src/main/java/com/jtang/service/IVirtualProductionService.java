package com.jtang.service;

import java.util.List;

import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.model.PageInfo;
import com.jtang.model.VirtualProduction;


public interface IVirtualProductionService {
	
	public List<VirtualProduction> getVirtualProductionByRFID( String enterpriseId , String rfidCard);
	
	/**
	 * 根据企业id返回现有的所有产品名
	 * @param enterpriseId
	 * @return
	 */
	public List<String> getAllVirtualProductionName(String enterpriseId);
	/**
	 * 根据企业id返回所有的产品记录,
	 * @return 查询结果
	 *
	 */
	public List<VirtualProduction> getAllVirtualProduction(String enterpriseId);
	
	public List<VirtualProduction> getAllInStorage(String enterpriseId);
	
	public List<VirtualProduction> getAllNotInStorage(String enterpriseId);
	
	public List<VirtualProduction> getAllInCar(String enterpriseId);
	
	public List<VirtualProduction> getAllInMarket(String enterpriseId);
	
	public List<VirtualProduction> getProductionsBy(Conditionor con,String enterpriseId);
	
	public int addProduction(VirtualProduction vp,String... exceptions);
	
	/**
	 * 也会同步更新status到入库状态
	 * @param qrCode
	 * @param rfid
	 * @return
	 */
	public int updateRfid(String qrCode,String rfid);
	
	/**
	 * 也会同步更新status到运输状态
	 * @param qrCode
	 * @param transNumber
	 * @return
	 */
	public int updateTransNumber(String qrCode,String transNumber);
	
	/**
	 * 也会同步更新status到销售状态
	 * @param qrCode
	 * @param saleNumber
	 * @return
	 */
	public int updateSaleNumber(String qrCode,String saleNumber);
	
	public PageInfo<VirtualProduction> getAllProductionByPage(PageInfo<VirtualProduction> pageInfo,Conditionor con,String enterpriseId);
	public PageInfo<VirtualProduction> getAllProductionByOrder(
			PageInfo<VirtualProduction> pageInfo, Conditionor con,
			String enterpriseId,String anotherTable,String field,String order);
	public VirtualProduction getProductionByQrcode(String qrCode);
}
