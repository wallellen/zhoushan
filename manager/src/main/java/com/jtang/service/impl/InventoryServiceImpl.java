package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Inventory;
import com.jtang.service.IInventoryService;

public class InventoryServiceImpl implements IInventoryService{
	
	private BasicManagerDao manager; 

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao proManager) {
		this.manager = proManager;
	}

	public List<Inventory> getAllPro(int storageId) {
		// TODO Auto-generated method stub
		Object [] paras = {storageId};
		int [] argTypes = {Types.INTEGER};
		return manager.query("select * from inventory where storageId=?",paras,argTypes,RowMapperEnum.INVENTORY.getMapperName());
	}

	public int addProCount(String name,int addCount) {
		// TODO Auto-generated method stub
		return manager.update("update inventory set proCount=proCount+"+addCount+" where proName='"+name+"'", null,null);
	}

	public int minusProCount(String name,int minusCount) {
		// TODO Auto-generated method stub
		return manager.update("update inventory set proCount=proCount-"+minusCount+" where proName='"+name+"'", null, null);
	}

	public Inventory getPro(String name) {
		// TODO Auto-generated method stub
		
		Object [] paras = {name};
		int [] argTypes = {Types.VARCHAR};
		List<Inventory> pros = manager.query("select * from inventory where proName= ?", paras,argTypes,RowMapperEnum.INVENTORY.getMapperName());
		
		if(pros != null && pros.size() == 1)
		{
			return pros.get(0);
		}
		else 
		{
			return null;
		}
		
	}

	public int updatePro( int storageId,String name, String msg) {
		// TODO Auto-generated method stub
		String sql ="update inventory SET proMsg=? WHERE  proName=?";
		Object[]  paras =  new Object[]{msg,name};
		int[] types = {Types.VARCHAR,Types.VARCHAR};
		return manager.update(sql, paras, types);
	}

	public int deletePro(String name) {
		// TODO Auto-generated method stub
		String sql = "delete from inventory where proName=?";
		Object[]  paras =  new Object[]{name};
		int[] types = {Types.VARCHAR};
		
		return manager.update(sql, paras, types);
	}

	public int addPro(Inventory in) {
		// TODO Auto-generated method stub
		return manager.addAuto(in);
	}

}
