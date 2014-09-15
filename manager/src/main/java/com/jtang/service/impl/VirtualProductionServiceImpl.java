package com.jtang.service.impl;

import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.DaoUtil;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.PageInfo;
import com.jtang.model.VirtualProduction;
import com.jtang.service.IVirtualProductionService;

public class VirtualProductionServiceImpl implements IVirtualProductionService {

	
	private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	
	public List<VirtualProduction> getAllVirtualProduction(
			String enterpriseId) {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from("virtual_production")
						.where(Zql.condition("enterpriseId", "=", enterpriseId))
				, RowMapperEnum.VIRTUALPRODUCTION.toString());
	}

	public List<VirtualProduction> getAllInStorage(String enterpriseId) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("enterpriseId", "=", enterpriseId)
							.and(Zql.condition("status", "=", 1)).brackets();
		return manager.query(Zql.select("*").from("virtual_production").where(con), 
				RowMapperEnum.VIRTUALPRODUCTION.toString());
		
	}

	public List<VirtualProduction> getAllNotInStorage(String enterpriseId) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("enterpriseId", "=", enterpriseId)
				.and(Zql.condition("status", "=", 0)).brackets();
		return manager.query(Zql.select("*").from("virtual_production").where(con), 
								RowMapperEnum.VIRTUALPRODUCTION.toString());
	}

	public List<VirtualProduction> getAllInCar(String enterpriseId) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("enterpriseId", "=", enterpriseId)
				.and(Zql.condition("status", "=", 2)).brackets();
		return manager.query(Zql.select("*").from("virtual_production").where(con), 
								RowMapperEnum.VIRTUALPRODUCTION.toString());
	}

	public List<VirtualProduction> getAllInMarket(String enterpriseId) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("enterpriseId", "=", enterpriseId)
				.and(Zql.condition("status", "=", 3)).brackets();
		return manager.query(Zql.select("*").from("virtual_production").where(con), 
								RowMapperEnum.VIRTUALPRODUCTION.toString());
	}

	public List<VirtualProduction> getProductionsBy(Conditionor con,
			String enterpriseId) {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from("virtual_production").where(con.brackets()
				.and(Zql.condition("enterpriseId", "=", enterpriseId))), 
				RowMapperEnum.VIRTUALPRODUCTION.toString());
	}

	public int addProduction(VirtualProduction vp, String... exceptions) {
		// TODO Auto-generated method stub
		return manager.addAuto(vp, exceptions);
	}

	public int updateRfid(String qrCode, String rfid) {
		// TODO Auto-generated method stub
		return manager.update(Zql.update("virtual_production")
								.set("rfid", "=", rfid)
								.set("status", "=", 1)
								.where(Zql.condition("qrCode", "=", qrCode)));
	}

	public int updateTransNumber(String qrCode, String transNumber) {
		// TODO Auto-generated method stub
		return manager.update(Zql.update("virtual_production")
				.set("transNumber", "=", transNumber)
				.set("status", "=", 2)
				.where(Zql.condition("qrCode", "=", qrCode)));
	}

	public int updateSaleNumber(String qrCode, String saleNumber) {
		// TODO Auto-generated method stub
		return manager.update(Zql.update("virtual_production")
				.set("saleNumber", "=", saleNumber)
				.set("status", "=", 3)
				.where(Zql.condition("qrCode", "=", qrCode)));
	}

	public PageInfo<VirtualProduction> getAllProductionByPage(
			PageInfo<VirtualProduction> pageInfo, Conditionor con,
			String enterpriseId) {
		// TODO Auto-generated method stub
		Conditionor newCon = Zql.condition("enterpriseId", "=", enterpriseId)
				.and((con == null?null:con.brackets()));
		List<VirtualProduction> vpList = manager.query(DaoUtil.getSelectorByPage(pageInfo, "virtual_production", newCon, manager),
									RowMapperEnum.VIRTUALPRODUCTION.getMapperName());
		pageInfo.setDataList(vpList);
		return pageInfo;
	}
	
	public PageInfo<VirtualProduction> getAllProductionByOrder(
			PageInfo<VirtualProduction> pageInfo, Conditionor con,
			String enterpriseId,String anotherTable,String field,String order) {
		// TODO Auto-generated method stub
		String field1 = field;
		if("transNumber".equals(field.trim())) field1 = "transportNumber";
		if("rfid".equals(field.trim())) field1 = "cardNum";
		Conditionor newCon = Zql.condition("virtual_production.enterpriseId", "=", enterpriseId)
				.and((con == null?null:con.brackets()));
		Selector sel = DaoUtil.getSelectorByPage(pageInfo, "virtual_production", newCon, manager);
		sel.join(anotherTable, "virtual_production." + field, "=", anotherTable + "." + field1).orderby(order);
		List<VirtualProduction> vpList = manager.query(sel,
									RowMapperEnum.VIRTUALPRODUCTION.getMapperName());
		pageInfo.setDataList(vpList);
		return pageInfo;
	}
	
	
	public VirtualProduction getProductionByQrcode(String qrCode){
		List<VirtualProduction > vpList 
			= manager.query(Zql.select("*")
				 .from("virtual_production")
				 .where(Zql.condition("qrCode", "=", qrCode)), RowMapperEnum.VIRTUALPRODUCTION.toString());
		return vpList == null ? null : vpList.get(0);
	}

	@Override
	public List<VirtualProduction> getVirtualProductionByRFID(
			String enterpriseId, String rfidCard) {
		// TODO Auto-generated method stub
		List<VirtualProduction > vpList 
		   = manager.query(Zql.select("*")
			 .from("virtual_production")
			 .where(Zql.condition("rfid", "=", rfidCard)
			 .and(Zql.condition("enterpriseId", "=", enterpriseId))), RowMapperEnum.VIRTUALPRODUCTION.toString());
	    return vpList;
	}

	@Override
	public List<String> getAllVirtualProductionName(String enterpriseId) {
		// TODO Auto-generated method stub
		List<?> vproNames = null;
		try {
			vproNames = manager.queryForList("SELECT productionName FROM virtual_production where enterpriseId = " + enterpriseId + " GROUP BY productionName", 
					null, Class.forName("java.lang.String"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (List<String>) vproNames;
	}


}
