package com.jtang.service.impl;

import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.TempService;
import com.jtang.service.ITempServiceSettingService;

public class TempServiceSettingServiceImpl implements
		ITempServiceSettingService {

	private final String tableName ="temp_service";
	private final String mapperClassName =  RowMapperEnum.TEMPSERVICE.toString();
	
	private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	
	@Override
	public List<TempService> getAllTempService() {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from(this.tableName), this.mapperClassName);
	}

}
