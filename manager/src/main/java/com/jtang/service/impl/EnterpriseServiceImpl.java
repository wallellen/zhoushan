package com.jtang.service.impl;

import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.DaoUtil;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Enterprise;
import com.jtang.model.PageInfo;
import com.jtang.service.IEnterpriseService;

public class EnterpriseServiceImpl implements IEnterpriseService {
	
	private final String tableName = "enterprise";
	private final String mapperClassName =  RowMapperEnum.ENTERPRISE.toString();
	
	private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	@Override
	public List<Enterprise> getAllEnterprises() 
	{
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from(this.tableName), this.mapperClassName);
	}

	@Override
	public PageInfo<Enterprise> getAllEnterprisesByPage(
			PageInfo<Enterprise> pageInfo) 
	{
		// TODO Auto-generated method stub
		List<Enterprise> enterpriseList = manager.query(DaoUtil.getSelectorByPage(pageInfo, tableName, null, manager), mapperClassName);
		pageInfo.setDataList(enterpriseList);
		return pageInfo;
	}

	@Override
	public Enterprise getEnterPriseByIdOrName(String idOrName) 
	{
		// TODO Auto-generated method stub
		List<Enterprise> list = manager.query(Zql.select("*").from(this.tableName)
										.where(Zql.condition("id", "=", idOrName).or(Zql.condition("name", "=", idOrName))),
										this.mapperClassName);

		if( list != null && list.size() > 0)
		{
			return list.get(0);
		}
		else 
			return null;
	}

}
