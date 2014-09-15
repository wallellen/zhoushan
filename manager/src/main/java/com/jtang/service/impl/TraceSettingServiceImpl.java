package com.jtang.service.impl;

import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.TraceService;
import com.jtang.service.ITraceSettingService;

public class TraceSettingServiceImpl implements ITraceSettingService {

	@Override
	public List<TraceService> getAllTraceService() {
		// TODO Auto-generated method stub
		return manager.query(Zql.select("*").from(this.tableName), this.mapperClassName);
	}
	
	private final String tableName ="trace_service";
	private final String mapperClassName =  RowMapperEnum.TRACESERVICE.toString();
	
	private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}


}
