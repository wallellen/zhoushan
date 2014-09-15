package com.jtang.service.impl;

import java.util.List;
import java.util.Map;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.SimpleConditionor;
import com.jtang.dao.sqlUtil.SimpleDeletor;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.StorageService;
import com.jtang.service.IStorageServiceSettingService;

public class StorageServiceSettingServiceImpl implements
		IStorageServiceSettingService {
	
	private final String tableName ="storage_service";
	private final String mapperClassName =  RowMapperEnum.STORAGESERVICE.toString();
	
	private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	

	@Override
	public List<StorageService> getAllStorageService() {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from(this.tableName), this.mapperClassName);
	}

	@Override
	public int add(StorageService s) {
		// TODO Auto-generated method stub
		return manager.addAuto(s, "");
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return manager.delete(Zql.deleteFrom(tableName)
				.where(Zql.condition("id", "=", id)));
	}

	@Override
	public List<Map<String, Object>> getUserCount() {
		// TODO Auto-generated method stub
		return manager.queryForListMap("SELECT COUNT(id) as user_count, storageService as id FROM `enterprise` GROUP BY storageService", null, null);
		
	}

}
